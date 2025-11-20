package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;

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
    /**
     * IMPORTANT: This bean gets Autoconfigured for us by Spring Kafka
     * {@link KafkaAutoConfiguration}
     *
     * At runtime Spring checks that we never created a {@link KafkaTemplate} Bean and will autoconfigure this bean for us.
     * @Bean
     * @ConditionalOnMissingBean(KafkaTemplate.class)
     * public KafkaTemplate<?, ?> kafkaTemplate(...) {}
     * */
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
}
