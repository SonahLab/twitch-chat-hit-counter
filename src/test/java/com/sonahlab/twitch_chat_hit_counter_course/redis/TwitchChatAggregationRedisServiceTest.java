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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataRedisTest
@Import({RedisConfig.class, TwitchChatAggregationRedisService.class})
@Testcontainers
@Tag("Module5")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
@Disabled
public class TwitchChatAggregationRedisServiceTest {

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
    private TwitchChatAggregationRedisService twitchChatAggregationRedisService;

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
    void incrementHitCounterTest() {
        long eventTs1 = 1767231010000L; // Thu Jan 01 2026 01:30:10 GMT+0000
        long eventTs2 = 1767231055000L; // Thu Jan 01 2026 01:30:55 GMT+0000
        boolean output1 = twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs1);
        boolean output2 = twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs2);
        boolean output3 = twitchChatAggregationRedisService.incrementHitCounter("shroud", eventTs1);

        assertTrue(output1);
        assertTrue(output2);
        assertTrue(output3);
        assertEquals(2, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.MINUTE + "#s0mcs#" + 1767231000000L))));
        assertEquals(2, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.HOUR + "#s0mcs#" + 1767229200000L))));
        assertEquals(2, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.DAY + "#s0mcs#" + 1767225600000L))));
        assertEquals(1, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.MINUTE + "#shroud#" + 1767231000000L))));
        assertEquals(1, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.HOUR + "#shroud#" + 1767229200000L))));
        assertEquals(1, Long.valueOf(Objects.requireNonNull(
                redisTemplate.opsForValue().get(Granularity.DAY + "#shroud#" + 1767225600000L))));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void getHitCountsTest() {
        long eventTs1 = 1767254439000L; // Thu Jan 01 2026 08:00:39 GMT+0000
        long eventTs2 = 1767254445000L; // Thu Jan 01 2026 08:00:45 GMT+0000
        long eventTs3 = 1767258145000L; // Thu Jan 01 2026 09:02:25 GMT+0000
        long eventTs4 = 1767340800000L; // Fri Jan 02 2026 00:00:00 GMT+0000

        twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs1);
        twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs2);
        twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs3);
        twitchChatAggregationRedisService.incrementHitCounter("s0mcs", eventTs4);

        twitchChatAggregationRedisService.incrementHitCounter("shroud", eventTs1);

        Map<String, Long> output1 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.MINUTE,
                "s0mcs",
                1767254400000L,
                1767340800000L);
        Map<String, Long> output2 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.HOUR,
                "s0mcs",
                1767254400000L,
                1767340800000L);
        Map<String, Long> output3 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.DAY,
                "s0mcs",
                1767254400000L,
                1767340800000L);

        Assertions.assertThat(output1).hasSize(3).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "s0mcs#1767254400000", 2L,
                        "s0mcs#1767258000000", 1L,
                        "s0mcs#1767340800000", 1L
                )
        );
        Assertions.assertThat(output2).hasSize(3).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "s0mcs#1767254400000", 2L,
                        "s0mcs#1767258000000", 1L,
                        "s0mcs#1767340800000", 1L
                )
        );
        Assertions.assertThat(output3).hasSize(2).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "s0mcs#1767225600000", 3L,
                        "s0mcs#1735776000000", 1L
                )
        );

        Map<String, Long> output4 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.MINUTE,
                "shroud",
                1767254400000L,
                1767340800000L);
        Map<String, Long> output5 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.HOUR,
                "shroud",
                1767254400000L,
                1767340800000L);
        Map<String, Long> output6 = twitchChatAggregationRedisService.getHitCounts(
                Granularity.DAY,
                "shroud",
                1767254400000L,
                1767340800000L);
        Assertions.assertThat(output4).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "shroud#1767254400000", 1L
                )
        );
        Assertions.assertThat(output5).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "shroud#1767254400000", 1L
                )
        );
        Assertions.assertThat(output6).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        "shroud#1767225600000", 1L
                )
        );
    }
}
