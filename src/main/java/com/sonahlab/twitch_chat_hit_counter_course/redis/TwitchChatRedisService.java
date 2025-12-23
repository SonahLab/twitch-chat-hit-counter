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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public boolean incrementMinuteHitCounter(String channelName, long eventTimestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        ZonedDateTime dayBoundaryDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(eventTimestampMs), ZoneId.of("UTC"));

        long minuteBucketTs = eventTimestampMs - (eventTimestampMs % 60_000L);
        redisDao.increment(Granularity.MINUTE + "#" + channelName + "#" + minuteBucketTs);

        long hourBucketTs = eventTimestampMs - (eventTimestampMs % 3_600_000L);
        redisDao.increment(Granularity.HOUR + "#" + channelName + "#" + hourBucketTs);

        long dayBucketTs = eventTimestampMs - (eventTimestampMs % 86_400_000L);
        redisDao.increment(Granularity.DAY + "#" + channelName + "#" + dayBucketTs);

        return true;
    }

    public Map<String, Long> getHitCounts(
            Granularity granularity,
            String channelName,
            long startTimeMillis,
            long endTimeMillis) {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, Long> hitCounts = new HashMap<>();

        long millisBoundary = 0;
        if (granularity == Granularity.MINUTE) {
            millisBoundary = 60_000L;
        } else if (granularity == Granularity.HOUR) {
            millisBoundary = 3_600_000L;
        } else if (granularity == Granularity.DAY) {
            millisBoundary = 86_400_000L;
        }

        long startBoundaryTimeMillis = startTimeMillis - (startTimeMillis % millisBoundary);
        long endBoundaryTimeMillis = endTimeMillis - (endTimeMillis % millisBoundary);
        for (long currentMillis = startBoundaryTimeMillis; currentMillis <= endBoundaryTimeMillis; currentMillis += millisBoundary) {
            String key = granularity + "#" + channelName.toLowerCase() + "#" + currentMillis;
            try {
                Long count = Long.valueOf(redisDao.get(key));
                hitCounts.put(key, count);
            } catch (Exception ex) {}

        }

        LOGGER.info("Found {} hits for channel: {}#{} between {} and {}", hitCounts.size(), granularity, channelName, startTimeMillis, endTimeMillis);

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
