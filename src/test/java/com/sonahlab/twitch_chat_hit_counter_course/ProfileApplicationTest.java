package com.sonahlab.twitch_chat_hit_counter_course;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = MainApplicationTest.class)
@ActiveProfiles(value = "default")
public class ProfileApplicationTest {

    @Autowired
    private Environment env;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    @Tag("Module2")
    public void testDefaultProfile_kafkaConfigs() {
        assertEquals("localhost:9092", env.getProperty("spring.kafka.bootstrap-servers"));

        assertTrue(env.getProperty("spring.kafka.consumer.group-id").toLowerCase().contains("twitch-chat-hit-counter-group-id-"));
        assertEquals("earliest", env.getProperty("spring.kafka.consumer.auto-offset-reset"));
        assertEquals(false, env.getProperty("spring.kafka.consumer.enable-auto-commit"));
        assertEquals("org.apache.kafka.common.serialization.StringDeserializer", env.getProperty("spring.kafka.consumer.key-deserializer"));
        assertEquals("org.apache.kafka.common.serialization.ByteArrayDeserializer", env.getProperty("spring.kafka.consumer.value-deserializer"));

        assertEquals("org.apache.kafka.common.serialization.StringSerializer", env.getProperty("spring.kafka.producer.key-serializer"));
        assertEquals("org.apache.kafka.common.serialization.ByteArraySerializer", env.getProperty("spring.kafka.producer.value-serializer"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
    @Disabled
    @Tag("Module2")
    public void testDefaultProfile_kafka_greetingTopicName() {
        assertEquals("greeting-events", env.getProperty("twitch-chat-hit-counter.kafka.producer.greeting-topic"));
        assertEquals("greeting-events", env.getProperty("twitch-chat-hit-counter.kafka.consumer.greeting-topic"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    @Tag("Module3")
    public void testDefaultProfile_sql_greetingTableName() {
        assertEquals("greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
    @Disabled
    @Tag("Module3")
    public void testDefaultProfile_sql_batchGreetingTableName() {
        assertEquals("batch_greeting_events", env.getProperty("twitch-chat-hit-counter.sql.greeting-table-batch"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Modul4")
    public void testDefaultProfile_redisConfigs() {
        assertEquals("localhost", env.getProperty("spring.redis.host"));
        assertEquals("6379", env.getProperty("spring.redis.port"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Module4")
    public void testDefaultProfile_redis_deduperDB() {
        assertEquals(0, env.getProperty("twitch-chat-hit-counter.redis.event-dedupe-database"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    @Tag("Module4")
    public void testDefaultProfile_redis_greetingFeedDB() {
        assertEquals(1, env.getProperty("twitch-chat-hit-counter.redis.greeting-feed-database"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void testDefaultProfile_kafka_twitchChatTopicName() {
        assertEquals("twitch-chat-events", env.getProperty("twitch-chat-hit-counter.kafka.producer.twitch-chat-topic"));
        assertEquals("twitch-chat-events", env.getProperty("twitch-chat-hit-counter.kafka.consumer.twitch-chat-topic"));
    }

}
