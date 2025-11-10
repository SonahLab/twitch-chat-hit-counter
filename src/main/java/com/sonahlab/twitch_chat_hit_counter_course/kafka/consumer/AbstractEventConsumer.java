package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

public abstract class AbstractEventConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventConsumer.class);

    public AbstractEventConsumer() {
        /**
         * TODO: Implement as part of Module 2
         * */
    }

    protected abstract EventType eventType();

    protected abstract Class<T> eventClass();

    protected abstract void coreLogic();

    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2
         * */
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
