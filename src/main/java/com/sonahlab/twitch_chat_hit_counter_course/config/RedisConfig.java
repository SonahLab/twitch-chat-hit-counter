package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.List;
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
    // TODO: Add Redis configs here
    @Bean
    public Map<Integer, RedisTemplate<String, String>> redisTemplateFactory(
            @Value("${spring.data.redis.host}") String host,
            @Value("${spring.data.redis.port}") int port,
            @Value("${twitch-chat-hit-counter.redis.event-dedupe-database}") int databaseIndexDb0,
            @Value("${twitch-chat-hit-counter.redis.greeting-feed-database}") int databaseIndexDb1,
            @Value("${twitch-chat-hit-counter.redis.oauth-token-database}") int databaseIndexDb2,
            @Value("${twitch-chat-hit-counter.redis.twitch-chat-hit-counter-database}") int databaseIndexDb3,
            @Value("${twitch-chat-hit-counter.redis.chatbot-channels-database}") int databaseIndexDb4
    ) {
        Map<Integer, RedisTemplate<String, String>> factory = new HashMap<>();

        List<Integer> databaseIndexes = List.of(databaseIndexDb0, databaseIndexDb1, databaseIndexDb2, databaseIndexDb3, databaseIndexDb4);
        for (int databaseIndex : databaseIndexes) {
            RedisTemplate<String, String> template = new RedisTemplate<>();

            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(host, port);
            redisStandaloneConfiguration.setDatabase(databaseIndex);

            LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
            lettuceConnectionFactory.afterPropertiesSet();
            lettuceConnectionFactory.start();

            template.setConnectionFactory(lettuceConnectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new StringRedisSerializer());
            template.afterPropertiesSet();

            factory.putIfAbsent(databaseIndex, template);
        }

        return factory;
    }

    @Bean
    @Qualifier("eventDedupeRedisDao")
    public RedisDao eventDedupeRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${twitch-chat-hit-counter.redis.event-dedupe-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("greetingFeedRedisDao")
    public RedisDao greetingFeedRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${twitch-chat-hit-counter.redis.greeting-feed-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("oauthTokenRedisDao")
    public RedisDao oauthTokenRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${twitch-chat-hit-counter.redis.oauth-token-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("twitchChatHitCounterRedisDao")
    public RedisDao twitchChatHitCounterRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${twitch-chat-hit-counter.redis.twitch-chat-hit-counter-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("chatBotChannelsRedisDao")
    public RedisDao chatBotChannelsRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${twitch-chat-hit-counter.redis.chatbot-channels-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }
}
