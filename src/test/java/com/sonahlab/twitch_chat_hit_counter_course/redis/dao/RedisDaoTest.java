package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@Tag("Module4")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
@Disabled
public class RedisDaoTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");

    // Dynamically configure Redis connection for Spring
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

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

        @Bean
        public RedisDao redisDao(RedisTemplate<String, String> redisTemplate) {
            return new RedisDao(redisTemplate);
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
    void incrementTest() {
        String key = "incrementKey";

        redisDao.increment(key);
        assertEquals(1, redisDao.get(key).intValue());
    }

    @Test
    void incrementByTest() {
        String key = "incrementByKey";
        long count = 1000L;

        redisDao.incrementBy(key, count);
        assertEquals(count, redisDao.get(key).longValue());
    }

    @Test
    void setAndGetTest() {
        String key = "setAndGetKey";
        long count = 1000L;

        assertNull(redisDao.get(key));
        redisDao.set(key, count);
        assertEquals(count, redisDao.get(key).longValue());
    }

    @Test
    void keysTest() {
        Map<String, Long> actual = redisDao.keys("nonExistentPrefix");

        assertEquals(0, actual.size());

        redisDao.increment("USER#user1");
        redisDao.incrementBy("USER#user2", 100L);
        redisDao.set("USER#user3", 999L);

        actual = redisDao.keys("USER#*");
        assertEquals(3, actual.size());
        assertEquals(1L, actual.get("USER#user1").longValue());
        assertEquals(100L, actual.get("USER#user2").longValue());
        assertEquals(999L, actual.get("USER#user3").longValue());
    }

    @Test
    void listTest() {
        String key = "listKey";
        List<String> expected = List.of("Apple", "Banana");

        for (String item : expected) {
            redisDao.listAdd(key, item);
        }

        List<String> actual = redisDao.listGet(key);

        // Validate list count matches the expected list count
        assertEquals(2, actual.size());

        // Validate each string in the expected list appears in the redis list
        for (String item : expected) {
            assertTrue(actual.contains(item));
        }
    }

    @Test
    void setTest() {
        String key = "setKey";
        List<String> users = List.of(
                "Alice",
                "Alice", // This will be deduped in Redis since we are dealing with a Set object
                "Bob",
                "Charlie");

        for (String user : users) {
            redisDao.setAdd(key, user);
        }

        Set<String> actual = redisDao.setMembers(key);

        assertEquals(3, actual.size());
        assertTrue(actual.contains("Alice"));
        assertTrue(actual.contains("Bob"));
        assertTrue(actual.contains("Charlie"));

        redisDao.setRemove(key, "Alice");
        actual = redisDao.setMembers(key);

        assertEquals(2, actual.size());
        assertTrue(actual.contains("Bob"));
        assertTrue(actual.contains("Charlie"));
    }
}
