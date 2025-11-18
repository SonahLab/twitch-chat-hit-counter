package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.io.IOException;
import java.util.List;

public abstract class AbstractEventConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventConsumer.class);
    private ObjectMapper objectMapper;

    public AbstractEventConsumer() {
        /**
         * TODO: Implement as part of Module 3+
         * */
        this.objectMapper = new ObjectMapper();
    }

    protected abstract EventType eventType();

    protected abstract Class<T> eventClass();

    protected abstract void coreLogic();

    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         * */
        String key = record.key();
        byte[] value = record.value();

        T event = convertRecordToEvent(record);
        LOGGER.info("Received message with key: {}, value: {}", key, event);

        ack.acknowledge();
    }

    public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 4
         * */
    }

    protected void isDupeEvent() {
        /**
         * TODO: Implement as part of Module 4
         * */
    }

    protected T convertRecordToEvent(ConsumerRecord<String, byte[]> record) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         */
        try {
            return objectMapper.readValue(record.value(), eventClass());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
