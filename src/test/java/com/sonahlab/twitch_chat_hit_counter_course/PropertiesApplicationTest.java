package com.sonahlab.twitch_chat_hit_counter_course;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MainApplicationTest.class)
@ActiveProfiles(value = "default")
public class PropertiesApplicationTest {

    @Autowired
    private Environment env;

    // =================================================================================================================
    // MODULE 2
    // =================================================================================================================
    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    @Tag("Module2")
    public void springKafkaConfigsTest() {
        assertEquals("localhost:9092", env.getProperty("spring.kafka.bootstrap-servers"));
        assertTrue(env.getProperty("spring.kafka.consumer.group-id").toLowerCase().contains("twitch-chat-hit-counter-group-id-"));
        assertEquals("earliest", env.getProperty("spring.kafka.consumer.auto-offset-reset"));
        assertEquals(false, Boolean.valueOf(env.getProperty("spring.kafka.consumer.enable-auto-commit")));
        assertEquals("org.apache.kafka.common.serialization.StringDeserializer", env.getProperty("spring.kafka.consumer.key-deserializer"));
        assertEquals("org.apache.kafka.common.serialization.ByteArrayDeserializer", env.getProperty("spring.kafka.consumer.value-deserializer"));
        assertEquals("org.apache.kafka.common.serialization.StringSerializer", env.getProperty("spring.kafka.producer.key-serializer"));
        assertEquals("org.apache.kafka.common.serialization.ByteArraySerializer", env.getProperty("spring.kafka.producer.value-serializer"));
        assertEquals(ContainerProperties.AckMode.MANUAL.name(), env.getProperty("spring.kafka.listener.ack-mode"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    @Tag("Module2")
    public void kafkaGreetingTopicNameTest() {
        assertEquals("greeting-events", env.getProperty("twitch-chat-hit-counter.kafka.greeting-topic"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    @Tag("Module2")
    public void kafkaBatchConfigsTest() {
        assertTrue(env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.group-id").toLowerCase().contains("twitch-chat-hit-counter-group-id-batch-"));
        assertNotEquals(env.getProperty("spring.kafka.consumer.group-id"), env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.group-id"));
        assertEquals("BATCH", env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.listener.type"));
    }

    // =================================================================================================================
    // MODULE 3
    // =================================================================================================================
    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    @Tag("Module3")
    public void sqlGreetingTableNameTest() {
        assertEquals("greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    @Tag("Module3")
    public void sqlBatchGreetingTableNameTest() {
        assertEquals("batch_greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table-batch"));
    }

    // =================================================================================================================
    // MODULE 4
    // =================================================================================================================
    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Module4")
    public void springRedisConfigsTest() {
        assertEquals("localhost", env.getProperty("spring.redis.host"));
        assertEquals("6379", env.getProperty("spring.redis.port"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Module4")
    public void redisDeduperDatabaseTest() {
        assertEquals(0, env.getProperty("twitch-chat-hit-counter.redis.event-dedupe-database"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Module4")
    public void redisGreetingFeedDatabaseTest() {
        assertEquals(1, env.getProperty("twitch-chat-hit-counter.redis.greeting-feed-database"));
    }

    // =================================================================================================================
    // MODULE 5
    // =================================================================================================================
    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void kafkaTwitchChatTopicNameTest() {
        assertEquals("twitch-chat-events", env.getProperty("twitch-chat-hit-counter.kafka.twitch-chat-topic"));
    }

}
