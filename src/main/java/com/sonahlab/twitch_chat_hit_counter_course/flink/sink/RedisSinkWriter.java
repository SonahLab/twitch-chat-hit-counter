package com.sonahlab.twitch_chat_hit_counter_course.flink.sink;

import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.api.java.tuple.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.IOException;

public class RedisSinkWriter implements SinkWriter<Tuple3<String, Long, Long>> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisSinkWriter.class);
    private final RedisTemplate<String, String> redisTemplate;

    public RedisSinkWriter() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.setHostName("localhost");
        connectionFactory.setPort(6379);
        connectionFactory.setDatabase(3);
        connectionFactory.start();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        try {
            redisTemplate.afterPropertiesSet();
        } catch (Exception e) {
            throw new RuntimeException("Error initializing RedisTemplate", e);
        }
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void write(Tuple3<String, Long, Long> element, Context context) throws IOException, InterruptedException {
        LOGGER.info("RedisSink: Key={}, set={}", element.f0 + ":" + element.f1, element.f2);
        redisTemplate.opsForValue().set(element.f0 + ":" + element.f1, String.valueOf(element.f2));
    }

    @Override
    public void flush(boolean endOfInput) throws IOException, InterruptedException {

    }

    @Override
    public void close() throws Exception {

    }
}
