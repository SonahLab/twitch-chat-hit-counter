package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.github.twitch4j.helix.domain.User;
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
@RequestMapping("/api/twitchReporting")
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
    public Map<String, Long> hitCounter(
            @RequestParam(name = "granularity") String granularity,
            @RequestParam(name = "channelName") String channelName,
            @RequestParam(name = "startTimestampMillis") long startTimestampMillis,
            @RequestParam(name = "endTimestampMillis") long endTimestampMillis
    ) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @PutMapping("/addChannel")
    public ResponseEntity addChannel(@RequestParam(name = "channelName") String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @DeleteMapping("/removeChannel")
    public boolean removeChannel(@RequestParam(name = "channelName") String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return false;
    }

    @GetMapping("/getChannels")
    public ResponseEntity<Set<String>> getChannels() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    @GetMapping("/getChannelsMetadata")
    public ResponseEntity<Map<String, User>> getChannelsMetadata() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }
}
