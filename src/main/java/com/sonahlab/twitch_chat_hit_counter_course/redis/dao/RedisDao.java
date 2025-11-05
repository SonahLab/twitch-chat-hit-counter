package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * TODO: Implement as part of Module 4
     * */
    public RedisDao(RedisTemplate<String, String> redisTemplate) {}

    /** VALUE OPERATIONS */
    // INCR: https://redis.io/docs/latest/commands/incr/
    public Long increment(String key) {
        return null;
    }

    // INCRBY: https://redis.io/docs/latest/commands/incrby/
    public Long incrementBy(String key, long delta) {
        return null;
    }

    // SET: https://redis.io/docs/latest/commands/set/
    public void set(String key, String value) {}

    // GET: https://redis.io/docs/latest/commands/get/
    public String get(String key) {
        return null;
    }

    /** LIST OPERATIONS */
    // LPUSH: https://redis.io/docs/latest/commands/lpush/
    public Long listAdd(String key, String... values) {
        return null;
    }

    // LRANGE: https://redis.io/docs/latest/commands/lrange/
    public List<String> listGet(String key) {
        return null;
    }

    /** SET OPERATIONS */
    // SADD: https://redis.io/docs/latest/commands/sadd/
    public Long setAdd(String key, String... values) {
        return null;
    }

    // SREM: https://redis.io/docs/latest/commands/srem/
    public Long setRemove(String key, String... values) {
        return null;
    }

    // SMEMBERS: https://redis.io/docs/latest/commands/smembers/
    public Set<String> getSetMembers(String key) {
        return null;
    }

    /** KEY OPERATIONS */
    // KEYS: https://redis.io/docs/latest/commands/keys/
    public Map<String, String> scanKeys(String prefix) {
        return null;
    }
}
