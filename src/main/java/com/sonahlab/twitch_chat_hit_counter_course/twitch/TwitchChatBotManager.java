package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.TwitchChatEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.OAuthRedisService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.OptionalInt;
import java.util.Set;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.UserUtils.USERNAME;

/**
 * Service responsible for interacting with the Twitch Chat Bot API Client.
 */
@Service
public class TwitchChatBotManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    private TwitchConfig config;
    private OAuthRedisService oauthRedisService;
    private TwitchAuthService twitchAuthService;
    private TwitchClient twitchClient;
    private TwitchChatEventProducer twitchChatEventProducer;

    // Constructor
    public TwitchChatBotManager(TwitchConfig config,
                                OAuthRedisService oauthRedisService,
                                TwitchAuthService twitchAuthService,
                                TwitchChatEventProducer twitchChatEventProducer) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.config = config;
        this.oauthRedisService = oauthRedisService;
        this.twitchAuthService = twitchAuthService;
        this.twitchChatEventProducer = twitchChatEventProducer;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        validateAndRefreshUserOAuth();
        twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, this::handleMessage);

        initChannelsToJoin();
    }

    public void joinChannel(String channelName) throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        validateAndRefreshUserOAuth();
        twitchClient.getChat().joinChannel(channelName);
    }

    public boolean leaveChannel(String channelName) throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        validateAndRefreshUserOAuth();
        return twitchClient.getChat().leaveChannel(channelName);
    }

    private void handleMessage(ChannelMessageEvent event) {
        LOGGER.debug("Processing event: " + event);

        OptionalInt subscriberMonths = event.getMessageEvent().getSubscriberMonths();
        OptionalInt subscriptionTier = event.getMessageEvent().getSubscriptionTier();

        TwitchChatEvent twitchChatEvent = new TwitchChatEvent(
                event.getEventId(),
                event.getMessage(),
                event.getMessageEvent().getFiredAt().getTimeInMillis(),
                event.getUser().getId(),
                event.getUser().getName(),
                event.getChannel().getId(),
                event.getChannel().getName(),
                subscriberMonths.isPresent() ? subscriberMonths.getAsInt() : 0,
                subscriptionTier.isPresent() ? subscriptionTier.getAsInt() : 0);
        LOGGER.info("Processing TwitchChatEvent={}", twitchChatEvent);

        boolean published = twitchChatEventProducer.publish(twitchChatEvent.eventId(), twitchChatEvent);
        LOGGER.debug("Published={} eventId={} to kafka topic", published, twitchChatEvent.eventId());
    }

    private void initChannelsToJoin() throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        validateAndRefreshUserOAuth();

        Set<String> channels = new HashSet<>() {{
            add("s0mcs");
//            add("Demon1");
//            add("k3soju");
        }};
        for (String channelName : channels) {
            twitchClient.getChat().joinChannel(channelName);
        }
    }

    private void validateAndRefreshUserOAuth() throws JsonProcessingException {
        OAuth2Credential oAuth2Credential = oauthRedisService.getAccessToken(USERNAME);
        if (oAuth2Credential == null) {
            LOGGER.error("No access token found for username={}, authorize first!", USERNAME);
            throw new RuntimeException(String.format("No access token found for username=%s, authorize first!", USERNAME));
        }

        boolean isValidToken = twitchAuthService.validateOAuthToken(oAuth2Credential.getAccessToken());
        LOGGER.info("Validated accessToken={}, isValid={}", oAuth2Credential.getAccessToken(), isValidToken);
        if (isValidToken) {
            if (twitchClient == null) {
                LOGGER.info("Initializing {} twitchClient and an already valid token", this.getClass().getSimpleName());
                refreshTwitchClient(oAuth2Credential);
            }
        } else {
            LOGGER.info("Refreshing TwitchClient with a fresh token", this.getClass().getSimpleName());
            twitchAuthService.refreshOAuthToken(oAuth2Credential.getRefreshToken());
            oAuth2Credential = oauthRedisService.getAccessToken(USERNAME);
            refreshTwitchClient(oAuth2Credential);
        }
    }

    private void refreshTwitchClient(OAuth2Credential oAuth2Credential) {
        twitchClient = TwitchClientBuilder.builder()
                .withEnableHelix(true)
                .withEnableChat(true)
                .withChatAccount(oAuth2Credential)
                .withClientId(config.getTwitchApiClientId())
                .withClientSecret(config.getTwitchApiClientSecret())
                .build();
    }
}
