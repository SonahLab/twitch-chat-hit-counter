package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OAuthRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRedisService.class);

    // Constructor
    public OAuthRedisService() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public void updateLatestToken(String username, OAuth2Credential oAuth2Credential) {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public OAuth2Credential getAccessToken(String username) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
