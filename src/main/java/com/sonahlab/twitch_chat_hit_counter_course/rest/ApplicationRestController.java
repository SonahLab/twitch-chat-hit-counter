package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * HTTP GET request handler for endpoint /api/queryGreetingEventsFromSQL. Endpoint that will:
     * call the GreetingSqlService to queryAllEvents() and return all the GreetingEvent objects.
     *
     * @return List of GreetingEvent objects currently stored in our SQL greeting_events table.
     */
    @GetMapping("/queryGreetingEventsFromSQL")
    @Operation(summary = "Query all events from dev_db.greeting_events SQL table", description = "Returns a List<GreetingEvent>")
    public List<GreetingEvent> getSqlGreetingEvents() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return null;
    }

    /**
     * TODO: Implement more API endpoints below for all other Modules.
     * */
    @GetMapping("/hitCounter")
    @Operation(summary = "Get chat counter of a streamer", description = "")
    public Map<String, Long> hitCounter(@RequestParam String channelName) {
        return null;
    }

    @GetMapping("/addChannel")
    public ResponseEntity<String> addChannel(@RequestParam String channelName) {
        return null;
    }

    @GetMapping("/getChannels")
    public Set<String> getChannels() {
        return null;
    }

    @GetMapping("/removeChannel")
    public boolean removeChannel(@RequestParam String channelName) {
        return false;
    }
}
