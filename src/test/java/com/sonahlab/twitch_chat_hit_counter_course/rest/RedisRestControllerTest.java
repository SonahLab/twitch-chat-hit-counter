package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ExtendWith(SpringExtension.class)
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
@Disabled
@Tag("Module4")
public class RedisRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void getRedisGreetingFeedTest() throws Exception {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
        GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");

        redisDao.listAdd("receiver#Bob", event1.toString());
        redisDao.listAdd("receiver#Bob", event2.toString());
        redisDao.listAdd("receiver#David", event3.toString());

        List<GreetingEvent> result1 = callQueryGreetingFeedEndpoint("Bob");
        assertThat(result1).hasSize(2).containsExactly(event1, event2);


        List<GreetingEvent> result2 = callQueryGreetingFeedEndpoint("David");
        assertThat(result2).hasSize(1).containsExactly(event3);


        List<GreetingEvent> result3 = callQueryGreetingFeedEndpoint("Alice");
        assertThat(result3).hasSize(0);
    }

    private List<GreetingEvent> callQueryGreetingFeedEndpoint(String name) throws Exception {
        String jsonResponse = mockMvc.perform(get("/api/redis/queryGreetingFeed?name=" + name))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return MAPPER.readValue(jsonResponse, new TypeReference<List<GreetingEvent>>() {});
    }
}
