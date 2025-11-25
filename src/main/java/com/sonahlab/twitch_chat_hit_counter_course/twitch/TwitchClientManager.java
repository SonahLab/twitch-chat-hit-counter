package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TwitchClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchClientManager.class);

    // Constructor
    public TwitchClientManager() {
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public TwitchClient getTwitchClient() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    public void refreshCredential(OAuth2Credential newCredential) {
        /**
         * TODO: Implement as part of Module 5
         * */
    }
}
