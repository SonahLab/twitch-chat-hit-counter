package com.sonahlab.twitch_chat_hit_counter_course.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for all of our service's simple greeting endpoints. These endpoints will be accessed by
 * SwaggerUI and the React frontend app.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api/greeting")
@Tag(name = "Greeting API", description = "Backend API endpoints for basic Greeting HTTP requests")
public class GreetingRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingRestController.class);

    private static final String GREETING_TEMPLATE = "Hello, %s!";

    /**
     * HTTP GET request handler for endpoint /api/greeting/hello. Endpoint that will receive an input name and
     * return a basic greeting.
     *
     * Return a string response using this template, "Hello, {name}".
     * If no name is input, the default name that will be expected to be used should be "Mysterious Individual".
     *
     * @param name The name of the person to greet
     * @return String greeting message
     */
    @GetMapping("/hello")
    @Operation(summary = "Say Hello", description = "Returns a greeting message")
    public String sayHello(@RequestParam(required = false) String name) {
        /**
         * TODO: Implement as part of Module 1 Exercise 1.
         * */
        LOGGER.info("sayHello() called for {}", name);

        if (StringUtils.isEmpty(name)) {
            name = "Mysterious Individual";
        }

        String greeting = String.format(GREETING_TEMPLATE, name);
        LOGGER.info("sayHello() message: {}", greeting);
        return String.format(greeting, name);
    }
}
