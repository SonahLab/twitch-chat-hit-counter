package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.io.IOException;

public abstract class AbstractEventConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventConsumer.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public AbstractEventConsumer() {
        /**
         * TODO: Implement as part of Module 2
         * */
    }

    protected abstract EventType eventType();

    protected abstract Class<T> eventClass();

    protected abstract void coreLogic();

    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) throws IOException {
        /**
         * TODO: Implement as part of Module 2
         * */
        String key = record.key();
        byte[] value = record.value();

        T event = OBJECT_MAPPER.readValue(value, eventClass());
        LOGGER.info("Received message with key: {}, value: {}", key, event);

        ack.acknowledge();
    }

    protected void isDupeEvent() {
        /**
         * TODO: Implement as part of Module 2
         * */
    }

    protected T convertRecordToEvent(ConsumerRecord<String, byte[]> record) {
        /**
         * TODO: Implement as part of Module 2
         */
        return null;
    }
}
