package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * A POJO data model event for basic greeting event.
 *
 * Recommended Learning materials to learn more about creating Java Objects:
 * - Java Records (https://docs.oracle.com/en/java/javase/17/language/records.html)
 * - Java Builder Patterns (https://www.baeldung.com/java-builder-pattern)
 *
 * @param sender the name of the sender
 * @param receiver the name of the receiver
 * @param message the greeting message
 */
public record GreetingEvent(String sender, String receiver, String message) {}
