package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
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
import org.testcontainers.shaded.com.google.common.base.Splitter;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Module5")
public class OAuthRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TwitchConfig config;

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
        Map<String, String> queryParams = Splitter.on('&')
                .withKeyValueSeparator('=')
                .split(query);

        Assertions.assertEquals("code", queryParams.get("response_type"));
        Assertions.assertEquals(config.getTwitchApiClientId(), queryParams.get("client_id"));
        Assertions.assertEquals("http://localhost:8080/oauth2/callback", queryParams.get("redirect_uri"));
        List<String> scopes = Arrays.stream(queryParams.get("scope").split("\\+")).toList();
        Assertions.assertTrue(scopes.contains("chat:read"));
        Assertions.assertTrue(scopes.contains("chat:edit"));
        Assertions.assertDoesNotThrow(() -> UUID.fromString(queryParams.get("state")));
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
                .andExpect(jsonPath("$.authorization").exists())
                .andExpect(jsonPath("$.authorization.code").value("mockAuthorizationCode1"))
                .andExpect(jsonPath("$.authorization.scope").value("chat:read"))
                .andExpect(jsonPath("$.authorization.state").value("mockState123"))
                .andExpect(jsonPath("$.authorization.error").doesNotExist())
                .andExpect(jsonPath("$.authorization.error_description").doesNotExist());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackSuccessTest_token() throws Exception {
        mockMvc.perform(get("/oauth2/callback")
                        .param("code", "mockAuthorizationCode1")
                        .param("scope", "chat:read")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.token.accessToken").value("mockAccessToken123"))
                .andExpect(jsonPath("$.token.expiresIn").value(3600))
                .andExpect(jsonPath("$.token.refreshToken").value("mockRefreshToken"))
                .andExpect(jsonPath("$.token.scopes").value(("chat:read")));
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
                .andExpect(jsonPath("$.authorization").exists())
                .andExpect(jsonPath("$.authorization.code").doesNotExist())
                .andExpect(jsonPath("$.authorization.scope").doesNotExist())
                .andExpect(jsonPath("$.authorization.state").value("mockState123"))
                .andExpect(jsonPath("$.authorization.error").value("access_denied"))
                .andExpect(jsonPath("$.authorization.error_description").value("The+user+denied+you+access"));
    }
}
