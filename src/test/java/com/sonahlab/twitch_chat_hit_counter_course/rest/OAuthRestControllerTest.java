package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Module5")
public class OAuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Environment environment;

    @Autowired
    private TwitchAuthService twitchAuthService;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    void getAuthUrlTest() throws Exception {
        String output = mockMvc.perform(get("/oauth2/authorize"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        URI uri = URI.create(output);

        Assertions.assertEquals("https", uri.getScheme());
        Assertions.assertEquals("id.twitch.tv", uri.getHost());
        Assertions.assertEquals("/oauth2/authorize", uri.getPath());

        String query = uri.getQuery();
        Assertions.assertTrue(query.contains("response_type=code"));
        Assertions.assertTrue(query.contains("client_id=" + environment.getProperty("twitch-api.client-id")));
        Assertions.assertTrue(query.contains("redirect_uri=http://localhost:8080/oauth2/callback"));
        Assertions.assertTrue(query.contains("scope=chat:read"));
        Assertions.assertTrue(query.contains("state="));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackSuccessTest() throws Exception {
        mockMvc.perform(get("/oauth2/callback")
                        .param("code", "mockAuthorizationCode1")
                        .param("scope", "chat:read")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("mockAuthorizationCode1"))
                .andExpect(jsonPath("$.scope").value("chat:read"))
                .andExpect(jsonPath("$.state").value("mockState123"))
                .andExpect(jsonPath("$.error").doesNotExist())
                .andExpect(jsonPath("$.error_description").doesNotExist());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackErrorTest() throws Exception {
        mockMvc.perform(get("/oauth2/callback")
                        .param("error", "access_denied")
                        .param("error_description", "The+user+denied+you+access")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").doesNotExist())
                .andExpect(jsonPath("$.scope").doesNotExist())
                .andExpect(jsonPath("$.state").value("mockState123"))
                .andExpect(jsonPath("$.error").value("access_denied"))
                .andExpect(jsonPath("$.error_description").value("The+user+denied+you+access"));
    }
}
