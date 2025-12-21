package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.metrics.MetricsCollector;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
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
 * Kafka BATCH consumer to read GreetingEvents from 'greeting_events' topic in Module 2.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventBatchConsumer extends AbstractEventConsumer<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventBatchConsumer.class);

    private GreetingSqlService greetingSqlService;
    private MetricsCollector metricsCollector;

    // Constructor
    public GreetingEventBatchConsumer(
            @Qualifier("batchGreetingSqlService") GreetingSqlService greetingSqlService,
            EventDeduperRedisService eventDeduperRedisService,
            MetricsCollector metricsCollector) {
        /**
         * TODO: Implement as part of Module 3+
         * */
        super(eventDeduperRedisService, metricsCollector);
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
    protected Class<GreetingEvent> eventClass() {
        /**
         * TODO: Implement as part of Module 2
         * */
        return GreetingEvent.class;
    }

    @Override
    protected String eventKey(GreetingEvent event) {
        return event.eventId();
    }

    @Override
    protected void coreLogic(List<GreetingEvent> events) {
        /**
         * TODO: Implement as part of Module 3+
         * */
        greetingSqlService.insert(events);
    }

    @Override
    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.greeting-topic}",
            containerFactory = "batchKafkaListenerContainerFactory"
    )
    public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2
         * */
        super.processMessages(records, ack);
    }
}
