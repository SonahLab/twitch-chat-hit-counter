package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Kafka producer to write TwitchChatEvents to 'twitch_chat_events' topic in Module 5.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class TwitchChatEventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatEventProducer.class);

    public TwitchChatEventProducer() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public boolean publish(String messageId, TwitchChatEvent event) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return false;
    }
}
