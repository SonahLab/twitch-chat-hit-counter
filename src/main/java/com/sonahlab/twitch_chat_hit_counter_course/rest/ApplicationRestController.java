package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.Pokemon;
import com.sonahlab.twitch_chat_hit_counter_course.module.Module1;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private Module1 module1;

    public ApplicationRestController(Module1 module1) {
        this.module1 = module1;
    }

    /**
    * HTTP GET request handler for endpoint /api/hello. Endpoint that will receive an input name and
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
    public String sayHello(@RequestParam String name) {
        /**
         * TODO: Implement as part of Module 1
         * */
        return String.format("Hello, %s.", name);
    }

    @PutMapping("/pokemon/takeDamage")
    public Pokemon takeDamage(@RequestParam String pokemonName, @RequestParam int damage) {
        return module1.takeDamage(pokemonName, damage);
    }

    @DeleteMapping("/pokemon/releasePokemon")
    public boolean releasePokemon(@RequestParam String pokemonName) {
        return module1.releasePokemon(pokemonName);
    }

    @GetMapping("/pokemon/getAshPokemon")
    public List<Pokemon> getAshPokemon() {
        return module1.getAshPokemon();
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
        return null;
    }

    /**
     * TODO: Implement more API endpoints below for all other Modules.
     * We will need to implement more REST endpoints for Module 7, if we want the Front end to be fully working.
     * */
}
