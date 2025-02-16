package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.GreetingRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChatRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import com.sonahlab.twitch_chat_hit_counter_course.sql.TwitchChatSqlService;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Kafka consumer to read TwitchChatEvents from Module 5 topic.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class TwitchChatEventConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatEventConsumer.class);

    private ObjectMapper objectMapper;
    private TwitchChatSqlService twitchChatSqlService;
    private EventDeduperRedisService eventDeduperRedisService;
    private TwitchChatRedisService twitchChatRedisService;

    public TwitchChatEventConsumer(
            ObjectMapper objectMapper,
            TwitchChatSqlService twitchChatSqlService,
            EventDeduperRedisService eventDeduperRedisService,
            TwitchChatRedisService twitchChatRedisService) {
        this.objectMapper = objectMapper;
        this.twitchChatSqlService = twitchChatSqlService;
        this.eventDeduperRedisService = eventDeduperRedisService;
        this.twitchChatRedisService = twitchChatRedisService;
    }

    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.consumer.twitch-chat-topic}",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        String key = record.key();
        byte[] value = record.value();
        EventType eventType = EventType.TWITCH_CHAT_EVENT;

        try {
            TwitchChatEvent event = objectMapper.readValue(value, TwitchChatEvent.class);

            if (eventDeduperRedisService.isDupeEvent(eventType, event.eventId())) {
                LOGGER.warn("Received DUPE message with key: {}, value: {}", key, event);
                ack.acknowledge();
            }
            LOGGER.info("Received {} message with key: {}, value: {}", eventType, key, event);

            int successfulWrites = twitchChatSqlService.writeChatEvent(event);
            if (successfulWrites == 0) {
                LOGGER.error("Failed to write/save ChatEvent to sql db");
                throw new RuntimeException("Failed to write/save ChatEvent to sql db");
            }

            String minuteKey = event.messageContext().channelName() + "#" + roundToNearestMinute(event.messageContext().eventTs());
            twitchChatRedisService.incrHitCounter(minuteKey);
            eventDeduperRedisService.processEvent(eventType, event.eventId());

            ack.acknowledge();
        } catch (Exception ex) {
            LOGGER.error("Failed to process GreetingEvent message: {}", ex);
        }
    }

    public static long roundToNearestMinute(long timestampMillis) {
        long minuteInMillis = 60 * 1000;
        return (timestampMillis / minuteInMillis) * minuteInMillis;
    }
}
