package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for interacting with Redis data store, focused on higher-level business logic.
 *
 * In a real-world application, multiple service classes may be implemented to handle different
 * types of events EventA, EventB, etc., and each service would use the RedisDao.java to interact
 * with Redis.
 *
 * RedisDao (DAO layer) is more generic in terms of dealing with Redis databases and all Redis
 * operations. GreetingRedisService (Service layer) is more closely aligned to application/business
 * logic.
 *
 * Recommended Learning materials:
 * - DAO Layer Vs. Service Layer(https://softwareengineering.stackexchange.com/questions/220909/service-layer-vs-dao-why-both)
 */
@Component
public class EventDeduperRedisService {
    /**
     * TODO: Implement as part of Module 4
     * */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventDeduperRedisService.class);
    private static final String EVENT_KEY = "%s#%s";

    private RedisDao redisDao;
    private ObjectMapper objectMapper;

    public EventDeduperRedisService(@Qualifier("eventDedupeRedisDao") RedisDao redisDao,
                                    ObjectMapper objectMapper) {
        this.redisDao = redisDao;
        this.objectMapper = objectMapper;
    }

    public boolean isDupeEvent(GreetingEvent event) {
        String key = String.format(EVENT_KEY, EventType.GREETING_EVENT, event.eventId());
        Long result = redisDao.get(key);
        if (result != null && result == 1) {
            return true;
        }
        return false;
    }

    public void processEvent(GreetingEvent event) {
        String key = String.format(EVENT_KEY, EventType.GREETING_EVENT, event.eventId());
        redisDao.increment(key);
    }
}
