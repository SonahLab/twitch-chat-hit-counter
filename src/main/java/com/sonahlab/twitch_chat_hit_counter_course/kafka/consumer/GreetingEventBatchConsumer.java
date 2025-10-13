package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kafka BATCH consumer to read GreetingEvents from 'greeting_events' topic in Module 2.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventBatchConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventBatchConsumer.class);

    /**
     * TODO: Implement as part of Module 2
     * */
    public void processMessage(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {}
}
