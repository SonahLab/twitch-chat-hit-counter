package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private Map<Integer, RedisTemplate<String, String>> redisTemplateFactory;

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void eventDedupeRedisDaoTest() {
        assertTrue(context.containsBean("redisTemplateFactory"));

        int databaseIndex = 0;
        RedisTemplate<String, String> redisTemplate = redisTemplateFactory.get(databaseIndex);

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory).isInstanceOf(LettuceConnectionFactory.class);

        LettuceConnectionFactory lettuceFactory = (LettuceConnectionFactory) connectionFactory;
        assertThat(lettuceFactory.getHostName()).isEqualTo("localhost");
        assertThat(lettuceFactory.getPort()).isEqualTo(6379);
        assertThat(lettuceFactory.getDatabase()).isEqualTo(databaseIndex);
    }

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void greetingFeedRedisDaoTest() {
        int databaseIndex = 1;
        RedisTemplate<String, String> redisTemplate = redisTemplateFactory.get(databaseIndex);

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory).isInstanceOf(LettuceConnectionFactory.class);

        LettuceConnectionFactory lettuceFactory = (LettuceConnectionFactory) connectionFactory;
        assertThat(lettuceFactory.getHostName()).isEqualTo("localhost");
        assertThat(lettuceFactory.getPort()).isEqualTo(6379);
        assertThat(lettuceFactory.getDatabase()).isEqualTo(databaseIndex);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void oauthTokenRedisDaoTest() {
        int databaseIndex = 2;
        RedisTemplate<String, String> redisTemplate = redisTemplateFactory.get(databaseIndex);

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory).isInstanceOf(LettuceConnectionFactory.class);

        LettuceConnectionFactory lettuceFactory = (LettuceConnectionFactory) connectionFactory;
        assertThat(lettuceFactory.getHostName()).isEqualTo("localhost");
        assertThat(lettuceFactory.getPort()).isEqualTo(6379);
        assertThat(lettuceFactory.getDatabase()).isEqualTo(databaseIndex);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void twitchChatHitCounterRedisDaoTest() {
        int databaseIndex = 3;
        RedisTemplate<String, String> redisTemplate = redisTemplateFactory.get(databaseIndex);

        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        assertThat(connectionFactory).isNotNull();
        assertThat(connectionFactory).isInstanceOf(LettuceConnectionFactory.class);

        LettuceConnectionFactory lettuceFactory = (LettuceConnectionFactory) connectionFactory;
        assertThat(lettuceFactory.getHostName()).isEqualTo("localhost");
        assertThat(lettuceFactory.getPort()).isEqualTo(6379);
        assertThat(lettuceFactory.getDatabase()).isEqualTo(databaseIndex);
    }
}
