package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
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
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    @Autowired
    private EventDeduperRedisService eventDeduperRedisService;

    @Autowired
    private RedisDao redisDao;

    // Configuration for RedisTemplate (used in tests)
    @Configuration
    static class TestConfig {
        @Bean
        public RedisConnectionFactory redisConnectionFactory() {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
            config.setHostName(REDIS_CONTAINER.getHost());
            config.setPort(REDIS_CONTAINER.getFirstMappedPort());
            return new LettuceConnectionFactory(config);
        }

        @Bean
        public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
            RedisTemplate<String, String> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new StringRedisSerializer());
            template.afterPropertiesSet();
            return template;
        }

        @Bean(name = "eventDedupeRedisDao") // Match @Qualifier
        public RedisDao eventDedupeRedisDao(RedisTemplate<String, String> redisTemplate) {
            return new RedisDao(redisTemplate);
        }

        @Bean
        public EventDeduperRedisService eventDedupeRedisService(@Qualifier("eventDedupeRedisDao") RedisDao redisDao, ObjectMapper objectMapper) {
            // TODO: Update when RedisConfig.java is implemented
            return new EventDeduperRedisService();
        }

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @BeforeAll
    static void startContainer() {
        REDIS_CONTAINER.start();
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
