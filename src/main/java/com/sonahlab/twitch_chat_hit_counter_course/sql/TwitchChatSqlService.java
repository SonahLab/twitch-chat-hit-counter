package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Service layer for interacting with SQL databases to handle TwitchChatEvent data.
 *
 * Recommended Learning materials to learn Spring Boot + SQL integration:
 * - https://docs.spring.io/spring-boot/reference/data/sql.html
 */
@Service
public class TwitchChatSqlService extends AbstractSqlService<TwitchChatEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatSqlService.class);

    // Constructor
    public TwitchChatSqlService() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    @Override
    public String sqlTableName() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return "";
    }

    @Override
    public List<String> columns() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return List.of();
    }

    @Override
    protected Object[] values(TwitchChatEvent event) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return new Object[0];
    }

    @Override
    protected TwitchChatEvent parseEventFromResultSet(ResultSet rs) throws SQLException {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
