package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.config.RedisConfig;
import com.sonahlab.twitch_chat_hit_counter_course.model.Granularity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataRedisTest
@Import({RedisConfig.class, TwitchChatRedisService.class})
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
        Long output1 = twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", 0L);
        Long output2 = twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", 500L);
        Long output3 = twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "shroud", 61234L);

        assertEquals(1, output1);
        assertEquals(2, output2);
        assertEquals(1, output3);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void getHitCountsTest() {
        long eventTs1 = 1767254439000L; // Thu Jan 01 2026 08:00:39 GMT+0000
        long eventTs2 = 1767254445000L; // Thu Jan 01 2026 08:00:45 GMT+0000
        long eventTs3 = 1767254545000L; // Thu Jan 01 2026 08:02:25 GMT+0000
        long eventTs4 = 1767340800000L; // Fri Jan 02 2026 00:00:00 GMT+0000

        twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", eventTs1);
        twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", eventTs2);
        twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", eventTs3);
        twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "s0mcs", eventTs4);

        twitchChatRedisService.incrementMinuteHitCounter(Granularity.MINUTE, "shroud", eventTs1);

        Map<String, Long> output1 = twitchChatRedisService.getHitCounts(
                Granularity.MINUTE,
                "s0mcs",
                20260101);
        Map<String, Long> output2 = twitchChatRedisService.getHitCounts(
                Granularity.MINUTE,
                "s0mcs",
                20260102);
        Map<String, Long> output3 = twitchChatRedisService.getHitCounts(
                Granularity.MINUTE,
                "shroud",
                20260101);

        Assertions.assertThat(output1).hasSize(2).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "s0mcs#1767254400000", 2L,
                        "s0mcs#1767254525000", 1L
                )
        );
        Assertions.assertThat(output2).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "s0mcs#1767340800000", 1L
                )
        );
        Assertions.assertThat(output3).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "shroud#1767254400000", 1L
                )
        );
    }
}
