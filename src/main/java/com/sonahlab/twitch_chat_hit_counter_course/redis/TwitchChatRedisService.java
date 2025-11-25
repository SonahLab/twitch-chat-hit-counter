package com.sonahlab.twitch_chat_hit_counter_course.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Service for storing TwitchChatEvents using a Redis DB named 'twitch-chat-hit-counter-database' (that we will create).
 * */
@Service
public class TwitchChatRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatRedisService.class);

    // Constructor
    public TwitchChatRedisService() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public Long incrementMinuteHitCounter(String channelName, long eventTimestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    public Map<String, Long> getHitCounts(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
