package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
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

    private GreetingSqlService singleGreetingSqlService;
    private GreetingSqlService batchGreetingSqlService;

    // Constructor
    public SqlRestController(
            @Qualifier("singleGreetingSqlService") GreetingSqlService singleGreetingSqlService,
            @Qualifier("batchGreetingSqlService") GreetingSqlService batchGreetingSqlService) {
        /**
         * TODO: Implement as part of Module 3
         * */
        this.singleGreetingSqlService = singleGreetingSqlService;
        this.batchGreetingSqlService = batchGreetingSqlService;
    }

    /**
     * HTTP GET request handler for endpoint /api/sql/queryGreetingEvents. Endpoint that will:
     * call the GreetingSqlService to queryAllEvents() and return all the GreetingEvent objects.
     *
     * @return List of GreetingEvent objects currently stored in our SQL greeting_events table.
     */
    @GetMapping("/queryGreetingEvents")
    @Operation(summary = "Query all Greeting events from dev_db.{tableName} SQL table", description = "Returns a List<GreetingEvent>")
    public List<GreetingEvent> getSqlGreetingEvents(@RequestParam(name = "tableName") String tableName) {
        /**
         * TODO: Implement as part of Module 3
         * */
        LOGGER.info("/queryGreetingEvents tableName: {}", tableName);
        if (singleGreetingSqlService.sqlTableName().equals(tableName)) {
            LOGGER.info("Querying {}", singleGreetingSqlService.sqlTableName());
            return singleGreetingSqlService.queryAllEvents();
        } else if (batchGreetingSqlService.sqlTableName().equals(tableName)) {
            LOGGER.info("Querying {}", batchGreetingSqlService.sqlTableName());
            return batchGreetingSqlService.queryAllEvents();
        } else {
            throw new UnsupportedOperationException("Unsupported table name: " + tableName);
        }
    }
}
