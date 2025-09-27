package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.Pokemon;
import com.sonahlab.twitch_chat_hit_counter_course.repository.AshPokemonManager;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
@Tag(name = "Pokemon API", description = "Backend API endpoints controller")
public class PokemonRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PokemonRestController.class);

    private AshPokemonManager ashPokemonManager;

    public PokemonRestController(AshPokemonManager ashPokemonManager) {
        this.ashPokemonManager = ashPokemonManager;
    }

    /**
     * HTTP PUT request handler for endpoint /api/pokemon/takeDamage. This endpoint updates the health of a specified
     * Pokémon in Ash's collection by reducing its health by the given damage amount. The method interacts with a static data
     * store (to be defined in AshPokemonManager.java) to retrieve and update the Pokémon's data.
     *
     * The goal of the implementation is to locate the Pokémon by its name, decrease its health by the specified damage
     * (ensuring health does not go below 0), and return the updated Pokémon object. If the Pokémon does not exist in the
     * collection, the method should return null. This simulates a real-world scenario where a Pokémon takes damage in a battle
     * and its state is updated accordingly.
     *
     * @param pokemonName The name of the Pokémon to take damage (case-sensitive).
     * @param damage The integer amount of damage to deal to the Pokémon. Must be non-negative.
     * @return The updated {@link Pokemon} object with the new health value after taking damage, or {@code null} if no Pokémon
     *         with the given name exists in Ash's collection.
     */
    @PutMapping("/pokemon/takeDamage")
    public Pokemon takeDamage(@RequestParam String pokemonName, @RequestParam int damage) {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return null;
    }

    /**
     * HTTP DELETE request handler for endpoint /api/pokemon/releasePokemon. This endpoint removes a specified Pokémon
     * from Ash's collection based on its name. The method interacts with a static Array (to be defined in AshPokemonManager.java)
     * to perform the removal.
     *
     * The goal of the implementation is to find the Pokémon by its name and remove it from the collection, returning
     * {@code true} if the removal was successful. If no Pokémon with the given name exists, the method should return
     * {@code false}. This simulates releasing a Pokémon back into the wild, effectively removing it from Ash's ownership.
     *
     * @param pokemonName The name of the Pokémon to release (case-sensitive).
     * @return {@code true} if the Pokémon was successfully removed from the collection, {@code false} if no Pokémon with the
     *         given name exists.
     */
    @DeleteMapping("/pokemon/releasePokemon")
    public boolean releasePokemon(@RequestParam String pokemonName) {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return false;
    }

    /**
     * Handles an HTTP GET request to the endpoint /api/pokemon/getAshPokemon. This endpoint retrieves the list of all Pokémon
     * currently in Ash's collection. The method queries a static data store (to be defined in AshPokemonManager.java)
     * to obtain the collection.
     *
     * The goal of the implementation is to return a list of all Pokémon objects in Ash's possession. If the collection is
     * empty, an empty list should be returned. This provides a way to view the current state of Ash's Pokémon roster, useful
     * for monitoring or displaying the collection in a user interface.
     *
     * @return A {@link List} of {@link Pokemon} objects representing all Pokémon in Ash's collection. Returns an empty list if
     *         no Pokémon are present.
     */
    @GetMapping("/pokemon/getAshPokemon")
    public List<Pokemon> getAshPokemon() {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return new ArrayList<>();
    }
}
