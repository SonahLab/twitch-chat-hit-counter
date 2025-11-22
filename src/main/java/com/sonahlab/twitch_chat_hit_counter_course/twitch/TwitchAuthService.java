package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.twitch4j.common.exception.UnauthorizedException;
import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import com.sonahlab.twitch_chat_hit_counter_course.redis.OAuthRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.UserUtils.USERNAME;

/**
 * Manages the lifecycle of Twitch OAuth2 access tokens for the chat hit counter application.
 *
 * <p>This class is responsible for:
 * <ul>
 *   <li>Creating initial access and refresh tokens via the OAuth2 Authorization Code Flow</li>
 *   <li>Refreshing expired access tokens using the refresh token</li>
 *   <li>Validating the current access token against Twitch's {@code /oauth2/validate} endpoint</li>
 * </ul>
 * </p>
 *
 * <p><strong>Important:</strong> Twitch recommends <strong>reacting to 401 Unauthorized</strong> responses
 * rather than relying on {@code expires_in}. This class supports both proactive validation and
 * reactive refresh on HTTP 401 or IRC login failure.</p>
 *
 * <p>All methods throw unchecked exceptions on critical failures (e.g., invalid refresh token)
 * to force immediate handling and re-authentication.</p>
 *
 * @see <a href="https://dev.twitch.tv/docs/authentication">Twitch OAuth Documentation</a>
 * @see <a href="https://dev.twitch.tv/docs/authentication/refresh-tokens">Refresh Tokens</a>
 * @since 1.0
 */
