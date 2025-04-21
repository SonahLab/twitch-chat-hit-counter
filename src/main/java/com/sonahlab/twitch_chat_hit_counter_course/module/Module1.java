package com.sonahlab.twitch_chat_hit_counter_course.module;

import com.sonahlab.twitch_chat_hit_counter_course.model.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Module1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Module1.class);


    private static Map<String, Pokemon> ASH_POKEMON = new HashMap<>() {{
        put("Pikachu", new Pokemon("Pikachu", "Lightning", 5, 100));
    }};

    /**
     * Module 1 Playground: Implement as part of Module 1, Task 2.
     * */
    public Pokemon takeDamage(String pokemonName, int damage) {
        if (!ASH_POKEMON.containsKey(pokemonName)) {
            return null;
        }

        Pokemon pokemon = ASH_POKEMON.get(pokemonName);
        int originalHealth = pokemon.getHealth();
        int newHealth = Math.max(0, originalHealth - damage);
        pokemon.setHealth(newHealth);
        LOGGER.info("{}'s took {} damage, his health went from {} to {}.", pokemonName, damage, originalHealth, newHealth);
        return pokemon;
    }

    public boolean releasePokemon(String pokemonName) {
        if (!ASH_POKEMON.containsKey(pokemonName)) {
            return false;
        }

        ASH_POKEMON.remove(pokemonName);
        return true;
    }

    public List<Pokemon> getAshPokemon() {
        return ASH_POKEMON.values().stream().toList();
    }
}
