package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for storing GreetingEvents using a Redis DB named 'greeting-feed-database' (that we will create).
 *
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
@Service
public class GreetingRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingRedisService.class);
    // Key template: receiver#{receiver}
    private static final String KEY_TEMPLATE = "receiver#%s";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private RedisDao redisDao;

    // Constructor
    public GreetingRedisService(@Qualifier("greetingFeedRedisDao") RedisDao redisDao) {
        /**
         * TODO: Implement as part of Module 4
         * */
        this.redisDao = redisDao;
    }

    public Long addGreetingToFeed(GreetingEvent event) throws JsonProcessingException {
        String key = getKey(event.receiver());
        Long feedCount = redisDao.listAdd(key, OBJECT_MAPPER.writeValueAsString(event));
        LOGGER.info("Added new event to {}'s greeting feed, event={}", key, event);
        return feedCount;
    }

    public List<GreetingEvent> getGreetingFeed(String name) throws JsonProcessingException {
        String key = getKey(name);
        List<String> rawEventsFeed = redisDao.listGet(key);
        List<GreetingEvent> greetingEventsFeed = new ArrayList<>();

        for (String rawEvent : rawEventsFeed) {
            greetingEventsFeed.add(OBJECT_MAPPER.readValue(rawEvent, GreetingEvent.class));
        }

        LOGGER.info("Retrieved Greeting Feed for {}, feed={}", name, greetingEventsFeed);
        return greetingEventsFeed;
    }

    private String getKey(String name) {
        return String.format(KEY_TEMPLATE, name);
    }
}
