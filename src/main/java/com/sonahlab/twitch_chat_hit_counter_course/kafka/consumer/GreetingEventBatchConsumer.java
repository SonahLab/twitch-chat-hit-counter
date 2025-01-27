package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Kafka consumer to read GreetingEvents from Module 2 topic.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventBatchConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventBatchConsumer.class);

    private ObjectMapper objectMapper;
    private GreetingSqlService greetingSqlService;

    public GreetingEventBatchConsumer(
            ObjectMapper objectMapper,
            GreetingSqlService greetingSqlService) {
        this.objectMapper = objectMapper;
        this.greetingSqlService = greetingSqlService;
    }

    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.consumer.greeting-topic}",
            groupId = "${spring.kafka.consumer.group-id-batch}",
            containerFactory = "batchKafkaListenerContainerFactory",
            batch = "true"
    )
    public void processMessage(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        LOGGER.info("Received {} events in batch", records.size());

        List<GreetingEvent> events = new ArrayList<>();

        for (ConsumerRecord<String, byte[]> record : records) {
            String key = record.key();
            byte[] value = record.value();

            try {
                GreetingEvent event = objectMapper.readValue(value, GreetingEvent.class);
                LOGGER.info("Received message with key: {}, value: {}", key, event);
                events.add(event);
            } catch (Exception ex) {
                LOGGER.error("Failed to process GreetingEvent message: {}", ex);
            }
        }

        if (!events.isEmpty()) {
            greetingSqlService.insertBatch(events);
        }

        ack.acknowledge();
    }
}
