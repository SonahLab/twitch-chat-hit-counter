package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service for deduplicating events using a Redis DB named 'event-dedupe-database' db (that we will create).
 * Ensures that the same event is not processed multiple times by checking for the presence
 * of an eventID in Redis. If the event has already been processed, it is considered a duplicate.
 */
@Service
public class EventDeduperRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventDeduperRedisService.class);
    // Should be EventType#eventId
    private static final String KEY_TEMPLATE = "%s#%s";

    private RedisDao redisDao;

    // Constructor
    public EventDeduperRedisService(@Qualifier("eventDedupeRedisDao") RedisDao redisDao) {
        /**
         * TODO: Implement as part of Module 4
         * */
        this.redisDao = redisDao;
    }

    public void processEvent(EventType eventType, String eventId) {
        /**
         * TODO: Implement as part of Module 4
         * */
        String key = getKey(eventType, eventId);
        Long update = redisDao.increment(key);
        LOGGER.info("Deduper incremented key: {}, new value: {}", key, update);
    }

    public boolean isDupeEvent(EventType eventType, String eventId) {
        /**
         * TODO: Implement as part of Module 4
         * */
        String key = getKey(eventType, eventId);
        String value = redisDao.get(key);
        if (value == null || Long.parseLong(value) < 1) {
            return false;
        }
        return true;
    }

    private String getKey(EventType eventType, String eventId) {
        return String.format(KEY_TEMPLATE, eventType, eventId);
    }
}
