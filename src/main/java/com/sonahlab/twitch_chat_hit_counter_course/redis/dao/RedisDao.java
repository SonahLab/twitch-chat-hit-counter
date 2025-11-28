package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    // Constructor
    public RedisDao() {
        /**
         * TODO: Implement as part of Module 4
         * */
    }

    /** VALUE OPERATIONS */
    // INCR: https://redis.io/docs/latest/commands/incr/
    public Long increment(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // INCRBY: https://redis.io/docs/latest/commands/incrby/
    public Long incrementBy(String key, long delta) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // SET: https://redis.io/docs/latest/commands/set/
    public void set(String key, String value) {
        /**
         * TODO: Implement as part of Module 4
         * */
    }

    // GET: https://redis.io/docs/latest/commands/get/
    public String get(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    /** LIST OPERATIONS */
    // RPUSH: https://redis.io/docs/latest/commands/rpush/
    public Long listAdd(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // LRANGE: https://redis.io/docs/latest/commands/lrange/
    public List<String> listGet(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    /** SET OPERATIONS */
    // SADD: https://redis.io/docs/latest/commands/sadd/
    public Long setAdd(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // SREM: https://redis.io/docs/latest/commands/srem/
    public Long setRemove(String key, String... values) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // SMEMBERS: https://redis.io/docs/latest/commands/smembers/
    public Set<String> getSetMembers(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    /**
     * HASH OPERATIONS
     * https://redis.io/docs/latest/develop/data-types/hashes/
     * */
    // HINCRBY: https://redis.io/docs/latest/commands/hincrby/
    public Long hashIncrBy(String key, String field, long delta) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    // HGETALL: https://redis.io/docs/latest/commands/hgetall/
    public Map<String, String> hashGetAll(String key) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }
}
