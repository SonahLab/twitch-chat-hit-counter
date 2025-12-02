package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.sonahlab.twitch_chat_hit_counter_course.kafka.AbstractKafkaIntegrationTest;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
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
@TestPropertySource(properties = {
        "twitch-chat-hit-counter.kafka.greeting-topic=test-topic"
})
@Tag("Module2")
@Execution(ExecutionMode.SAME_THREAD)
public class GreetingEventProducerTest extends AbstractKafkaIntegrationTest {

    @Autowired
    private GreetingEventProducer greetingEventProducer;

    private KafkaConsumer<String, byte[]> consumer;

    @BeforeEach
    void setUp() {
        super.setup();

        Map<String, Object> consumerProps = new HashMap<>();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "test-producer-consumer-group");
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        consumer = new KafkaConsumer<>(consumerProps);
        consumer.subscribe(Collections.singletonList(TEST_TOPIC));
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
        // Poll for records from real Kafka
        ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofSeconds(10));

        List<GreetingEvent> consumedEvents = new ArrayList<>();
        for (ConsumerRecord<String, byte[]> record : records) {
            GreetingEvent event = objectMapper.readValue(record.value(), GreetingEvent.class);
            consumedEvents.add(event);
        }

        System.out.println(consumedEvents);
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
