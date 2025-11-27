package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Set;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
@Tag("Module6")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
@Disabled
public class TwitchChannelRedisServiceTest {

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
    private TwitchChannelRedisService twitchChannelRedisService;

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
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    void getJoinedChannelsTest() {
        redisTemplate.opsForSet().add(String.format("user#%s#channels", USERNAME), "s0mcs");
        redisTemplate.opsForSet().add(String.format("user#%s#channels", USERNAME), "shroud");

        Set<String> output1 = twitchChannelRedisService.getJoinedChannels(USERNAME);
        Set<String> output2 = twitchChannelRedisService.getJoinedChannels("nonexistentUser");

        assertThat(output1).hasSize(2).contains("s0mcs", "shroud");
        assertThat(output2).hasSize(0);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    void addChannelsTest() {
        Long output1 = twitchChannelRedisService.addChannels(USERNAME, "s0mcs", "shroud");
        Long output2 = twitchChannelRedisService.addChannels(USERNAME, "s0mcs");

        assertEquals(2L, output1.longValue());
        assertEquals(0L, output2.longValue());
        assertThat(twitchChannelRedisService.getJoinedChannels(USERNAME)).hasSize(2).contains("s0mcs", "shroud");
        assertThat(twitchChannelRedisService.getJoinedChannels("nonexistentUser")).hasSize(0);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    void removeChannelsTest() {
        redisTemplate.opsForSet().add(String.format("user#%s#channels", USERNAME), "s0mcs");
        redisTemplate.opsForSet().add(String.format("user#%s#channels", USERNAME), "shroud");

        Long output1 = twitchChannelRedisService.removeChannels(USERNAME, "shroud", "nonexistentChannelName");
        Long output2 = twitchChannelRedisService.removeChannels("nonexistentUser", "shroud");

        assertEquals(1L, output1.longValue());
        assertEquals(0L, output2.longValue());
        assertThat(twitchChannelRedisService.getJoinedChannels(USERNAME)).hasSize(1).contains("s0mcs");
        assertThat(twitchChannelRedisService.getJoinedChannels("nonexistentUser")).hasSize(0);
    }
}
