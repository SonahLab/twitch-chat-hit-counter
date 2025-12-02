package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.GreetingEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST Controller for all of our service's Kafka related endpoints.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api/kafka")
@Tag(name = "Kafka API", description = "Backend API endpoints Kafka related actions")
public class KafkaRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaRestController.class);

    private GreetingEventProducer greetingEventProducer;

    // Constructor
    public KafkaRestController(GreetingEventProducer greetingEventProducer) {
        /**
         * TODO: Implement as part of Module 2
         * */
        this.greetingEventProducer = greetingEventProducer;
    }

    /**
     * HTTP POST request handler for endpoint /api/kafka/publishGreetingEvent. Endpoint that will:
     * receive some input parameters, create a data model POJO GreetingEvent, produce the event to
     * a kafka topic.
     *
     * @param sender the name of the sender
     * @param receiver the name of the receiver
     * @param message the greeting message
     * @return Boolean status of kafka producer send() operation
     */
    @PostMapping("/publishGreetingEvent")
    @Operation(summary = "Publish Kafka Event", description = "Publish a GreetingEvent")
    public Boolean produceKafkaGreetingEvent(
            @RequestParam(name = "sender") String sender,
            @RequestParam(name = "receiver") String receiver,
            @RequestParam(name = "message") String message) {
        /**
         * TODO: Implement as part of Module 2
         * */
        String eventId = UUID.randomUUID().toString();
        return greetingEventProducer.publish(eventId, new GreetingEvent(eventId, sender, receiver, message));
    }
}
