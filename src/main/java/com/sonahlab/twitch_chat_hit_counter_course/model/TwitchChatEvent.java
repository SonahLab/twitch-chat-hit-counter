package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventChannel;
import com.github.twitch4j.common.events.domain.EventUser;

/**
 * Data Model for a simplified version of Twitch4J's ChannelMessageEvent.
 *
 * Read up on what fields are supported through Twitch4J on each incoming ChannelMessageEvents by reading through the docs.
 * {@link ChannelMessageEvent}: https://twitch4j.github.io/javadoc/com/github/twitch4j/chat/events/channel/ChannelMessageEvent.html
 * {@link EventUser}: https://twitch4j.github.io/javadoc/com/github/twitch4j/common/events/domain/EventUser.html
 * {@link EventChannel}: https://twitch4j.github.io/javadoc/com/github/twitch4j/common/events/domain/EventChannel.html
 *
 * Required fields:
 *   - event_id (String): unique eventId for the ChannelMessageEvent
 *   - message (String): raw message of the ChannelMessageEvent
 *   - event_ts (long): Timestamp millis
 *   - user_id ({@link EventUser id}):
 *   - username ({@link EventUser name}):
 *   - channel_id ({@link EventChannel id}):
 *   - channel_name ({@link EventChannel name}):
 *   - subscription_months (int)
 *   - subscription_tier (int)
 * */
public record TwitchChatEvent() {
    /**
     * TODO: Implement as part of Module 5
     * */
}
