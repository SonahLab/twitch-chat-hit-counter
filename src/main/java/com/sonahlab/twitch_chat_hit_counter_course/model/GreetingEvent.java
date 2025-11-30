package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * DTO for a basic greeting event.
 *
 * Recommended Learning materials to learn more about creating Java Objects:
 * - Java Records (https://docs.oracle.com/en/java/javase/17/language/records.html)
 * - Java Builder Patterns (https://www.baeldung.com/java-builder-pattern)
 * - Java Constructor (https://www.w3schools.com/java/java_encapsulation.asp)
 *
 * @param eventId the unique identifier of the event
 * @param sender the name of the sender
 * @param receiver the name of the receiver
 * @param message the greeting message
 */
public record GreetingEvent(String eventId, String sender, String receiver, String message) {}
