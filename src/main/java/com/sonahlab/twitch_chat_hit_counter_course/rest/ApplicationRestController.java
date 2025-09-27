package com.sonahlab.twitch_chat_hit_counter_course.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }

    /**
     * TODO: Implement more API endpoints below for all other Modules.
     * We will need to implement more REST endpoints for Module 7, if we want the Front end to be fully working.
     * */
}
