package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.config.RedisConfig;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Granularity;
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
@Import({
        RedisConfig.class,
        TwitchChatRedisService.class,
})
@Testcontainers
@Tag("Module5")
public class TwitchChatRedisServiceTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");

    // Dynamically configure Redis connection for Spring
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TwitchChatRedisService service;

//    @BeforeAll
//    static void startContainer() {
//        REDIS_CONTAINER.start();
//    }

    @BeforeEach
    void resetContainer() {
        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
    }

//    @AfterAll
//    static void stopContainer() {
//        REDIS_CONTAINER.stop();
//    }

    @Test
    // TODO: add the @Disabled tag here in the main branch
    void incrementMinuteHitCounterTest() {
        long eventTs1 = 1767231055000L; // Thu Jan 01 2026 01:30:55 GMT+0000
        long eventTs2 = 1767225645000L; // Thu Jan 01 2026 00:00:45 GMT+0000
        long eventTs3 = 1767225745000L; // Thu Jan 01 2026 00:02:25 GMT+0000

        boolean output1 = service.incrementHitCounter("s0mcs", eventTs1);
        boolean output2 = service.incrementHitCounter("s0mcs", eventTs2);
        boolean output3 = service.incrementHitCounter("s0mcs", eventTs3);

        assertTrue(output1);
        assertTrue(output2);
        assertTrue(output3);

        Long minute1 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767225600000L))));
        Long minute2 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767225720000L))));
        Long minute3 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767231000000L))));
        Long hour1 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.HOUR, "s0mcs", 1767225600000L))));
        Long hour2 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.HOUR, "s0mcs", 1767229200000L))));
        Long day1 = Long.valueOf(Objects.requireNonNull(redisTemplate.opsForValue().get(String.format("%s#%s#%s", Granularity.DAY, "s0mcs", 1767225600000L))));

        assertEquals(1, minute1);
        assertEquals(1, minute2);
        assertEquals(1, minute3);
        assertEquals(2, hour1);
        assertEquals(1, hour2);
        assertEquals(3, day1);
    }

    @Test
    // TODO: add the @Disabled tag here in the main branch
    void getHitCountsTest() {
        long eventTs1 = 1767231055000L; // Thu Jan 01 2026 01:30:55 GMT+0000
        long eventTs2 = 1767225645000L; // Thu Jan 01 2026 00:00:45 GMT+0000
        long eventTs3 = 1767225745000L; // Thu Jan 01 2026 00:02:25 GMT+0000

        service.incrementHitCounter("s0mcs", eventTs1);
        service.incrementHitCounter("s0mcs", eventTs2);
        service.incrementHitCounter("s0mcs", eventTs3);

        Map<String, Long> output1 = service.getHitCounts(
                Granularity.MINUTE,
                "s0mcs",
                1767225600000L,  // 20260101 00:00:00 UTC
                1767311940000L);              // 20260101 23:59:00 UTC
        Assertions.assertThat(output1).hasSize(3).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767225600000L), 1L,
                        String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767225720000L), 1L,
                        String.format("%s#%s#%s", Granularity.MINUTE, "s0mcs", 1767231000000L), 1L
                )
        );

        Map<String, Long> output2 = service.getHitCounts(
                Granularity.HOUR,
                "s0mcs",
                1767225600000L,  // 20260101 00:00:00 UTC
                1767311940000L);              // 20260101 23:59:00 UTC
        Assertions.assertThat(output2).hasSize(2).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        String.format("%s#%s#%s", Granularity.HOUR, "s0mcs", 1767225600000L), 2L,
                        String.format("%s#%s#%s", Granularity.HOUR, "s0mcs", 1767229200000L), 1L
                )
        );

        Map<String, Long> output3 = service.getHitCounts(
                Granularity.DAY,
                "s0mcs",
                1767225600000L,  // 20260101 00:00:00 UTC
                1767311940000L);              // 20260101 23:59:00 UTC
        Assertions.assertThat(output3).hasSize(1).containsExactlyInAnyOrderEntriesOf(
                Map.of(
                        String.format("%s#%s#%s", Granularity.DAY, "s0mcs", 1767225600000L), 3L
                )
        );
    }
}
