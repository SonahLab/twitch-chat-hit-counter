package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RedisConfigTest {

    @Autowired
    private Map<Integer, RedisTemplate<String, String>> redisTemplateFactory;

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void eventDedupeRedisDaoTest() {
        validateRedisDB(0);
    }

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void greetingFeedRedisDaoTest() {
        validateRedisDB(1);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void oauthTokenRedisDaoTest() {
        validateRedisDB(2);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void twitchChatHitCounterRedisDaoTest() {
        validateRedisDB(3);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    public void chatBotChannelsRedisDaoTest() {
        validateRedisDB(4);
    }

    private void validateRedisDB(int databaseIndex) {
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
