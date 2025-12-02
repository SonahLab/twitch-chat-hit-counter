//package com.sonahlab.twitch_chat_hit_counter_course.redis;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.redis.testcontainers.RedisContainer;
//import com.sonahlab.twitch_chat_hit_counter_course.config.RedisConfig;
//import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@DataRedisTest
//@Import({
//        RedisConfig.class,
//        TwitchChatRedisService.class,
//})
//@Testcontainers
//@Tag("Module5")
//public class TwitchChatRedisServiceTest {
//
//    @Container
//    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");
//
//    // Dynamically configure Redis connection for Spring
//    @DynamicPropertySource
//    static void redisProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
//        registry.add("spring.data.redis.port", REDIS_CONTAINER::getFirstMappedPort);
//    }
//
//    @Autowired
//    private RedisTemplate<String, String> redisTemplate;
//
//    @Autowired
//    private TwitchChatRedisService twitchChatRedisService;
//
//    @BeforeAll
//    static void startContainer() {
//        REDIS_CONTAINER.start();
//    }
//
//    @BeforeEach
//    void resetContainer() {
//        redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
//    }
//
//    @AfterAll
//    static void stopContainer() {
//        REDIS_CONTAINER.stop();
//    }
//
//    @Test
//        // TODO: add the @Disabled tag here in the main branch
//    void incrementMinuteHitCounterTest() throws JsonProcessingException {
//        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
//        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
//        GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");
//
//        Long output1 = greetingRedisService.addGreetingToFeed(event1);
//        Long output2 = greetingRedisService.addGreetingToFeed(event2);
//        Long output3 = greetingRedisService.addGreetingToFeed(event3);
//
//        assertEquals(1, output1);
//        assertEquals(2, output2);
//        assertEquals(1, output3);
//    }
//
//    @Test
//        // TODO: add the @Disabled tag here in the main branch
//    void getHitCountsTest() throws JsonProcessingException {
//        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
//        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
//        GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");
//
//        greetingRedisService.addGreetingToFeed(event1);
//        greetingRedisService.addGreetingToFeed(event2);
//        greetingRedisService.addGreetingToFeed(event3);
//
//        List<GreetingEvent> output1 = greetingRedisService.getGreetingFeed("Bob");
//        List<GreetingEvent> output2 = greetingRedisService.getGreetingFeed("David");
//        List<GreetingEvent> output3 = greetingRedisService.getGreetingFeed("Alice");
//
//        Assertions.assertThat(output1).hasSize(2).containsExactly(event1, event2);
//        Assertions.assertThat(output2).hasSize(1).containsExactly(event3);
//        Assertions.assertThat(output3).hasSize(0);
//    }
//}
