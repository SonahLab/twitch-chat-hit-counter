package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import com.sonahlab.twitch_chat_hit_counter_course.config.RedisConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Data Access Object (DAO) for managing direct interactions with the Redis data store.
 *
 * <p>This class acts as an abstraction layer above the low-level, library-specific
 * operations provided by the Spring Data {@link org.springframework.data.redis.core.RedisTemplate}.
 * </p>
 *
 * <p>By encapsulating {@code RedisTemplate} calls here, we achieve two main goals:</p>
 * <ul>
 * <li><b>Decoupling:</b> Protects service layer logic from direct knowledge of Redis data structures or commands.</li>
 * <li><b>Application Logic:</b> Allows defining custom, reusable application logic on top of raw Redis operations.</li>
 * </ul>
 *
 * Example: Incrementing a Key with Logging
 * <pre>{@code
 * public Long increment(String key) {
 *     Long newValue = redisTemplate.opsForValue().increment(key);
 *     LOGGER.info("Incremented key={} by 1. New value={}", key, newValue);
 *     ... other application logic everytime we want to increment ...
 *     return newValue;
 * }
 * }</pre>
 *
 * @see org.springframework.data.redis.core.RedisTemplate
 * @see <a href="https://redis.io/learn/develop/java/redis-and-spring-course">Redis and Spring Course (redis.io)</a>
 * @see <a href="https://www.baeldung.com/spring-data-redis-tutorial">Spring Data Redis Tutorial (Baeldung)</a>
 */
public class RedisDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisDao.class);

    // Constructor
    /**
     * Requirements:
     * - Inject the {@link RedisTemplate} that you create in {@link RedisConfig}'s @Bean redisTemplateFactory()
     * */
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
}
