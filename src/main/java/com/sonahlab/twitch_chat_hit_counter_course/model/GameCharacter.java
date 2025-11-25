package com.sonahlab.twitch_chat_hit_counter_course.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Data Model to represent a simple GameCharacter object to be used in Module 1, Exercise 2.
 *
 * This class will handle any state changes for the underlying HP, MP, and inventory fields.
 * */
public class GameCharacter {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameCharacter.class);

    /**
     * TODO: Implement as part of Module 1 Exercise 2.
     *
     * Data Model requirements:
     * 1. Stat.HP (int): Character's health points (between 0 and 100)
     * 2. Stat.MP (int): Character's mana points (between 0 and 100)
     * 3. Inventory (Map<Potion, Integer>): Character's inventory of Potion -> quantity count
     * */
    // Constructor
    public GameCharacter() {
        // TODO:
        // 1. Define the underlying fields however you like
        // 2. Set default values for HP (100), MP (100), Inventory ({HP_POTION: 5, MP_POTION: 5})
    }

    // =================================================================================================================
    // GETTERs and SETTERs
    // =================================================================================================================
    /**
     * Returns the current value of the specified {@link Stat}.
     *
     * This generic getter allows retrieval of any stat (e.g., HP, MP, etc.) by passing
     * the corresponding Stat enum value. If the stat is not recognized or not yet initialized,
     * a default value of -1 is returned.
     *
     * @param stat the {@link Stat} enum constant identifying which stat to retrieve
     * @return the current integer value of the requested stat, or -1 if unavailable or invalid
     */
    public int getStat(Stat stat) {
        return -1;
    }

    /**
     * Returns an map of the player's current potion inventory.
     *
     * The map uses {@link Potion} as the key and the quantity of that potion as the value.
     * Returns an empty map if no potions are in inventory.
     *
     * @return a Map mapping each {@link Potion} type to its count in inventory
     */
    public Map<Potion, Integer> getInventory() {
        return null;
    }

    /**
     * Sets the player's HP (Hit Points) to the specified value, with bounds checking.
     *
     * The HP value must be between 0 and 100 (inclusive). If the input
     * is outside this range, the operation is ignored and the current HP remains unchanged.
     *
     * @param hp the new HP value to set (must be in range [0, 100])
     */
    public void setHp(int hp) {}

    /**
     * Sets the player's MP (Mana Points) to the specified value, with bounds checking.
     *
     * The MP value must be between 0 and 100 (inclusive). If the input
     * is outside this range, the operation is ignored and the current MP remains unchanged.
     *
     * @param mp the new MP value to set (must be in range [0, 100])
     */
    public void setMp(int mp) {}

    // =================================================================================================================
    // CHARACTER STATE METHODS
    // =================================================================================================================
    /**
     * Applies damage to the character and returns the new HP value.
     *
     * Remember:
     *   - the HP value should never go below 0.
     *
     * @param damage the amount of damage to inflict (must be >= 0)
     * @return the character's HP after taking the damage (0 to 100)
     * */
    public int takeDamage(int damage) {
        return -1;
    }

    /**
     * Consumes a potion from the character's inventory and applies its effect.
     * Take a look at Potion.java enum class to see how I've set it up.
     *
     * Rules:
     *   - The GameCharacter object must have the Potion currently in their inventory.
     *   - Potion.HP_POTION should increment the character HP by 50.
     *   - Potion.MP_POTION should increment the character MP by 50.
     *   - Decrement the quantity of the consumed Potion by 1 in the GameCharacter.inventory
     *   - Remove the Potion from the character inventory if the quantity hits 0.
     *
     * Remember:
     *   - the HP/MP value should never go above 100.
     *
     * @param potion the Potion to consume
     * @return the new value of the affected stat (HP or MP), or -1 for any errors.
     * */
    public int consumePotion(Potion potion) {
        return -1;
    }

    /**
     * Returns all the relevant information about the GameCharacter's current snapshot.
     *
     * Requirements: output should be a map with 3 key pieces of information
     * 1. "HP": currentHpValue
     * 2. "MP": currentMpValue
     * 3. "INVENTORY": currentInventoryMap
     *
     * @return a map containing the current HP, MP, and a copy of the inventory
     * */
    public Map<String, Object> getCharacterState() {
        return null;
    }
}
