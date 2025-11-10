package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Kafka producer to write GreetingEvents to 'greeting_events' topic in Module 2.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventProducer extends AbstractEventProducer<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventProducer.class);

    // Constructor
    public GreetingEventProducer() {
        /**
         * TODO: Implement as part of Module 2
         * */
        super();
    }

    @Override
    protected String topicName() {
        /**
         * TODO: Implement as part of Module 2
         * */
        return null;
    }
}
