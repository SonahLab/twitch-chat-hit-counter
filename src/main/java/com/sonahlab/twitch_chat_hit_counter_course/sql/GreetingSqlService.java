package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;

/**
 * Service layer for interacting with SQL databases to handle GreetingEvent data.
 *
 * Recommended Learning materials to learn Spring Boot + SQL integration:
 * - https://docs.spring.io/spring-boot/reference/data/sql.html
 */
public class GreetingSqlService extends AbstractSqlService<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingSqlService.class);

    // Constructor
    public GreetingSqlService() {
        /**
         * TODO: Implement as part of Module 3
         * */
    }

    @Override
    public String sqlTableName() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return "";
    }

    @Override
    public List<String> columns() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return List.of();
    }

    @Override
    protected void bind(PreparedStatement ps, GreetingEvent event) {
        /**
         * TODO: Implement as part of Module 3
         * */
    }
}
