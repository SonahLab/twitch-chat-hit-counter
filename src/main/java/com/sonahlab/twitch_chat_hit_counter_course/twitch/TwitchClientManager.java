package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import com.sonahlab.twitch_chat_hit_counter_course.redis.OAuthRedisService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils.USERNAME;


@Component
public class TwitchClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchClientManager.class);

    private TwitchClient twitchClient;
    private OAuth2Credential oAuth2Credential;
    private OAuthRedisService oAuthRedisService;
    private TwitchConfig twitchConfig;

    public TwitchClientManager(TwitchConfig twitchConfig,
                               OAuthRedisService oAuthRedisService) {
        this.twitchConfig = twitchConfig;
        this.oAuthRedisService = oAuthRedisService;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        OAuth2Credential existingCredential = oAuthRedisService.getAccessToken(USERNAME);

        if (existingCredential != null) {
            oAuth2Credential = existingCredential;
        } else {
            LOGGER.warn("No existing credential found for user={}. Please go through the /authorize flow to issue a token.", USERNAME);
            oAuth2Credential = new OAuth2Credential("twitch", "");
        }

        this.twitchClient = TwitchClientBuilder.builder()
                .withClientId(twitchConfig.getTwitchApiClientId())
                .withClientSecret(twitchConfig.getTwitchApiClientSecret())
                .withEnableHelix(true)
                .withEnableChat(true)
                .withChatAccount(oAuth2Credential)
                .build();
    }

    public TwitchClient getTwitchClient() {
        return twitchClient;
    }

    public void refreshCredential(OAuth2Credential newCredential) {
        oAuth2Credential.updateCredential(newCredential);
    }
}
