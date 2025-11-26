package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.github.twitch4j.TwitchClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Config class for all of our application's Twitch API related beans. The beans defined here are singletons that we
 * can use and inject throughout any other classes in our app.
 *
 * Recommended learning materials to learn about how Spring Boot sets up Configurations:
 *  - Configuration (https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html)
 *  - Bean (https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html)
 */
@Configuration
@PropertySource("classpath:twitch-key.properties")
public class TwitchConfig {

    public String getTwitchApiClientId() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    public String getTwitchApiClientSecret() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @Bean
    public TwitchClient twitchClient() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
