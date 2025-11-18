package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private GreetingSqlService greetingSqlService;

    // Constructor
    public GreetingEventConsumer(@Qualifier("singleGreetingSqlService") GreetingSqlService greetingSqlService) {
        /**
         * TODO: Implement as part of Module 3+
         * */
        this.greetingSqlService = greetingSqlService;
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
    protected void coreLogic(List<GreetingEvent> events) {
        /**
         * TODO: Implement as part of Module 3+
         * */
        LOGGER.info("Inserting into table: {} {} events", greetingSqlService.sqlTableName(), events.size());
        int result = greetingSqlService.insert(events);
        LOGGER.info("Inserted into table: {} {} events", greetingSqlService.sqlTableName(), result);
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
