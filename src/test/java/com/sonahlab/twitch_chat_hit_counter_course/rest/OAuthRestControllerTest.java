package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.google.common.base.Splitter;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Tag("Module5")
public class OAuthRestControllerTest {

    private final OAuth2Credential MOCK_CREDENTIAL = new OAuth2Credential(
            "twitch",
            "dummyAccessToken",
            "dummyRefreshToken",
            null,
            null,
            14124,
            null); // Setting scopes to null because Twitch4J has a bug where we need to manually override the OAuth2Credential object's scope field

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TwitchConfig config;

    @Autowired
    private TwitchAuthService twitchAuthService;

    @MockitoSpyBean
    private TwitchIdentityProvider twitchIdentityProvider;

    private static ObjectMapper MAPPER = new ObjectMapper();

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
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
        Map<String, String> queryParams = Splitter.on('&')
                .withKeyValueSeparator('=')
                .split(query);

        assertEquals("code", queryParams.get("response_type"));
        assertEquals(config.getTwitchApiClientId(), queryParams.get("client_id"));
        assertEquals("http://localhost:8080/oauth2/callback", queryParams.get("redirect_uri"));
        List<String> scopes = Arrays.stream(queryParams.get("scope").split("\\+")).toList();
        Assertions.assertTrue(scopes.contains("chat:read"));
        Assertions.assertTrue(scopes.contains("chat:edit"));
        Assertions.assertDoesNotThrow(() -> UUID.fromString(queryParams.get("state")));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackSuccessTest() throws Exception {
        // guard rail so test doesn't fail when you implement the actual getCredentialByCode() call later
        doReturn(MOCK_CREDENTIAL).when(twitchIdentityProvider).getCredentialByCode(eq("mockAuthorizationCode1"));

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
        doReturn(MOCK_CREDENTIAL).when(twitchIdentityProvider).getCredentialByCode(eq("mockAuthorizationCode1"));

        String response = mockMvc.perform(get("/oauth2/callback")
                        .param("code", "mockAuthorizationCode1")
                        .param("scope", "chat:read")
                        .param("state", "mockState123"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode json = MAPPER.readTree(response);
        JsonNode tokenJson = json.get("token");
        assertEquals("twitch", tokenJson.get("identityProvider").asText());
        assertEquals("dummyAccessToken", tokenJson.get("accessToken").asText());
        assertEquals("dummyRefreshToken", tokenJson.get("refreshToken").asText());
        assertEquals(14124, tokenJson.get("expiresIn").asInt());
        assertTrue(tokenJson.get("scopes").isArray());
        ArrayNode scopes = (ArrayNode) tokenJson.get("scopes");
        List<String> scopeList = StreamSupport.stream(scopes.spliterator(), false)
                .map(JsonNode::asText)
                .toList();
        assertThat(scopeList)
                .containsExactlyInAnyOrder("chat:read", "chat:edit");
        assertTrue(tokenJson.get("scopes").isArray());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    void handleCallbackErrorTest() throws Exception {
        // guard rail so test doesn't fail when you implement the actual getCredentialByCode() call later
        doReturn(MOCK_CREDENTIAL).when(twitchIdentityProvider).getCredentialByCode(eq("mockAuthorizationCode1"));

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
