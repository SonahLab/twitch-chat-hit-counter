package com.sonahlab.twitch_chat_hit_counter_course.flink.transform;

import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountAggregateFunction implements AggregateFunction<TwitchChatEvent, Long, Long> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountAggregateFunction.class);

    @Override
    public Long createAccumulator() {
        return 0L;
    }

    @Override
    public Long add(TwitchChatEvent value, Long accumulator) {
        return accumulator + 1L;
    }

    @Override
    public Long getResult(Long accumulator) {
        return accumulator;
    }

    @Override
    public Long merge(Long a, Long b) {
        return a + b;
    }
}
