package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Kafka BATCH consumer to read GreetingEvents from 'greeting_events' topic in Module 2.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventBatchConsumer extends AbstractEventConsumer<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventBatchConsumer.class);

    // Constructor
    public GreetingEventBatchConsumer() {
        /**
         * TODO: Implement as part of Module 3+
         * */
    }

    @Override
    protected EventType eventType() {
        return EventType.GREETING_EVENT;
    }

    @Override
    protected Class<GreetingEvent> eventClass() {
        return GreetingEvent.class;
    }

    @Override
    protected void coreLogic() {
        /**
         * TODO: Implement as part of Module 3+
         * */
    }

    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.consumer.greeting-topic}",
            containerFactory = "batchkafkaListenerContainerFactory"
    )
    public void processMessage(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        LOGGER.info("Received {} events in batch", records.size());

        List<GreetingEvent> events = new ArrayList<>();

        for (ConsumerRecord<String, byte[]> record : records) {
            String key = record.key();
            byte[] value = record.value();

            try {
                GreetingEvent event = new ObjectMapper().readValue(value, eventClass());
                events.add(event);
            } catch (Exception ex) {
                LOGGER.error("Failed to process GreetingEvent message: {}", ex);
            }
        }

        LOGGER.info("Received batch messages events: {}", events);

        ack.acknowledge();
    }
}
