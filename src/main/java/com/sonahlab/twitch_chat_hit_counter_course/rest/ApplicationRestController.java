package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.GreetingEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.kafka.shaded.com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * REST Controller for all of our service's backend endpoints. These endpoints will be accessed by
 * SwaggerUI and the React frontend app.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Twitch Chat Hit Counter API", description = "Backend API endpoints controller")
public class ApplicationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRestController.class);

    private ObjectMapper objectMapper;
    private GreetingEventProducer greetingEventProducer;

    public ApplicationRestController(
            ObjectMapper objectMapper,
            GreetingEventProducer greetingEventProducer) {
        this.objectMapper = objectMapper;
        this.greetingEventProducer = greetingEventProducer;
    }

    /**
    * HTTP GET request handler for endpoint /api/hello. Endpoint that will receive an input name and
    * return a custom greeting.
    *
    * @param name The name of the person to greet
    * @return String greeting message
    */
    @GetMapping("/hello")
    @Operation(summary = "Say Hello", description = "Returns a greeting message")
    public String sayHello(@RequestParam String name) {
        /**
         * TODO: Implement as part of Module 1
         * */
        return null;
    }

    /**
    * HTTP POST request handler for endpoint /api/publishGreetingEvent. Endpoint that will:
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
    public Boolean produceKafkaGreetingEvent(@RequestParam String sender, @RequestParam String receiver, @RequestParam String message) {
        /**
         * TODO: Implement as part of Module 2
         * */
        String key = String.format("%s#%s#%s", sender, receiver, UUID.randomUUID());
        GreetingEvent event = new GreetingEvent(sender, receiver, message);
        boolean isPublished = greetingEventProducer.publish(key, event);
        return isPublished;
    }

    /**
     * TODO: Implement more API endpoints below
     * */
}
