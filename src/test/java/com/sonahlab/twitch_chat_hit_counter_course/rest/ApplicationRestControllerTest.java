package com.sonahlab.twitch_chat_hit_counter_course.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// TODO: delete @Disabled once you are ready to test
@Disabled
public class ApplicationRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Tag("Module1")
    void testSayHello() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Alice."));
    }

    @Test
    @Tag("Module1")
    public void testSayHelloEmptyName() throws Exception {
        mockMvc.perform(get("/api/hello")
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Mysterious Individual."));
    }

    @Test
    @Tag("Module1")
    void testPokemonEndpoints() throws Exception {
        // 1. Get Ash's Pokémon (should only contain Pikachu)
        mockMvc.perform(get("/api/pokemon/getAshPokemon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pikachu"))
                .andExpect(jsonPath("$[0].type").value("Lightning"))
                .andExpect(jsonPath("$[0].level").value(5))
                .andExpect(jsonPath("$[0].health").value(100));

        // 2. Pikachu takes 70 damage
        mockMvc.perform(MockMvcRequestBuilders.put("/api/pokemon/takeDamage")
                        .param("pokemonName", "Pikachu")
                        .param("damage", "70"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.health").value(30));

        // 3. Ash releases Pikachu
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/pokemon/releasePokemon")
                        .param("pokemonName", "Pikachu"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        // 4. Get Ash's Pokémon again (empty list expected)
        mockMvc.perform(get("/api/pokemon/getAshPokemon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
