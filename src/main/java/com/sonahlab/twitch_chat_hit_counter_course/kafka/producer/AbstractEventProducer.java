package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public abstract class AbstractEventProducer<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEventProducer.class);

    private KafkaTemplate<String, byte[]> kafkaTemplate;
    private ObjectMapper objectMapper;

    // Constructor
    public AbstractEventProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public boolean publish(String key, T event) {
        try {
            CompletableFuture<SendResult<String, byte[]>> future = kafkaTemplate.send(topicName(), key, objectMapper.writeValueAsBytes(event));
            RecordMetadata recordMetadata = future.get().getRecordMetadata();
            LOGGER.info("Succesfully published to topic={}, key={}, message={}, partition={}, offset={}",
                    topicName(),
                    key,
                    event,
                    recordMetadata.partition(),
                    recordMetadata.offset());
            return true;
        } catch (Exception e) {
            LOGGER.error("Failed to publish to topic={}, key={}, message={}, exception={}", topicName(), key, event, e);
        }
        return false;
    }

    protected abstract String topicName();
}
