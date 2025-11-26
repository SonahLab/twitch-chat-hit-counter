package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.redis.testcontainers.RedisContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Tag("Module5")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
@Disabled
public class TwitchChatRedisServiceTest {

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
    private TwitchChatRedisService twitchChatRedisService;

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
    void incrementMinuteHitCounterTest() {
        Long output1 = twitchChatRedisService.incrementMinuteHitCounter("s0mcs", 0L);
        Long output2 = twitchChatRedisService.incrementMinuteHitCounter("s0mcs", 500L);
        Long output3 = twitchChatRedisService.incrementMinuteHitCounter("shroud", 61234L);

        assertEquals(1, output1);
        assertEquals(2, output2);
        assertEquals(1, output3);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void getHitCountsTest() {
        twitchChatRedisService.incrementMinuteHitCounter("s0mcs", 0L);
        twitchChatRedisService.incrementMinuteHitCounter("s0mcs", 500L);
        twitchChatRedisService.incrementMinuteHitCounter("shroud", 61234L);

        Map<String, Long> output1 = twitchChatRedisService.getHitCounts("s0mcs");
        Map<String, Long> output2 = twitchChatRedisService.getHitCounts("s0mcs");
        Map<String, Long> output3 = twitchChatRedisService.getHitCounts("shroud");

        Assertions.assertThat(output1).containsEntry("s0mcs#0", 1L);
        Assertions.assertThat(output2).containsEntry("s0mcs#0", 2L);
        Assertions.assertThat(output3).containsEntry("shroud#60000", 1L);
    }
}
