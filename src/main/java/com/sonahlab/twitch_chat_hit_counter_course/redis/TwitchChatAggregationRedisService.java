package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.model.Granularity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for storing TwitchChatEvents using a Redis DB named 'twitch-chat-hit-counter-database' (that we will create).
 * */
@Service
public class TwitchChatAggregationRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatAggregationRedisService.class);

    // Constructor
    public TwitchChatAggregationRedisService() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public boolean incrementHitCounter(String channelName, long eventTimestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return false;
    }

    /**
     * MINUTE#channelName#minBoundaryUTC
     * HOUR#channelName#hourBoundaryUTC
     * DAY#channelName#dayBoundaryUTC
     *
     * datestart/end loop through and get value
     * */
    public Map<String, Long> getHitCounts(
            Granularity granularity,
            String channelName,
            long startTimestampMillis,
            long endTimestampMillis) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
