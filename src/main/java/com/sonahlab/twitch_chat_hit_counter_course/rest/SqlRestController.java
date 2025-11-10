package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for all of our SQL DB related endpoints. These endpoints will be accessed by
 * SwaggerUI and the React frontend app.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api/sql")
@Tag(name = "SQL API", description = "Backend API endpoints for SQL related actions")
public class SqlRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SqlRestController.class);

    // Constructor
    public SqlRestController() {
        /**
         * TODO: Implement as part of Module 3
         * */
    }

    /**
     * HTTP GET request handler for endpoint /api/sql/queryGreetingEventsFromSQL. Endpoint that will:
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
}
