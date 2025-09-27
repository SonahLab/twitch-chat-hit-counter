package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 1.
@Disabled
public class PokemonRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Tag("Module1")
    void testPokemonEndpoints() throws Exception {
        // 1. Get Ash's Pokémon (should only contain Pikachu)
        String jsonResponse = mockMvc.perform(get("/api/pokemon/getAshPokemon"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(jsonResponse);
        Assertions.assertEquals(2, json.size());

        Assertions.assertEquals("Pikachu", json.get(0).get("name").asText());
        Assertions.assertEquals("Lightning", json.get(0).get("type").asText());
        Assertions.assertEquals(5, json.get(0).get("level").asInt());
        Assertions.assertEquals(100, json.get(0).get("health").asInt());

        Assertions.assertEquals("Charizard", json.get(1).get("name").asText());
        Assertions.assertEquals("Fire", json.get(1).get("type").asText());
        Assertions.assertEquals(100, json.get(1).get("level").asInt());
        Assertions.assertEquals(9999, json.get(1).get("health").asInt());

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
        jsonResponse = mockMvc.perform(get("/api/pokemon/getAshPokemon"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn()
                .getResponse()
                .getContentAsString();
        json = mapper.readTree(jsonResponse);
        Assertions.assertEquals(1, json.size());

        Assertions.assertEquals("Charizard", json.get(0).get("name").asText());
        Assertions.assertEquals("Fire", json.get(0).get("type").asText());
        Assertions.assertEquals(100, json.get(0).get("level").asInt());
        Assertions.assertEquals(9999, json.get(0).get("health").asInt());
    }
}
