package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.model.GameCharacter;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Potion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * REST Controller for managing character state in the game. These endpoints allow interaction with a character's
 * health points (HP), mana points (MP), and potion inventory via a REST API.
 *
 * Recommended Learning Materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - Swagger UI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api/fantasyGame")
@Tag(name = "Character API", description = "Backend API endpoints for managing character state")
public class GameRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameRestController.class);

    private GameCharacter gameCharacter;

    // Constructor
    public GameRestController() {
        this.gameCharacter = new GameCharacter();
    }

    /**
     * Handles an HTTP PUT request to the endpoint /api/fantasyGame/takeDamage. This endpoint reduces the character's
     * health points (HP) by the specified damage amount. The character's HP is managed by the CharacterManager,
     * and the HP value will not go below 0.
     *
     * The goal of this endpoint is to simulate a character taking damage in a game scenario, updating their HP
     * accordingly, and returning the new HP value. This can be used to reflect damage from battles or other game events.
     *
     * @param damage The integer amount of damage to deal to the character. Must be non-negative.
     * @return The updated health points (HP) of the character after taking damage.
     */
    @PutMapping("/takeDamage")
    public int takeDamage(@RequestParam int damage) {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return gameCharacter.takeDamage(damage);
    }

    /**
     * Handles an HTTP PUT request to the endpoint /api/fantasyGame/consumePotion. This endpoint consumes a specified
     * potion (HP_POTION or MP_POTION) from the character's inventory, restoring either HP or MP based on the potion type.
     * The character's state is managed by the CharacterManager, and the potion is removed from the inventory if its count
     * reaches zero.
     *
     * The goal of this endpoint is to simulate a character using a potion to restore health or mana, updating the
     * character's state and returning the new HP or MP value. If an invalid potion name is provided, -1 is returned.
     *
     * @param potionName The name of the potion to consume (e.g., "HP_POTION" or "MP_POTION").
     * @return The updated HP if an HP_POTION is consumed, the updated MP if an MP_POTION is consumed, or -1 if the potion
     *         name is invalid or the potion is not in the inventory.
     */
    @PutMapping("/consumePotion")
    public int consumePotion(@RequestParam String potionName) {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return gameCharacter.consumePotion(Potion.valueOf(potionName));
    }

    /**
     * Handles an HTTP GET request to the endpoint /api/fantasyGame/characterState. This endpoint retrieves the current state
     * of the character, including their health points (HP), mana points (MP), and potion inventory. The character's state
     * is managed by the CharacterManager.
     *
     * The goal of this endpoint is to provide a snapshot of the character's current status for display in a user interface
     * or for monitoring purposes. The response includes HP, MP, and the inventory as a map of potion types to their quantities.
     *
     * @return A {@link Map} containing the character's HP, MP, and inventory (map of potion types to quantities).
     */
    @GetMapping("/characterState")
    public Map<String, Object> getCharacterState() {
        /**
         * TODO: Implement as part of Module 1 Exercise 2.
         * */
        return gameCharacter.getCharacterState();
    }
}
