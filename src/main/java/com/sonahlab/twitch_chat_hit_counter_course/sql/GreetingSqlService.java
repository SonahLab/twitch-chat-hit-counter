package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for interacting with SQL databases to handle GreetingEvent data.
 *
 * Recommended Learning materials to learn Spring Boot + SQL integration:
 * - https://docs.spring.io/spring-boot/reference/data/sql.html
 */
public class GreetingSqlService extends AbstractSqlService<GreetingEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingSqlService.class);

    private String sqlTableName;

    // Constructor
    public GreetingSqlService(
            JdbcTemplate jdbcTemplate,
            String sqlTableName) {
        /**
         * TODO: Implement as part of Module 3
         * */
        super(jdbcTemplate);
        this.sqlTableName = sqlTableName;
    }

    @Override
    public String sqlTableName() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return sqlTableName;
    }

    @Override
    public List<String> columns() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return List.of("event_id", "sender", "receiver", "message");
    }

    @Override
    protected Object[] values(GreetingEvent event) {
        /**
         * TODO: Implement as part of Module 3
         * */
        return new Object[]{ event.eventId(), event.sender(), event.receiver(), event.message() };
    }

    @Override
    protected GreetingEvent parseEventFromResultSet(ResultSet rs) throws SQLException {
        return new GreetingEvent(
                rs.getString("event_id"),
                rs.getString("sender"),
                rs.getString("receiver"),
                rs.getString("message"));
    }
}
