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

/**
 * Kafka consumer to read GreetingEvents from Module 2 topic.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventConsumer.class);

    private ObjectMapper objectMapper;
    private GreetingSqlService greetingSqlService;

    public GreetingEventConsumer(
            ObjectMapper objectMapper,
            GreetingSqlService greetingSqlService) {
        this.objectMapper = objectMapper;
        this.greetingSqlService = greetingSqlService;
    }

    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.consumer.greeting-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        String key = record.key();
        byte[] value = record.value();

        try {
            GreetingEvent event = objectMapper.readValue(value, GreetingEvent.class);
            LOGGER.info("Received message with key: {}, value: {}", key, event);

            greetingSqlService.insert(event);

            ack.acknowledge();
        } catch (Exception ex) {
            LOGGER.error("Failed to process GreetingEvent message: {}", ex);
        }
    }
}
