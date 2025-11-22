package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.github.twitch4j.common.events.domain.EventChannel;
import com.github.twitch4j.common.events.domain.EventUser;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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

    private String sqlTableName;

    // Constructor
    public TwitchChatSqlService(JdbcTemplate jdbcTemplate,
                                @Value("${twitch-chat-hit-counter.sql.twitch-chat-table}") String sqlTableName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        super(jdbcTemplate);
        this.sqlTableName = sqlTableName;
    }

    @Override
    public String sqlTableName() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return sqlTableName;
    }

    @Override
    public List<String> columns() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return List.of("event_id", "message", "event_ts", "user_id", "username", "channel_id", "channel_name", "subscription_months", "subscription_tier");
    }

    @Override
    protected Object[] values(TwitchChatEvent event) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return new Object[]{
                event.eventId(),
                event.message(),
                event.eventTs(),
                event.userId(),
                event.username(),
                event.channelId(),
                event.channelName(),
                event.subscriptionMonths(),
                event.subscriptionTier()
        };
    }

    @Override
    protected TwitchChatEvent parseEventFromResultSet(ResultSet rs) throws SQLException {
        /**
         * TODO: Implement as part of Module 5
         * */
        TwitchChatEvent twitchChatEvent = new TwitchChatEvent(
                rs.getString("event_id"),
                rs.getString("message"),
                rs.getLong("event_ts"),
                rs.getString("user_id"),
                rs.getString("username"),
                rs.getString("channel_id"),
                rs.getString("channel_name"),
                rs.getInt("subscription_months"),
                rs.getInt("subscription_tier")
        );
        return twitchChatEvent;
    }
}
