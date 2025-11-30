package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Config class for all of our application's beans. The beans defined here are singletons that we
 * can use and inject throughout any other classes in our app.
 *
 * Recommended learning materials to learn about how Spring Boot sets up Configurations:
 *  - Configuration (https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html)
 *  - Bean (https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html)
 */
@Configuration
public class SqlConfig {
    // TODO: Add Sql configs here
    @Bean
    @Qualifier("singleGreetingSqlService")
    public GreetingSqlService singleGreetingSqlService(
            JdbcTemplate jdbcTemplate,
            @Value("${twitch-chat-hit-counter.sql.greeting-table}") String greetingEventsTableName) {
        return new GreetingSqlService(jdbcTemplate, greetingEventsTableName);
    }

    @Bean
    @Qualifier("batchGreetingSqlService")
    public GreetingSqlService batchGreetingSqlService(
            JdbcTemplate jdbcTemplate,
            @Value("${twitch-chat-hit-counter.sql.greeting-table-batch}") String batchGreetingEventsTableName) {
        return new GreetingSqlService(jdbcTemplate, batchGreetingEventsTableName);
    }
}

