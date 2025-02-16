package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize
public record TwitchChatEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("message") String message,
        @JsonProperty("messageContext") MessageContext messageContext,
        @JsonProperty("user") User user) {

    public record User(
            @JsonProperty("userId") String userId,
            @JsonProperty("username") String username) {}

    public record MessageContext(
            @JsonProperty("channelId") String channelId,
            @JsonProperty("channelName") String channelName,
            @JsonProperty("eventTs") long eventTs,
            @JsonProperty("subscriptionMonths") int subscriptionMonths,
            @JsonProperty("subscriptionTier") int subscriptionTier) {}
}