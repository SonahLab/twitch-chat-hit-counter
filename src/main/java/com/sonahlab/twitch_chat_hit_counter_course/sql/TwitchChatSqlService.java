package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TwitchChatSqlService {
    private String sqlTableName;
    private JdbcTemplate jdbcTemplate;

    public TwitchChatSqlService(@Value("${twitch-chat-hit-counter.sql.twitch-chat-event-table}") String sqlTableName,
                                JdbcTemplate jdbcTemplate) {
        this.sqlTableName = sqlTableName;
        this.jdbcTemplate = jdbcTemplate;
    }

    public int writeChatEvent(TwitchChatEvent chatEvent) {
        String sql = String.format("INSERT INTO %s (event_id, message, channel_id, channel_name, event_ts, subscription_months, subscription_tier, user_id, username) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", sqlTableName);

        int successfulWrites = jdbcTemplate.update(sql,
                chatEvent.eventId(),
                chatEvent.message(),
                chatEvent.messageContext().channelId(),
                chatEvent.messageContext().channelName(),
                chatEvent.messageContext().eventTs(),
                chatEvent.messageContext().subscriptionMonths(),
                chatEvent.messageContext().subscriptionTier(),
                chatEvent.user().userId(),
                chatEvent.user().username());
        return successfulWrites;
    }
}
