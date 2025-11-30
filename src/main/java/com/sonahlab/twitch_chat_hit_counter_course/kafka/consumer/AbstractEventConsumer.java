package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.model.EventType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.Acknowledgment;

import java.util.List;

/**
 * Abstract base class for Kafka event consumers.
 * This class provides the exoskeleton for implementing event consumption logic,
 * including defining the event type, deserialization, core business logic,
 * and handling message acknowledgment and deduplication.
 *
 * @param <T> The type of the domain event that this consumer is designed to process.
 */
public abstract class AbstractEventConsumer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventConsumer.class);

    // Constructor
    public AbstractEventConsumer() {
        /**
         * TODO: Implement as part of Module 3+
         * */
    }

    /**
     * Defines the specific type identifier (e.g., an Enum value) for the event
     * that this consumer handles. This is often used for filtering or routing.
     *
     * @return The {@link EventType} associated with this consumer.
     */
    protected abstract EventType eventType();

    /**
     * Defines the concrete class of the event {@code T} that the consumer expects
     * to deserialize the Kafka message value into.
     *
     * @return The {@code Class<T>} object representing the event type.
     */
    protected abstract Class<T> eventClass();

    /**
     * Extracts a unique key from the given event. This key is often used for
     * ensuring message ordering within a partition or for event deduplication.
     *
     * @param event The event instance from which to extract the key.
     * @return A {@link String} representing the unique key of the event.
     */
    protected abstract String eventKey(T event);

    /**
     * Contains the core business logic to be executed for a batch of consumed events.
     * This method is where the consumer processes the fully deserialized events.
     *
     * @param events A {@link List} of deserialized events (type {@code T}) to process.
     */
    protected abstract void coreLogic(List<T> events);

    /**
     * Processes a single Kafka {@link ConsumerRecord}. This method is typically
     * called by the Spring Kafka listener for single-message consumption.
     * It handles deserialization and calls the core logic for a single event.
     *
     * @param record The raw Kafka {@link ConsumerRecord} containing the key and byte array value.
     * @param ack The {@link Acknowledgment} object used to commit the offset after successful processing.
     */
    public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         * */
    }

    /**
     * Processes a batch of Kafka {@link ConsumerRecord}s. This method is typically
     * called by the Spring Kafka listener when batch consumption is enabled.
     * It handles deserialization for the batch and calls the core logic with the list of events.
     *
     * @param records A {@link List} of raw Kafka {@link ConsumerRecord}s.
     * @param ack The {@link Acknowledgment} object used to commit the offset for the entire batch.
     */
    public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack) {
        /**
         * TODO: Implement as part of Module 2 Exercise 4
         * */
    }

    /**
     * A placeholder method intended to check if an event is a duplicate based on its key
     * and potentially persistence layer state.
     */
    protected void isDupeEvent() {
        /**
         * TODO: Implement as part of Module 4
         * */
    }

    /**
     * Deserializes the raw byte array value from a Kafka record into the concrete event type {@code T}.
     * This method typically uses an external library like Jackson for JSON deserialization.
     *
     * @param value The byte array representing the event data.
     * @return The deserialized event object of type {@code T}, or {@code null} if deserialization fails.
     */
    protected T convertRecordToEvent(byte[] value) {
        /**
         * TODO: Implement as part of Module 2 Exercise 3
         */
        return null;
    }
}
