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
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 1, Task 1.
    @Disabled
    @Tag("Module2")
    public void testDefaultProfile_kafkaConfigs() {
        assertEquals(env.getProperty("spring.kafka.bootstrap-servers"), "localhost:9092");

        assertTrue(env.getProperty("spring.kafka.consumer.group-id").toLowerCase().contains( "twitch-chat-hit-counter-group-id-"));
        assertEquals(env.getProperty("spring.kafka.consumer.auto-offset-reset"), "earliest");
        assertEquals(env.getProperty("spring.kafka.consumer.enable-auto-commit"), "false");
        assertEquals(env.getProperty("spring.kafka.consumer.key-deserializer"), "org.apache.kafka.common.serialization.StringDeserializer");
        assertEquals(env.getProperty("spring.kafka.consumer.value-deserializer"), "org.apache.kafka.common.serialization.ByteArrayDeserializer");

        assertEquals(env.getProperty("spring.kafka.producer.key-serializer"), "org.apache.kafka.common.serialization.StringSerializer");
        assertEquals(env.getProperty("spring.kafka.producer.value-serializer"), "org.apache.kafka.common.serialization.ByteArraySerializer");
    }
}
