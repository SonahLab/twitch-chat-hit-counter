package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Service for storing TwitchChatEvents using a Redis DB named 'twitch-chat-hit-counter-database' (that we will create).
 * */
@Service
public class TwitchChatRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatRedisService.class);
    private static final String KEY_TEMPLATE = "%s#%s";

    private RedisDao redisDao;

    // Constructor
    public TwitchChatRedisService(@Qualifier("twitchChatHitCounterRedisDao") RedisDao redisDao) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.redisDao = redisDao;
    }

    public Long incrementMinuteHitCounter(String channelName, long eventTimestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        String key = getKey(channelName, eventTimestampMs);
        Long hitCounts = redisDao.increment(key);
        return hitCounts;
    }

    public Map<String, Long> getHitCounts(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, Long> hitCounts = new HashMap<>();

        Map<String, String> entries = redisDao.scanKeys(channelName);
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            hitCounts.put(entry.getKey(), Long.parseLong(entry.getValue()));
        }
        LOGGER.info("Found {} hits for channel: {}", hitCounts.size(), channelName);
        return hitCounts;
    }

    private String getKey(String channelName, long eventTimestampMs) {
        // 1 second = 1000 milliseconds
        // 1 minute = 60 seconds = 60000 milliseconds
        // Example: eventTs = 1767254435123
        // 1. 1767254435123 - (1767254435123 mod 60000) = 1767254435123 - (35123) = 1767254400000
        long minuteBucketTs = eventTimestampMs - (eventTimestampMs % 60000L);
        String minuteBucketKey = String.format(KEY_TEMPLATE, channelName, minuteBucketTs);
        return minuteBucketKey;
    }
}
