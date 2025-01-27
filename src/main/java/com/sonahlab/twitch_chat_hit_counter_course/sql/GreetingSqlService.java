package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Service layer for interacting with SQL databases to handle greeting-related data.
 *
 * Recommended Learning materials to learn Spring Boot + SQL integration:
 * - https://docs.spring.io/spring-boot/reference/data/sql.html
 */
@Service
public class GreetingSqlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingSqlService.class);
    private static final String INSERT_SQL_TEMPLATE = "INSERT INTO %s (sender, receiver, message) VALUES (?, ?, ?)";
    private static final String QUERY_SQL_TEMPLATE = "SELECT * FROM %s";

    private String sqlTableName;
    private JdbcTemplate jdbcTemplate;

    /**
     * TODO: Implement as part of Module 3
     * */
    public GreetingSqlService(
            @Value("${twitch-chat-hit-counter.sql.greeting-table}") String sqlTableName,
            JdbcTemplate jdbcTemplate) {
        this.sqlTableName = sqlTableName;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int insert(GreetingEvent event) {
        String sql = String.format(INSERT_SQL_TEMPLATE, sqlTableName);

        int successfulWrites = jdbcTemplate.update(sql, event.sender(), event.receiver(), event.message());
        LOGGER.info("Successfully wrote {} events to SQL table={}, event={}", successfulWrites, sqlTableName, event);

        return successfulWrites;
    }

    public int insertBatch(List<GreetingEvent> events) {
        String sql = String.format(INSERT_SQL_TEMPLATE, sqlTableName);

        int[] result = jdbcTemplate.execute(sql, (PreparedStatementCallback<int[]>) ps -> {
            for (GreetingEvent event : events) {
                ps.setString(1, event.sender());
                ps.setString(2, event.receiver());
                ps.setString(3, event.message());
                ps.addBatch();
            }
            return ps.executeBatch();
        });

        int successfulWrites = Arrays.stream(result).sum();
        LOGGER.info("Successfully wrote {} events to SQL table={}, events={}", successfulWrites, sqlTableName, events);

        return successfulWrites;
    }

    public List<GreetingEvent> queryAllEvents() {
        String sql = String.format(QUERY_SQL_TEMPLATE, sqlTableName);

        List<GreetingEvent> events = jdbcTemplate.query(sql, (rs, rowNum) -> new GreetingEvent(
                rs.getString("sender"),
                rs.getString("receiver"),
                rs.getString("message")));
        return events;
    }
}
