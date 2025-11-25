package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.model.EventType;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Tag("Module4")
public class EventDeduperRedisServiceTest {

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
    private EventDeduperRedisService eventDeduperRedisService;

    @Autowired
    @Qualifier("eventDedupeRedisDao")
    private RedisDao redisDao;

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
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void processEventTest() {
        eventDeduperRedisService.processEvent(EventType.GREETING_EVENT, "id1");
        assertEquals(1L, Long.valueOf(redisDao.get("GREETING_EVENT#id1")));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void isDupeEventTest() {
        eventDeduperRedisService.processEvent(EventType.GREETING_EVENT, "id2");
        Assertions.assertFalse(eventDeduperRedisService.isDupeEvent(EventType.GREETING_EVENT, "id1"));
        Assertions.assertTrue(eventDeduperRedisService.isDupeEvent(EventType.GREETING_EVENT, "id2"));
    }
}
