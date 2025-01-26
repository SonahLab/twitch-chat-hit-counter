package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Kafka producer to write GreetingEvents to Module 2 topic.
 *
 * Recommended Learning materials:
 * - High Level Kafka Overview (https://kafka.apache.org/intro)
 * - Spring Boot Kafka (https://www.baeldung.com/spring-kafka)
 */
@Component
public class GreetingEventProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingEventProducer.class);

    private String topic;
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private ObjectMapper objectMapper;

    /**
     * TODO: Implement as part of Module 2
     * */
    public GreetingEventProducer(
            @Value("${twitch-chat-hit-counter.kafka.producer.greeting-topic}") String topic,
            @Qualifier("greeting-producer") KafkaTemplate<String, byte[]> kafkaTemplate,
            ObjectMapper objectMapper) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public boolean publish(String key, GreetingEvent event) {
        try {
            CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(
                    topic, key, objectMapper.writeValueAsBytes(event));
            RecordMetadata recordMetadata = future.get().getRecordMetadata();
            LOGGER.info("Succesfully published to topic={}, key={}, message={}, partition={}, offset={}",
                    topic,
                    key,
                    event,
                    recordMetadata.partition(),
                    recordMetadata.offset());
            return true;
        } catch (JsonProcessingException | ExecutionException | InterruptedException e) {
            LOGGER.error("Failed to publish to topic={}, key={}, message={}, exception={}", topic, key, event, e);
        }
        return false;
    }
}
