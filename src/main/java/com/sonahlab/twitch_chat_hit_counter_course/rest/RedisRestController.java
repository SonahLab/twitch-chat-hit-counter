package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * REST Controller for all of our service's Redis related endpoints.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api/redis")
@Tag(name = "Redis API", description = "Backend API endpoints Redis related actions")
public class RedisRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisRestController.class);

    // Constructor
    public RedisRestController() {
        /**
         * TODO: Implement as part of Module 4
         * */
    }

    /**
     * HTTP GET request handler for endpoint /api/redis/queryGreetingFeed.
     *
     * Endpoint that will:
     * - receive some input name
     * - query our Redis db1 (used to aggregate our Greeting Feed)
     * - return a List<GreetingEvents> for all related Greetings that were sent to the user
     *
     * @param name the name of the receiver of the GreetingEvent messages
     * @return List of GreetingEvent for all previous GreetingEvents sent to the event.receiver() == 'name'
     */
    @GetMapping("/queryGreetingFeed")
    @Operation(summary = "Query all events from redis db1 for a user's Greeting feed", description = "Returns a List<GreetingEvent> of all incoming greetings")
    public List<GreetingEvent> getRedisGreetingFeed(@RequestParam(name = "name", required = false) String name) {
        /**
         * TODO: Implement as part of Module 4
         * */
        return null;
    }
}
