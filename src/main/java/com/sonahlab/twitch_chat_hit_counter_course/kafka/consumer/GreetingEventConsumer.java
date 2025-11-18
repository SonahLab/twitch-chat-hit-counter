package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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
public class GreetingEventConsumer extends AbstractEventConsumer<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventConsumer.class);

    // Constructor
    public GreetingEventConsumer() {
        /**
         * TODO: Implement as part of Module 3+
         * */
    }

    @Override
    protected EventType eventType() {
        /**
         * TODO: Implement as part of Module 2
         * */
        return EventType.GREETING_EVENT;
    }

    @Override
    protected Class eventClass() {
        /**
         * TODO: Implement as part of Module 2
         * */
        return GreetingEvent.class;
    }

    @Override
    protected void coreLogic() {
        /**
         * TODO: Implement as part of Module 3+
         * */
    }

    @Override
    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.greeting-topic}"
    )
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2
         * */
        super.processMessage(record, ack);
    }
}
