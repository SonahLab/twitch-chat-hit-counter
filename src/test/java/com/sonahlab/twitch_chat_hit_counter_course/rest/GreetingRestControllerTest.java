package com.sonahlab.twitch_chat_hit_counter_course.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingRestController.class)
@Tag("Module1")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 1.
@Disabled
public class GreetingRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSayHello() throws Exception {
        mockMvc.perform(get("/api/greeting/hello")
                        .param("name", "Alice"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Alice!"));
    }

    @Test
    public void testSayHelloEmptyName() throws Exception {
        mockMvc.perform(get("/api/greeting/hello")
                        .param("name", ""))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Mysterious Individual!"));
    }
}
