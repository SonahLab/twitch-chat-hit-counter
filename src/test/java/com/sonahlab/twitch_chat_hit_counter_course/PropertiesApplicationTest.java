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
    @Tag("Module2")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
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
    @Tag("Module2")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    public void kafkaGreetingTopicNameTest() {
        assertEquals("greeting-events", env.getProperty("twitch-chat-hit-counter.kafka.greeting-topic"));
    }

    @Test
    @Tag("Module2")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    public void kafkaBatchConfigsTest() {
        assertTrue(env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.group-id").toLowerCase().contains("twitch-chat-hit-counter-group-id-batch-"));
        assertNotEquals(env.getProperty("spring.kafka.consumer.group-id"), env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.group-id"));
        assertEquals("BATCH", env.getProperty("twitch-chat-hit-counter.kafka.batch-consumer.listener.type"));
    }

    // =================================================================================================================
    // MODULE 3
    // =================================================================================================================
    @Test
    @Tag("Module3")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    public void sqlGreetingTableNameTest() {
        assertEquals("greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table"));
    }

    @Test
    @Tag("Module3")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    public void sqlBatchGreetingTableNameTest() {
        assertEquals("batch_greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table-batch"));
    }

    // =================================================================================================================
    // MODULE 4
    // =================================================================================================================
    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void springRedisConfigsTest() {
        assertEquals("localhost", env.getProperty("spring.data.redis.host"));
        assertEquals("6379", env.getProperty("spring.data.redis.port"));
    }

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void redisDeduperDatabaseTest() {
        assertEquals("0", env.getProperty("twitch-chat-hit-counter.redis.event-dedupe-database"));
    }

    @Test
    @Tag("Module4")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    public void redisGreetingFeedDatabaseTest() {
        assertEquals("1", env.getProperty("twitch-chat-hit-counter.redis.greeting-feed-database"));
    }

    // =================================================================================================================
    // MODULE 5
    // =================================================================================================================
    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void kafkaTwitchChatTopicNameTest() {
        assertEquals("twitch-chat-events", env.getProperty("twitch-chat-hit-counter.kafka.twitch-chat-topic"));
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void redisOAuthTokenDatabaseTest() {
        assertEquals("2", env.getProperty("twitch-chat-hit-counter.redis.oauth-token-database"));
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void redisTwitchChatHitCounterDatabaseTest() {
        assertEquals("3", env.getProperty("twitch-chat-hit-counter.redis.twitch-chat-hit-counter-database"));
    }

    // =================================================================================================================
    // MODULE 6
    // =================================================================================================================
    @Test
    @Tag("Module6")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void redisTwitchChannelDatabaseTest() {
        assertEquals("4", env.getProperty("twitch-chat-hit-counter.redis.twitch-channel-database"));
    }
}
