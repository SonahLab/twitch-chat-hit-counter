package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.junit.jupiter.api.AfterAll;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Tag("Module4")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
@Disabled
public class GreetingRedisServiceTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");

    // Dynamically configure Redis connection for Spring
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    @Autowired
    private GreetingRedisService greetingRedisService;

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

        @Bean(name = "greetingRedisDao") // Match @Qualifier
        public RedisDao greetingRedisDao(RedisTemplate<String, String> redisTemplate) {
            return new RedisDao(redisTemplate);
        }

        @Bean
        public GreetingRedisService greetingRedisService(@Qualifier("greetingRedisDao") RedisDao redisDao, ObjectMapper objectMapper) {
            // TODO: Update when RedisConfig.java is implemented
            return new GreetingRedisService();
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
    void addAndGetFeedTest() throws JsonProcessingException {
        GreetingEvent event = new GreetingEvent("id1", "Alice", "Bob", "Hey Bob.");
        greetingRedisService.addGreetingToFeed(event);
        List<GreetingEvent> events = greetingRedisService.getGreetingFeed("Bob");

        assertEquals(1, events.size());
        GreetingEvent actual = events.get(0);
        assertEquals("id1", actual.eventId());
        assertEquals("Alice", actual.sender());
        assertEquals("Bob", actual.receiver());
        assertEquals("Hey Bob.", actual.message());
    }
}
