package com.sonahlab.twitch_chat_hit_counter_course.model;

import com.sonahlab.twitch_chat_hit_counter_course.utils.Potion;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Stat;

import java.util.Map;

/**
 * Data Model to represent a simple GameCharacter object to be used in Module 1, Exercise 2.
 *
 * This class will handle any state changes for the underlying HP, MP, and inventory fields.
 * */
public class GameCharacter {
    /**
     * TODO: Implement as part of Module 1 Exercise 2.
     *
     * Data Model requirements:
     * 1. Stat.HP (int): Character's health points (between 0 and 100)
     * 2. Stat.MP (int): Character's mana points (between 0 and 100)
     * 3. Inventory (Map<Potion, Integer>): Character's inventory of Potion -> quantity count
     * */
    public GameCharacter() {
        // TODO:
        // 1. Define the underlying fields however you like
        // 2. Set default values for HP (100), MP (100), Inventory ({HP_POTION: 5, MP_POTION: 5})
    }

    // =================================================================================================================
    // GETTERs and SETTERs
    // =================================================================================================================
    /**
     * In a game with many potential Stats, we will create a generic GETTER for all Stats.
     * Depending on the input parameter, we should return the correct Stat's value.
     * */
    public int getStat(Stat stat) {
        return -1;
    }

    /**
     * GETTER for the inventory map.
     * */
    public Map<Potion, Integer> getInventory() {
        return null;
    }

    /**
     * Setter for the HP Stat.
     *
     * Based on the requirement for this field you must check that a valid 'hp' value was input between 0 and 100.
     * Otherwise, do nothing.
     * */
    public void setHp(int hp) {
        return;
    }

    /**
     * Setter for the MP Stat.
     *
     * Based on the requirement for this field you must check that a valid 'mp' value was input between 0 and 100.
     * Otherwise, do nothing.
     * */
    public void setMp(int mp) {
        return;
    }

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
