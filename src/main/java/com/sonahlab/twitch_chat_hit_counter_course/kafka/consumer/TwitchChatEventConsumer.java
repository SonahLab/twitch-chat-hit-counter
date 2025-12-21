package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;


import com.sonahlab.twitch_chat_hit_counter_course.metrics.MetricsCollector;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChatRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.sql.TwitchChatSqlService;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
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

    private TwitchChatSqlService twitchChatSqlService;
    private TwitchChatRedisService twitchChatRedisService;
    private MetricsCollector metricsCollector;

    // Constructor
    public TwitchChatEventConsumer(TwitchChatSqlService twitchChatSqlService,
                                   EventDeduperRedisService eventDeduperRedisService,
                                   TwitchChatRedisService twitchChatRedisService,
                                   MetricsCollector metricsCollector) {
        /**
         * TODO: Implement as part of Module 5
         * */
        super(eventDeduperRedisService, metricsCollector);
        this.twitchChatSqlService = twitchChatSqlService;
        this.twitchChatRedisService = twitchChatRedisService;
    }

    @Override
    protected EventType eventType() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return EventType.TWITCH_CHAT_EVENT;
    }

    @Override
    protected Class<TwitchChatEvent> eventClass() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return TwitchChatEvent.class;
    }

    @Override
    protected String eventKey(TwitchChatEvent event) {
        return event.eventId();
    }

    @Override
    protected void coreLogic(List<TwitchChatEvent> events) {
        /**
         * TODO: Implement as part of Module 5
         * */
        LOGGER.debug("Received Twitch Chat Events: {}", events);

        int success = twitchChatSqlService.insert(events);
        for (TwitchChatEvent event : events) {
            twitchChatRedisService.incrementMinuteHitCounter(event.channelName(), event.eventTs());
        }
        LOGGER.info("Successfully wrote to SQL {} inserted {} out of {} event(s).",
                twitchChatSqlService.sqlTableName(),
                success,
                events.size());
    }

    @Override
    @KafkaListener(
            topics = "${twitch-chat-hit-counter.kafka.twitch-chat-topic}"
    )
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 5
         * */
        super.processMessage(record, ack);
    }
}
