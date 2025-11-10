package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEventProducer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventProducer.class);

    // Constructor
    public AbstractEventProducer() {
        /**
         * TODO: Implement as part of Module 2
         * */
    }

    public boolean publish(String key, T event) {
        /**
         * TODO: Implement as part of Module 2
         * */
        return false;
    }

    protected abstract String topicName();
}
