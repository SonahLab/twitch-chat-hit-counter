package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private GreetingRedisService greetingRedisService;

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
    void addGreetingToFeedTest() throws JsonProcessingException {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
        GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");

        Long output1 = greetingRedisService.addGreetingToFeed(event1);
        Long output2 = greetingRedisService.addGreetingToFeed(event2);
        Long output3 = greetingRedisService.addGreetingToFeed(event3);

        assertEquals(1, output1);
        assertEquals(2, output2);
        assertEquals(1, output3);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void getGreetingFeedTest() throws JsonProcessingException {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
        GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");

        greetingRedisService.addGreetingToFeed(event1);
        greetingRedisService.addGreetingToFeed(event2);
        greetingRedisService.addGreetingToFeed(event3);

        List<GreetingEvent> output1 = greetingRedisService.getGreetingFeed("Bob");
        List<GreetingEvent> output2 = greetingRedisService.getGreetingFeed("David");
        List<GreetingEvent> output3 = greetingRedisService.getGreetingFeed("Alice");

        Assertions.assertThat(output1).hasSize(2).containsExactly(event1, event2);
        Assertions.assertThat(output2).hasSize(1).containsExactly(event3);
        Assertions.assertThat(output3).hasSize(0);
    }
}
