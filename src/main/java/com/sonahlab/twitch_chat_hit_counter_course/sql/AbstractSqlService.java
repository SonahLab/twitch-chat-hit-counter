package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSqlService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSqlService.class);
    private static final String SQL_WRITE_TEMPLATE = """
            INSERT IGNORE INTO %s (%s)
            VALUES (%s)
            """;
    private static final String QUERY_SQL_TEMPLATE = "SELECT * FROM %s";

    private JdbcTemplate jdbcTemplate;

    // Constructor
    public AbstractSqlService(JdbcTemplate jdbcTemplate) {
        /**
         * TODO: Implement as part of Module 3
         * */
        this.jdbcTemplate = jdbcTemplate;
    }

    public abstract String sqlTableName();

    public abstract List<String> columns();

    protected abstract Object[] values(T event);

    protected abstract T parseEventFromResultSet(ResultSet rs) throws SQLException;

    public int insert(List<T> events) {
        /**
         * TODO: Implement as part of Module 3
         * */
        List<String> valuePlaceholders = new ArrayList<>();
        for (int index = 0; index < columns().size(); index++) {
            valuePlaceholders.add("?");
        }
        String sql = String.format(
                SQL_WRITE_TEMPLATE,
                sqlTableName(),
                String.join(",", columns()),
                String.join(",", valuePlaceholders));

        if (events.isEmpty()) {
            return 0;
        } else if (events.size() == 1) {
            return jdbcTemplate.update(sql, values(events.get(0)));
        } else {
            List<Object[]> batchArgs = events.stream()
                    .map(e -> values(e))
                    .toList();
            int[] result = jdbcTemplate.batchUpdate(sql, batchArgs);
            return Arrays.stream(result).sum();
        }
    }

    public List<T> queryAllEvents() {
        /**
         * TODO: Implement as part of Module 3
         * */
        String sql = String.format(QUERY_SQL_TEMPLATE, sqlTableName());

        List<T> events = jdbcTemplate.query(sql, (rs, rowNum) -> parseEventFromResultSet(rs));
        return events;
    }
}
