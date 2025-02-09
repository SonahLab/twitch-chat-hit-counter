package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.GreetingRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private EventDeduperRedisService eventDeduperRedisService;
    private GreetingRedisService greetingRedisService;

    public GreetingEventConsumer(
            ObjectMapper objectMapper,
            GreetingSqlService greetingSqlService,
            EventDeduperRedisService eventDeduperRedisService,
            GreetingRedisService greetingRedisService) {
        this.objectMapper = objectMapper;
        this.greetingSqlService = greetingSqlService;
        this.eventDeduperRedisService = eventDeduperRedisService;
        this.greetingRedisService = greetingRedisService;
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

            if (eventDeduperRedisService.isDupeEvent(event)) {
                LOGGER.warn("Received DUPE message with key: {}, value: {}", key, event);
                ack.acknowledge();
            }
            LOGGER.info("Received message with key: {}, value: {}", key, event);

            // Insert event into SQL DB
            greetingSqlService.insert(event);
            // Add this greeting event into the receiver's Redis Greeting Feed
            greetingRedisService.addGreetingToFeed(event);
            // Add this eventId to Redis, so we can skip de-dupe if we ever re-process the same duplicate event
            eventDeduperRedisService.processEvent(event);

            ack.acknowledge();
        } catch (Exception ex) {
            LOGGER.error("Failed to process GreetingEvent message: {}", ex);
        }
    }
}
