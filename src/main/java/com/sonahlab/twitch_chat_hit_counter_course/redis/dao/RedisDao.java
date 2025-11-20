package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
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

    private RedisTemplate<String, String> redisTemplate;

    // Constructor
    public RedisDao(RedisTemplate<String, String> redisTemplate) {
        /**
         * TODO: Implement as part of Module 4
         * */
        this.redisTemplate = redisTemplate;
    }

    /** VALUE OPERATIONS */
    // INCR: https://redis.io/docs/latest/commands/incr/
    public Long increment(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForValue().increment(key);
    }

    // INCRBY: https://redis.io/docs/latest/commands/incrby/
    public Long incrementBy(String key, long delta) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForValue().increment(key, delta);
    }

    // SET: https://redis.io/docs/latest/commands/set/
    public void set(String key, String value) {
        /**
         * TODO: Implement as part of Module 4
         * */
        redisTemplate.opsForValue().set(key, value);
    }

    // GET: https://redis.io/docs/latest/commands/get/
    public String get(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForValue().get(key);
    }

    /** LIST OPERATIONS */
    // LPUSH: https://redis.io/docs/latest/commands/lpush/
    public Long listAdd(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    // LRANGE: https://redis.io/docs/latest/commands/lrange/
    public List<String> listGet(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /** SET OPERATIONS */
    // SADD: https://redis.io/docs/latest/commands/sadd/
    public Long setAdd(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForSet().add(key, values);
    }

    // SREM: https://redis.io/docs/latest/commands/srem/
    public Long setRemove(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForSet().remove(key, values);
    }

    // SMEMBERS: https://redis.io/docs/latest/commands/smembers/
    public Set<String> getSetMembers(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return redisTemplate.opsForSet().members(key);
    }

    /** KEY OPERATIONS */
    // KEYS: https://redis.io/docs/latest/commands/keys/
    public Map<String, String> scanKeys(String prefix) {
        /**
         * TODO: Implement as part of Module 4
         * */
        Map<String, String> result = new HashMap<>();

        Set<String> keys = redisTemplate.keys(prefix + "*");
        try {
            // First, determine the type
            for (String key : keys) {
                String type = redisTemplate.type(key).code();

                String value = null;
                switch (type) {
                    case "string" -> value = get(key);
                    case "list"   -> value = listGet(key).toString();
                    case "set"    -> value = getSetMembers(key).toString();
                    default       -> throw new RuntimeException("Unsupported type: " + type);
                };
                result.put(key, value);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return result;
    }
}
