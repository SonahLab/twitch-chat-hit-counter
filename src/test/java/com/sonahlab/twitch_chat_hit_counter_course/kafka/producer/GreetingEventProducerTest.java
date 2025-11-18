package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * https://www.baeldung.com/spring-boot-kafka-testing
 * */
@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "test_producer_topic" }, brokerProperties = {
        "listeners=PLAINTEXT://localhost:0", "port=0"
})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "twitch-chat-hit-counter.kafka.greeting-topic=test_producer_topic"
})
@DirtiesContext
@Tag("Module2")
public class GreetingEventProducerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GreetingEventProducer greetingEventProducer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private KafkaConsumer<String, byte[]> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker));
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList("test_producer_topic"));
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void publishTest() throws Exception {
        List<GreetingEvent> events = List.of(
                new GreetingEvent("id1", "Alice", "Bob", "Hi hi hi"),
                new GreetingEvent("id2", "Charlie", "David", "Yo.")
        );

        for (int index = 0; index < events.size(); index++) {
            String messageId = "test-id-" + index;
            boolean success = greetingEventProducer.publish(messageId, events.get(index));
            assertTrue(success);
        }
        ConsumerRecords<String, byte[]> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10));

        List<GreetingEvent> consumedEvents = new ArrayList<>();
        for (ConsumerRecord<String, byte[]> record : records) {
            GreetingEvent event = objectMapper.readValue(record.value(), GreetingEvent.class);
            consumedEvents.add(event);
        }

        assertEquals(events.size(), consumedEvents.size(), "Number of consumed events does not match");

        // Assert each sent event is contained in consumed events
        for (GreetingEvent expected : events) {
            assertTrue(consumedEvents.stream().anyMatch(consumed ->
                    consumed.sender().equals(expected.sender()) &&
                            consumed.receiver().equals(expected.receiver()) &&
                            consumed.message().equals(expected.message())
            ), "Sent event not found in consumed events: " + expected);
        }
    }
}
