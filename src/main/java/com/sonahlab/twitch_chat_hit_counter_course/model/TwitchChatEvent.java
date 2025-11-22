package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.fasterxml.jackson.annotation.JsonProperty;
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
 *   - eventId (String): unique eventId for the ChannelMessageEvent
 *   - message (String): raw message of the ChannelMessageEvent
 *   - eventTs (long): Timestamp millis
 *   - user ({@link EventUser}):
 *      - id (String):
 *      - name (String):
 *   - channel ({@link EventChannel}):
 *      - id (String)
 *      - name (String)
 *   - subscriptionMonths (int)
 *   - subscriptionTier (int)
 * */
public record TwitchChatEvent(
        @JsonProperty("event_id") String eventId,
        @JsonProperty("message") String message,
        @JsonProperty("event_ts") long eventTs,
        @JsonProperty("user_id") String userId,
        @JsonProperty("username") String username,
        @JsonProperty("channel_id") String channelId,
        @JsonProperty("channel_name") String channelName,
        @JsonProperty("subscription_months") int subscriptionMonths,
        @JsonProperty("subscription_tier") int subscriptionTier) {
}