@Service
public class TwitchAuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchAuthService.class);
    private static final String SCHEME = "https";
    private static final String HOST = "id.twitch.tv";

    private static final String REDIRECT_URI = "http://localhost:8080/oauth2/callback";
    private static final List<String> SCOPE = List.of("chat:read");

    private static final String BASE_AUTH_URL = SCHEME + "://" + HOST;
    private static final String AUTH_ENDPOINT ="/oauth2/authorize";
    private static final String TOKEN_ENDPOINT ="/oauth2/token";
    private static final String VALIDATE_ENDPOINT ="/oauth2/validate";

    private TwitchConfig twitchConfig;
    private OAuthRedisService oAuthRedisService;

    // Constructor
    public TwitchAuthService(TwitchConfig twitchConfig,
                             OAuthRedisService oAuthRedisService) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.twitchConfig = twitchConfig;
        this.oAuthRedisService = oAuthRedisService;
    }

    /**
     * <p>This method is called from your {@code /oauth2/authorize} endpoint via Swagger, it will return the filled in
     * authorize URL that you need to input into your browser to kick off the OAuth Token process.</p><br>
     *
     * <p>
     * https://id.twitch.tv/oauth2/authorize?
     *         response_type=code
     *         &client_id={client_id}
     *         &redirect_uri={redirect_uri}
     *         &scope={scope(s)}
     *         &state={state}
     * </p>
     *
     * Initiates the OAuth2 Authorization Code Flow to obtain initial access and refresh tokens.
     * After user approval, Twitch redirects to your callback endpoint (e.g., {@code /oauth2/callback})
     * with an authorization {@code code}. That code must be exchanged for tokens via a backend call.</p>
     *
     * @return the full Twitch authorization URL with required parameters
     */
    public String getAuthUrl(String state) {
        /**
         * TODO: Implement in Module 5
         */
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(SCHEME)
                .host(HOST)
                .path(AUTH_ENDPOINT)
                .queryParam("response_type", "code")
                .queryParam("client_id", twitchConfig.getTwitchApiClientId())
                .queryParam("redirect_uri", REDIRECT_URI)
                .queryParam("scope", String.join(" ", SCOPE))
                .queryParam("state", state);
        return builder.toUriString();
    }

    /**
     * Exchanges an authorization code for access and refresh tokens using the OAuth2 Authorization Code Grant Flow.
     *
     * <p>This method is called from your {@code /oauth2/callback} endpoint after Twitch redirects the user back
     * with a temporary {@code code} parameter. It performs a secure server-side POST request to:</p>
     *
     * <pre>
     * POST https://id.twitch.tv/oauth2/token
     * </pre>
     *
     * <p><strong>Required Parameters (as per <a href="https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/#authorization-code-grant-flow">Twitch Docs</a>):</strong></p>
     * <ul>
     *   <li><strong>client_id</strong>: Your app’s registered client ID.</li>
     *   <li><strong>client_secret</strong>: Your app’s registered client secret. <em>Never expose in frontend!</em></li>
     *   <li><strong>code</strong>: The temporary authorization code from the {@code /authorize} redirect.</li>
     *   <li><strong>grant_type</strong>: Must be {@code authorization_code}.</li>
     *   <li><strong>redirect_uri</strong>: Must exactly match one of your app’s registered redirect URIs.</li>
     * </ul>
     *
     * <p>On success, Twitch returns:</p>
     * <pre>
     * {
     *   "access_token": "rfx2uswqe8l4g1mkagrvg5tv0ks3",
     *   "expires_in": 14124,
     *   "refresh_token": "5b93chm6hdve3mycz05zfzatkfdenfspp1h1ar2xxdalen01",
     *   "scope": [
     *     "channel:moderate",
     *     "chat:edit",
     *     "chat:read"
     *   ],
     *   "token_type": "bearer"
     * }
     * </pre>
     *
     * <p>The {@code access_token} is used for API and chat authentication.
     * The {@code refresh_token} must be stored securely and used to obtain new access tokens when expired.</p>
     *
     * <p><strong>Security Note:</strong> This method must be called from a <strong>backend server</strong> only.
     * The {@code client_secret} must never be exposed in client-side code.</p>
     *
     * @param authorizationCode the temporary authorization code received from Twitch after user approval
     * @return a String containing the access token, refresh token, expiry, and granted scopes
     * @see <a href="https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/#authorization-code-grant-flow">
     *      Twitch Authorization Code Grant Flow</a>
     */
    public Map<String, Object> createOAuthToken(String authorizationCode) throws JsonProcessingException {
        /**
         * TODO: Implement in Module 5
         * */
        RestClient restClient = RestClient.create(URI.create(BASE_AUTH_URL + TOKEN_ENDPOINT));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", twitchConfig.getTwitchApiClientId());
        body.add("client_secret", twitchConfig.getTwitchApiClientSecret());
        body.add("code", authorizationCode);
        body.add("grant_type", "authorization_code");
        body.add("redirect_uri", REDIRECT_URI);

        try {
            Map<String, Object> tokenResponse = restClient.post()
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        LOGGER.error("Status Code: {}, res: {}", res.getStatusCode(), res.getStatusText());
                        throw new UnauthorizedException();
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(String.format("Status Code: %s, res: %s", res.getStatusCode(), res.getStatusText()));
                    })
                    .body(new ParameterizedTypeReference<>() {});
            LOGGER.info("Twitch OAuth Token parameters: {}", tokenResponse);

            oAuthRedisService.updateLatestToken(USERNAME, tokenResponse);
            return tokenResponse;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Refreshes an expired access token using the OAuth2 Refresh Token Grant Flow.
     *
     * <p>This method is called when the current access token is invalid or expired (typically detected
     * via a 401 Unauthorized response from any Twitch API endpoint or IRC login failure).</p>
     *
     * <p>It performs a secure server-side POST request to:</p>
     *
     * <pre>
     * POST https://id.twitch.tv/oauth2/token
     * </pre>
     *
     * <p><strong>Required Parameters (as per <a href="https://dev.twitch.tv/docs/authentication/refresh-tokens">Twitch Refresh Token Docs</a>):</strong></p>
     * <ul>
     *   <li><strong>client_id</strong>: Your app’s registered client ID.</li>
     *   <li><strong>client_secret</strong>: Your app’s registered client secret. <em>Never expose in frontend!</em></li>
     *   <li><strong>grant_type</strong>: Must be {@code refresh_token}.</li>
     *   <li><strong>refresh_token</strong>: The current valid refresh token (stored from initial auth or previous refresh).</li>
     * </ul>
     *
     * <p>On success, Twitch returns:</p>
     * <pre>
     * {
     *   "access_token": "1ssjqsqfy6bads1ws7m03gras79zfr",
     *   "refresh_token": "eyJfMzUtNDU0OC4MWYwLTQ5MDY5ODY4NGNlMSJ9%asdfasdf=",
     *   "scope": [
     *     "channel:read:subscriptions",
     *     "channel:manage:polls"
     *   ],
     *   "token_type": "bearer"
     * }
     * </pre>
     *
     * @param refreshToken token that is granted on the initially created OAuth Token
     * @return a String containing the new access token, new refresh token, expiry, and scopes
     * @see <a href="https://dev.twitch.tv/docs/authentication/refresh-tokens">Twitch Refresh Tokens</a>
     */
    public Map<String, Object> refreshOAuthToken(String refreshToken) {
        /**
         * TODO: Implement in Module 5
         */
        RestClient restClient = RestClient.create(URI.create(BASE_AUTH_URL + TOKEN_ENDPOINT));

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", twitchConfig.getTwitchApiClientId());
        body.add("client_secret", twitchConfig.getTwitchApiClientSecret());
        body.add("grant_type", "refresh_token");
        body.add("refresh_token", refreshToken);

        try {
            Map<String, Object> tokenResponse = restClient.post()
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(body)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        LOGGER.error("Status Code: {}, res: {}", res.getStatusCode(), res.getStatusText());
                        throw new UnauthorizedException();
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(String.format("Status Code: %s, res: %s", res.getStatusCode(), res.getStatusText()));
                    })
                    .body(new ParameterizedTypeReference<>() {});
            LOGGER.info("Twitch OAuth Token parameters: {}", tokenResponse);

            oAuthRedisService.updateLatestToken(USERNAME, tokenResponse);
            return tokenResponse;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Validates the current access token by calling Twitch's OAuth2 token validation endpoint.
     *
     * <p>This method performs a lightweight GET request to:</p>
     *
     * <pre>
     * GET https://id.twitch.tv/oauth2/validate
     * Authorization: OAuth &lt;access_token&gt;
     * </pre>
     *
     * <p><strong>Response (per <a href="https://dev.twitch.tv/docs/authentication/validate-tokens">Twitch Validate Tokens Docs</a>):</strong></p>
     * <ul>
     *   <li><strong>200 OK</strong>: Token is valid. Returns:
     *     <pre>
     * {
     *   "client_id": "wbmytr93xzw8zbg0p1izqyzzc5mbiz",
     *   "login": "twitchdev",
     *   "scopes": [
     *     "channel:read:subscriptions"
     *   ],
     *   "user_id": "141981764",
     *   "expires_in": 5520838
     * }
     *     </pre>
     *   </li>
     *   <li><strong>401 Unauthorized</strong>: Token is expired, revoked, or invalid. Returns:
     *      <pre>
     * {
     *   "status": 401,
     *   "message": "invalid access token"
     * }
     *      </pre>
     *   </li>
     * </ul>

     * <p><strong>Rate Limits:</strong> This endpoint is <strong>not rate-limited</strong> for your client ID and is safe to call frequently.</p>
     *
     * @param accessToken the temporary authorization code received from Twitch
     * @return {@code true} if the token is valid and not expired; {@code false} if 401 is received
     * @see <a href="https://dev.twitch.tv/docs/authentication/validate-tokens">Validate Tokens</a>
     */
    public boolean validateOAuthToken(String accessToken) {
        /**
         * TODO: Implement in Module 5
         * */
        RestClient restClient = RestClient.create(URI.create(BASE_AUTH_URL + VALIDATE_ENDPOINT));
        try {
            Map<String, Object> response = restClient.get()
                    .header("Authorization", "OAuth " + accessToken)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        LOGGER.error("Status Code: {}, res: {}", res.getStatusCode(), res.getStatusText());
                        throw new UnauthorizedException();
                    })
                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                        throw new RuntimeException(String.format("Status Code: %s, res: %s", res.getStatusCode(), res.getStatusText()));
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            LOGGER.info("Twitch OAuth Token VALIDATE Response: {}", response);
            return true;
        } catch (UnauthorizedException e) {
            return false;
        }
    }
}