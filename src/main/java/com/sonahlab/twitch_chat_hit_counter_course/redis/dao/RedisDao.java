package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Data Access Object (DAO) layer for interacting with Redis data store via Redis API.
 *
 * This class provides low-level operations for interacting with Redis. It uses the RedisTemplate
 * to perform the Redis operations, abstracting the Redis API interactions away from the service
 * layer.
 *
 * Recommended Learning materials to learn about Spring Boot + Redis integration:
 * - https://redis.io/learn/develop/java/redis-and-spring-course
 * - https://www.baeldung.com/spring-data-redis-tutorial
 */
public class RedisDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    private RedisTemplate<String, String> redisTemplate;

    public RedisDao(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void increment(String key) {
        redisTemplate.opsForValue().increment(key);
    }

    public void incrementBy(String key, long delta) {
        redisTemplate.opsForValue().increment(key, delta);
    }

    public void set(String key, Long value) {
        redisTemplate.opsForValue().set(key, String.valueOf(value));
    }

    public Long get(String key) {
        String value = redisTemplate.opsForValue().get(key);
        if (value == null) {
            return null;
        }

        return Long.valueOf(value);
    }

    /** LIST OPERATIONS */
    public Long listAdd(String key, String value) {
        Long result = redisTemplate.opsForList().rightPush(key, value);
        return result;
    }

    public List<String> listGet(String key) {
        List<String> result = redisTemplate.opsForList().range(key, 0, -1);
        return result;
    }

    /** SET OPERATIONS */
    public Long setAdd(String key, String value) {
        Long result = redisTemplate.opsForSet().add(key, value);
        return result;
    }

    public Long setRemove(String key, String value) {
        Long result = redisTemplate.opsForSet().remove(key, value);
        return result;
    }

    public Set<String> setMembers(String key) {
        Set<String> members = redisTemplate.opsForSet().members(key);
        return members;
    }

    public Map<String, Long> keys(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix);
        TreeMap<String, Long> sortedScan = new TreeMap<>();

        LOGGER.info("KEYS " + keys);
        for (String key : keys) {
            sortedScan.put(key, Long.valueOf(redisTemplate.opsForValue().get(key)));
        }
        return sortedScan;
    }
}
