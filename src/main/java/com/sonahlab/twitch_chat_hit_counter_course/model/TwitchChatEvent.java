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
 * Required fields (IN THIS ORDER):
 *   - event_id (String): unique eventId for the ChannelMessageEvent
 *   - event_ts (long): Timestamp millis for when the chat even was sent
 *   - channel_id ({@link EventChannel id}): channel's unique ID on twitch
 *   - channel_name ({@link EventChannel name}): channe's name on Twitch
 *   - user_id ({@link EventUser id}): user's unique ID on Twitch
 *   - username ({@link EventUser name}): user's name on Twitch
 *   - subscription_months (int): user's months of being subscribed to the channel
 *   - subscription_tier (int): user's tier level (1,2,3) of the subscribed to channel
 *   - message (String): message of the chat event
 * */
public record TwitchChatEvent() {
    /**
     * TODO: Implement as part of Module 5
     * */
}
