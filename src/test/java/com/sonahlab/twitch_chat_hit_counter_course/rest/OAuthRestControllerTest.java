package com.sonahlab.twitch_chat_hit_counter_course.rest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Module5")
public class OAuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackSuccessTest() throws Exception {
        mockMvc.perform(get("/oauth2/callback")
                        .param("code", "mockAuthorizationCode1")
                        .param("scope", "chat:read")
                        .param("state", "mockState123"))
                .andExpect(status().isOk());
    }

    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackErrorTest() throws Exception {
        mockMvc.perform(get("/oauth2/callback")
                        .param("error", "access_denied")
                        .param("error_description", "The+user+denied+you+access")
                        .param("state", "mockState123"))
                .andExpect(status().isOk());
    }
}
