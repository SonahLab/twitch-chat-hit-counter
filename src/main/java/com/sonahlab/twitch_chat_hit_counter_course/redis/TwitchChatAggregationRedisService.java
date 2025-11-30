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

    public Long incrementMinuteHitCounter(Granularity granularity, String channelName, long eventTimestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    public Map<String, Long> getHitCounts(
            Granularity granularity,
            String channelName,
            int dateInt) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
