package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.events.domain.EventChannel;
import com.github.twitch4j.common.events.domain.EventUser;

/**
 * DTO for a simplified version of Twitch4J's ChannelMessageEvent.
 *
 * We will be leveraging Twitch4J's library to integrate with the Twitch API.
 * Instead of handling complicated Webhook/Websocket logic and reading raw JSON event payloads, Twitch4J abstracts most
 * of this away from the developer and packages the raw event into the {@link ChannelMessageEvent} DTO object.
 *
 * Read up on what fields are supported through Twitch4J on each incoming ChannelMessageEvents by reading through the docs.
 * {@link ChannelMessageEvent}: https://twitch4j.github.io/javadoc/com/github/twitch4j/chat/events/channel/ChannelMessageEvent.html
 * {@link EventUser}: https://twitch4j.github.io/javadoc/com/github/twitch4j/common/events/domain/EventUser.html
 * {@link EventChannel}: https://twitch4j.github.io/javadoc/com/github/twitch4j/common/events/domain/EventChannel.html
 *
 * Required DTO fields:
 *   - eventId (String): unique eventId for the ChannelMessageEvent
 *   - eventTs (long): Timestamp millis for when the chat even was sent
 *   - channelId ({@link EventChannel id}): channel's unique ID on twitch
 *   - channelName ({@link EventChannel name}): channe's name on Twitch
 *   - userId ({@link EventUser id}): user's unique ID on Twitch
 *   - username ({@link EventUser name}): user's name on Twitch
 *   - subscriptionMonths (int): user's months of being subscribed to the channel
 *   - subscriptionTier (int): user's tier level (1,2,3) of the subscribed to channel
 *   - message (String): message of the chat event
 * */
public record TwitchChatEvent() {
    /**
     * TODO: Implement as part of Module 5
     * */
}
