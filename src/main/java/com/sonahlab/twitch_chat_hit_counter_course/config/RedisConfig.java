package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

/**
 * Config class for all of our application's beans. The beans defined here are singletons that we
 * can use and inject throughout any other classes in our app.
 *
 * Recommended learning materials to learn about how Spring Boot sets up Configurations:
 *  - Configuration (https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html)
 *  - Bean (https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html)
 */
@Configuration
public class RedisConfig {

    @Bean
    public Map<Integer, RedisTemplate<String, String>> redisTemplateFactory() {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    @Bean
    @Qualifier("eventDedupeRedisDao")
    public RedisDao eventDedupeRedisDao() {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    @Bean
    @Qualifier("greetingRedisDao")
    public RedisDao greetingRedisDao() {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }

    @Bean
    @Qualifier("oauthTokenRedisDao")
    public RedisDao oauthTokenRedisDao() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @Bean
    @Qualifier("twitchChatHitCounterRedisDao")
    public RedisDao twitchChatHitCounterRedisDao() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @Bean
    @Qualifier("chatBotChannelsRedisDao")
    public RedisDao chatBotChannelsRedisDao() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }
}
