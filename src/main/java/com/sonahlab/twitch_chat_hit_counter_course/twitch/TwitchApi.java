package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.awt.*;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Component
public class TwitchApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchApi.class);

    private static final String TWITCH_API_BASE_URL = "https://api.twitch.tv/";
    private static final String USERS_ENDPOINT = "helix/users?login=%s";

    private static final String TWITCH_ID_BASE_URL = "https://id.twitch.tv/";
    private static final String AUTH_ENDPOINT = "oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=code&scope=%s";

    private static final String TWITCH_WEBSOCKET_URL = "wss://eventsub.wss.twitch.tv/ws";

    private static final String CLIENT_ID_HEADER = "Client-Id";
    private static final String OAUTH_HEADER = "Authorization";

    private static final String CLIENT_ID = "kx29yhv9hva3kqpenhpdmc9oayc4vy";
    private static final String CLIENT_SECRET = "ee04a6tx7sz58qg39pgho8fumi9q27";
    private static final String OAUTH_TOKEN = "pwuysppmm1vkxf6x2rj8sdcdu9d1u5";

    private WebClient webClient;
    private WebSocketClient webSocketClient;
    private WebSocketSession webSocketSession;

    public TwitchApi() {
        //curl -X GET 'https://api.twitch.tv/helix/users?login=sonahlab' \
        //-H 'Authorization: Bearer pwuysppmm1vkxf6x2rj8sdcdu9d1u5' \
        //-H 'Client-Id: kx29yhv9hva3kqpenhpdmc9oayc4vy'
        //
        //{"data":
        this.webClient = WebClient.create();
        this.webSocketClient = new StandardWebSocketClient();
//        initWebSocket();
//        sendHttpRequest(HttpMethod.GET, TWITCH_API_BASE_URL + String.format(USERS_ENDPOINT, "kurumx"), null);
        sendHttpRequest(HttpMethod.GET, TWITCH_ID_BASE_URL + String.format(AUTH_ENDPOINT, CLIENT_ID, "http://localhost:8080/oauth2/callback", "chat:read"), null);
    }

    private void initWebSocket() {
        WebSocketHandler handler = new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) {
                LOGGER.info("Connected to Twitch EventSub WebSocket");
                webSocketSession = session;
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                handleWebSocketMessage(message.getPayload());
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) {
                LOGGER.error("WebSocket error: " + exception.getMessage());
                reconnect();
            }

            @Override
            public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
                LOGGER.info("WebSocket closed: " + status);
                webSocketSession = null;
                reconnect();
            }
        };

        webSocketClient
                .execute(handler, new WebSocketHttpHeaders(), URI.create(TWITCH_WEBSOCKET_URL))
                .whenComplete((session, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error("Failed to connect to WebSocket: " + throwable.getMessage());
                        reconnect();
                    }
                });
    }


    private void handleWebSocketMessage(String message) throws Exception {
        Map<String, Object> json = new ObjectMapper().readValue(message, Map.class);
        String messageType = (String) ((Map<String, Object>) json.get("metadata")).get("message_type");

        String broadcasterId = "kurumx";
        switch (messageType) {
            case "session_welcome":
                String sessionId = (String) ((Map<String, Object>) ((Map<String, Object>) json.get("payload")).get("session")).get("id");
                LOGGER.info("Received session ID: " + sessionId);
                createSubscription(sessionId, "channel.follow", "2", "{\"broadcaster_user_id\": \"" + broadcasterId + "\", \"moderator_user_id\": \"" + broadcasterId + "\"}");
                break;
            case "notification":
                var payload = (Map<String, Object>) json.get("payload");
                if (payload == null) {
                    LOGGER.warn("Payload is null");
                    break;
                }
                Map<String, Object> subscription = (Map<String, Object>) payload.get("subscription");
                if (subscription == null) {
                    LOGGER.warn("Subscription is null");
                    break;
                }
                String subscriptionType = (String) subscription.get("type");
                if ("channel.follow".equals(subscriptionType)) {
                    Map<String, Object> event = (Map<String, Object>) payload.get("event");
                    if (event != null) {
                        String userName = (String) event.get("user_name");
                        LOGGER.info("New follower: " + userName);
                    } else {
                        LOGGER.warn("Event is null");
                    }
                }
                break;
            case "session_keepalive":
                LOGGER.info("Keepalive received");
                break;
            case "session_reconnect":
                String newUrl = (String) ((Map<String, Object>) ((Map<String, Object>) json.get("payload")).get("session")).get("reconnect_url");
                LOGGER.info("Reconnect requested: " + newUrl != null ? newUrl : TWITCH_WEBSOCKET_URL);
                if (webSocketSession != null) {
                    try {
                        webSocketSession.close();
                    } catch (Exception e) {
                        LOGGER.error("Error closing WebSocket: " + e.getMessage());
                    }
                }
                reconnect();
                break;
            default:
                LOGGER.warn("Unknown message type: " + messageType);
        }
    }

    private void createSubscription(String sessionId, String type, String version, String condition) {
        // Define HttpHeaders at the top
        HttpHeaders headers = new HttpHeaders();
        headers.add(CLIENT_ID_HEADER, CLIENT_ID);
        headers.add("Authorization", "Bearer " + OAUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format(
                "{\"type\": \"%s\", \"version\": \"%s\", \"condition\": %s, \"transport\": {\"method\": \"websocket\", \"session_id\": \"%s\"}}",
                type, version, condition, sessionId);

        webClient
                .post()
                .uri("/eventsub/subscriptions")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(body)
                .retrieve()
                .onStatus(status -> status.isError(), response ->
                        response.bodyToMono(String.class)
                                .map(b -> new RuntimeException("HTTP error: " + response.statusCode() + " - " + b)))
                .bodyToMono(String.class)
                .doOnSuccess(response -> LOGGER.info("Subscription created: " + response))
                .doOnError(error -> LOGGER.error("Failed to create subscription: " + error.getMessage()))
                .subscribe();
    }

    private void reconnect() {
        if (webSocketSession != null) {
            try {
                webSocketSession.close();
            } catch (Exception e) {
                LOGGER.error("Error closing WebSocket: " + e.getMessage());
            }
        }
        try {
            Thread.sleep(1000); // Wait 1 second before reconnecting
            initWebSocket();
        } catch (InterruptedException e) {
            LOGGER.error("Reconnect interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void sendHttpRequest(HttpMethod httpMethod, String url, String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(CLIENT_ID_HEADER, CLIENT_ID);
        headers.add(OAUTH_HEADER, "Bearer " + OAUTH_TOKEN);

        String response = webClient
                .method(httpMethod)
                .uri(URI.create(url))
                .headers(header -> header.addAll(headers))
                .bodyValue(requestBody != null ? requestBody : "")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        LOGGER.info("URL: " + url);
        LOGGER.info("TWITCH API Response: " + response);
        try {
            Desktop.getDesktop().browse(new URI(url));
//
//            Pattern pattern = Pattern.compile("href=\"(.*?)\"");
//            Matcher matcher = pattern.matcher(response);
//            if (matcher.find()) {
//                String hrefUrl = matcher.group(1);
//                LOGGER.info("HREF URL: "+ hrefUrl);
//                Desktop.getDesktop().browse(new URI(hrefUrl));
//                LOGGER.info("DONE");
//            }
        } catch (Exception ex) {
            LOGGER.error("", ex);
        }
        int i = 0;
    }
}
