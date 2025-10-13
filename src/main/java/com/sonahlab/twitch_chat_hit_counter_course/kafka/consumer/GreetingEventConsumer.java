package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer to read GreetingEvents from 'greeting_events' topic in Module 2.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventConsumer.class);

    /**
     * TODO: Implement as part of Module 2
     * */
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {}
}
