package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEventConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventConsumer.class);

    protected ObjectMapper objectMapper;
    protected EventDeduperRedisService eventDeduperRedisService;

    public AbstractEventConsumer(EventDeduperRedisService eventDeduperRedisService) {
        /**
         * TODO: Implement as part of Module 3+
         * */
        this.eventDeduperRedisService  = eventDeduperRedisService;
        this.objectMapper = new ObjectMapper();
    }

    protected abstract EventType eventType();

    protected abstract Class<T> eventClass();

    protected abstract String eventKey(T event);

    protected abstract void coreLogic(List<T> events);

    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         * */
        String key = record.key();
        byte[] value = record.value();

        T event = convertRecordToEvent(value);

        if (eventDeduperRedisService.isDupeEvent(eventType(), eventKey(event))) {
            LOGGER.warn("Received DUPE message with key: {}, value: {}", key, key);
            ack.acknowledge();
            return;
        }

        LOGGER.info("Received message with key: {}, value: {}", key, event);

        coreLogic(List.of(event));
        eventDeduperRedisService.processEvent(eventType(), eventKey(event));
        ack.acknowledge();
    }

    public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 4
         * */
        List<T> events = new ArrayList<>();

        for (ConsumerRecord<String, byte[]> record : records) {
            String key = record.key();
            byte[] value = record.value();

            T event = convertRecordToEvent(value);

            if (eventDeduperRedisService.isDupeEvent(eventType(), eventKey(event))) {
                LOGGER.warn("[BATCH] Received DUPE message with key: {}, value: {}", key, key);
                continue;
            }

            LOGGER.info("[BATCH] Received message with key: {}, value: {}", key, event);
            events.add(event);
        }

        coreLogic(events);

        for (T event : events) {
            eventDeduperRedisService.processEvent(eventType(), eventKey(event));
        }
        ack.acknowledge();
    }

    protected T convertRecordToEvent(byte[] value) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         */
        try {
            return objectMapper.readValue(value, eventClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
