package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Tag("Module5")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
@Disabled
public class OAuthRedisServiceTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");

    // Dynamically configure Redis connection for Spring
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private OAuthRedisService oAuthRedisService;

    @BeforeAll
    static void startContainer() {
        REDIS_CONTAINER.start();
    }

    @BeforeEach
    void resetContainer() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }

    @AfterAll
    static void stopContainer() {
        REDIS_CONTAINER.stop();
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void updateLatestToken_and_getAccessTokenTest() throws JsonProcessingException {
        OAuth2Credential credential = new OAuth2Credential(
                "twitch",
                "accessToken",
                "refreshToken",
                null,
                null,
                14124,
                List.of("chat:read"));
        oAuthRedisService.updateLatestToken("Alice", credential);

        OAuth2Credential actual = oAuthRedisService.getAccessToken("Alice");
        assertEquals("twitch", actual.getIdentityProvider());
        assertEquals("accessToken", actual.getAccessToken());
        assertEquals("refreshToken", actual.getRefreshToken());
        assertNull(actual.getUserId());
        assertNull(actual.getUserName());
        assertEquals(14124, actual.getExpiresIn());
        assertTrue(actual.getScopes().contains("chat:read"));
    }
}
