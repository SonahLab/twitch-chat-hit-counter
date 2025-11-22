package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OAuthRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRedisService.class);
    private static final String KEY_TEMPLATE = "twitch#token#%s";
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    private RedisDao redisDao;

    // Constructor
    public OAuthRedisService(@Qualifier("oauthTokenRedisDao") RedisDao redisDao) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.redisDao = redisDao;
    }

    public void updateLatestToken(String username, Map<String, Object> tokenResponse) throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        String key = String.format(KEY_TEMPLATE, username);
        OAuth2Credential oAuth2Credential = new OAuth2Credential(
                "twitch",
                tokenResponse.get("access_token").toString(),
                tokenResponse.get("refresh_token").toString(),
                null,
                null,
                (Integer) tokenResponse.get("expires_in"),
                (List<String>) tokenResponse.get("scope")
        );
        redisDao.set(key, MAPPER.writeValueAsString(oAuth2Credential));

        LOGGER.info("Updated latest token for username={}, token={}", username, tokenResponse);
    }

    public OAuth2Credential getAccessToken(String username) throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        String key = String.format(KEY_TEMPLATE, username);
        String token = redisDao.get(key);
        LOGGER.info("Retrieved access token for username={}, token={}", username, token);

        JsonNode node = MAPPER.readTree(token);
        String provider = node.get("identityProvider").asText();
        String accessToken = node.get("accessToken").asText();
        String refreshToken = node.has("refreshToken") && !node.get("refreshToken").isNull()
                ? node.get("refreshToken").asText()
                : null;
        Integer expiresIn = node.get("expiresIn").asInt();
        List<String> scopes = new ArrayList<>();
        node.get("scopes").forEach(x -> scopes.add(x.asText()));

        return new OAuth2Credential(provider, accessToken, refreshToken, null, null, expiresIn, scopes);
    }
}
