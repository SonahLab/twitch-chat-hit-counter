package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * Defines the Event type.
 *
 * Our service will handle multiple types of Events and you will have a single Redis DB for deduplication purposes.
 * This enum will give us a clean way of separating eventId's that have been processed.
 * Redis DB0 Key: '{eventType}#{eventId}'
 * */
public enum EventType {
    GREETING_EVENT
}
