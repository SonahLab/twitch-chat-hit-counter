package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Kafka consumer to read TwitchChatEvents from 'twitch_chat_events' topic in Module 5.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class TwitchChatEventConsumer extends AbstractEventConsumer<TwitchChatEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatEventConsumer.class);

    // Constructor
    public TwitchChatEventConsumer() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    @Override
    protected EventType eventType() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @Override
    protected Class<TwitchChatEvent> eventClass() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @Override
    protected void coreLogic(List<TwitchChatEvent> events) {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    @Override
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 5
         * */
    }
}
