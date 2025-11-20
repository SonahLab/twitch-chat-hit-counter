package com.sonahlab.twitch_chat_hit_counter_course.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

/**
 * REST Controller for all of our service's backend endpoint required by our Frontend application.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Twitch Chat Hit Counter API", description = "Backend API endpoints needed to interact with our Frontend application")
public class TwitchRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchRestController.class);

    // Constructor
    public TwitchRestController() {
        /**
         * TODO: Implement as part of Module 6
         * */
    }

    @GetMapping("/hitCounter")
    @Operation(summary = "Get chat counter of a streamer", description = "")
    public Map<String, Long> hitCounter(@RequestParam String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @GetMapping("/addChannel")
    public ResponseEntity<String> addChannel(@RequestParam String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @GetMapping("/getChannels")
    public Set<String> getChannels() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @GetMapping("/removeChannel")
    public boolean removeChannel(@RequestParam String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return false;
    }
}
