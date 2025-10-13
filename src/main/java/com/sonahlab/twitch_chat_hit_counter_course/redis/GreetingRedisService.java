package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.springframework.stereotype.Service;

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
    /**
     * TODO: Implement as part of Module 4
     * */
    public GreetingRedisService(RedisDao redisDao, ObjectMapper objectMapper) {}

    public void addGreetingToFeed(GreetingEvent event) {}

    public List<GreetingEvent> getGreetingFeed(String name) {
        return null;
    }
}
