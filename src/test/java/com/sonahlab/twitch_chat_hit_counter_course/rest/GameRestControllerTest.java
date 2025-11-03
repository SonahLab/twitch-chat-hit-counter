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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 1.
@Disabled
@Tag("Module1")
public class GameRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGameEndpoints() throws Exception {
        // 1. Get default character state
        // HP=100, MP=100, INVENTORY={HP_POTION=5, MP_POTION=5}
        String jsonResponse = mockMvc.perform(get("/api/fantasyGame/characterState"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(jsonResponse);

        Assertions.assertEquals(100, json.get("HP").asInt());
        Assertions.assertEquals(100, json.get("MP").asInt());
        Assertions.assertEquals(5, json.get("INVENTORY").get("HP_POTION").asInt());
        Assertions.assertEquals(5, json.get("INVENTORY").get("MP_POTION").asInt());

        // 2. Character takes 70 damage
        // 100 (current HP) - 70 (damage) = 30 HP
        mockMvc.perform(MockMvcRequestBuilders.put("/api/fantasyGame/takeDamage")
                        .param("damage", "70"))
                .andExpect(status().isOk())
                .andExpect(content().string("30"));

        // 3. Character drinks an HP potion
        // 30 (current HP) + 50 (HP potion) = 80 HP
        mockMvc.perform(MockMvcRequestBuilders.put("/api/fantasyGame/consumePotion")
                        .param("potionName", "HP_POTION"))
                .andExpect(status().isOk())
                .andExpect(content().string("80"));

        // 4. Get updated character state
        // HP=80, MP=100, INVENTORY={HP_POTION=4, MP_POTION=5}
        jsonResponse = mockMvc.perform(get("/api/fantasyGame/characterState"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        json = mapper.readTree(jsonResponse);

        Assertions.assertEquals(80, json.get("HP").asInt());
        Assertions.assertEquals(100, json.get("MP").asInt());
        Assertions.assertEquals(4, json.get("INVENTORY").get("HP_POTION").asInt());
        Assertions.assertEquals(5, json.get("INVENTORY").get("MP_POTION").asInt());
    }
}
