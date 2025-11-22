package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Module5")
public class OAuthRestControllerTest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TwitchConfig config;

    @Test
    @Tag("Module5")
    void getAuthUrlTest() throws Exception {
        String output = mockMvc.perform(get("/oauth2/authorize"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        URI uri = URI.create(output);

        assertEquals("https", uri.getScheme());
        assertEquals("id.twitch.tv", uri.getHost());
        assertEquals("/oauth2/authorize", uri.getPath());

        String query = uri.getQuery();
        Assertions.assertTrue(query.contains("response_type=code"));
        Assertions.assertTrue(query.contains("client_id=" + config.getTwitchApiClientId()));
        Assertions.assertTrue(query.contains("redirect_uri=http://localhost:8080/oauth2/callback"));
        Assertions.assertTrue(query.contains("scope=chat:read"));
        Assertions.assertTrue(query.contains("state="));
    }

    @Test
    void handleCallback_authorize_successTest() throws Exception {
        String response = mockMvc.perform(get("/oauth2/callback")
                        .param("code", "mockAuthorizationCode1")
                        .param("scope", "chat:read")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorize.code").value("mockAuthorizationCode1"))
                .andExpect(jsonPath("$.authorize.scope").value("chat:read"))
                .andExpect(jsonPath("$.authorize.state").value("mockState123"))
                .andExpect(jsonPath("$.authorize.error").doesNotExist())
                .andExpect(jsonPath("$.authorize.error_description").doesNotExist())
                .andReturn().getResponse().getContentAsString();

        Map<String, Map<String, Object>> result = OBJECT_MAPPER.readValue(response, Map.class);
        assertTrue(result.containsKey("authorize"));
        assertEquals("mockAuthorizationCode1", result.get("authorize").get("code"));
        assertEquals("chat:read", result.get("authorize").get("scope"));
        assertEquals("mockState123", result.get("authorize").get("state"));
        assertNull(result.get("authorize").get("error"));
        assertNull(result.get("authorize").get("error_description"));
    }

    @Test
    void handleCallback_authorize_errorTest() throws Exception {
        String response = mockMvc.perform(get("/oauth2/callback")
                        .param("error", "access_denied")
                        .param("error_description", "The+user+denied+you+access")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authorize.code").doesNotExist())
                .andExpect(jsonPath("$.authorize.scope").doesNotExist())
                .andExpect(jsonPath("$.authorize.state").value("mockState123"))
                .andExpect(jsonPath("$.authorize.error").value("access_denied"))
                .andExpect(jsonPath("$.authorize.error_description").value("The+user+denied+you+access"))
                .andReturn().getResponse().getContentAsString();

        Map<String, Map<String, Object>> result = OBJECT_MAPPER.readValue(response, Map.class);
        assertTrue(result.containsKey("authorize"));
        assertNull(result.get("authorize").get("code"));
        assertNull(result.get("authorize").get("scope"));
        assertEquals("mockState123", result.get("authorize").get("state"));
        assertEquals("access_denied", result.get("authorize").get("error"));
        assertEquals("The+user+denied+you+access", result.get("authorize").get("error_description"));
    }
}
