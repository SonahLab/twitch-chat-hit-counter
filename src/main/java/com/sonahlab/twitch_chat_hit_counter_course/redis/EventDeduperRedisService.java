package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for deduplicating events using a Redis DB named 'event-dedupe-database' db (that we will create).
 * Ensures that the same event is not processed multiple times by checking for the presence
 * of an eventID in Redis. If the event has already been processed, it is considered a duplicate.
 */
@Service
public class EventDeduperRedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDeduperRedisService.class);

    /**
     * TODO: Implement as part of Module 4
     * */
    public EventDeduperRedisService(RedisDao redisDao, ObjectMapper objectMapper) {}

    public void processEvent(GreetingEvent event) {}

    public boolean isDupeEvent(GreetingEvent event) {
        return true;
    }
}
