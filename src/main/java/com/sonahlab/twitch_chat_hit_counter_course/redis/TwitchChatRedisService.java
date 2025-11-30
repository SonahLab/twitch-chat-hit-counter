package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Granularity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Service for storing TwitchChatEvents using a Redis DB named 'twitch-chat-hit-counter-database' (that we will create).
 * */
@Service
public class TwitchChatRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatRedisService.class);
    private static final String KEY_TEMPLATE = "%s#%s";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

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
        ZonedDateTime dayBoundaryDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(eventTimestampMs), ZoneId.of("UTC"));
        String key = Granularity.MINUTE + "#" + channelName + "#" + dayBoundaryDateTime.format(DATE_FORMATTER);
        long minuteBucketTs = eventTimestampMs - (eventTimestampMs % 60000L);

        Long hitCounts = redisDao.hashIncrBy(key, String.valueOf(minuteBucketTs), 1L);
        return hitCounts;
    }

    public Map<String, Long> getHitCounts(
            Granularity granularity,
            String channelName,
            int dateInt) {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, Long> hitCounts = new HashMap<>();

        // "{Granularity}#{channelName}#{YYYYMMDD}"
        String key = granularity.toString() + "#" + channelName + "#" + dateInt;
        Map<Object, Object> entries = redisDao.hashGetAll(key);

        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            hitCounts.put(entry.getKey().toString(), Long.valueOf(entry.getValue().toString()));
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
