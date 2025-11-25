package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.google.common.base.Splitter;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TwitchAuthServiceTest {

    @Autowired
    private TwitchAuthService service;

    @Autowired
    private TwitchConfig config;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void getAuthUrlTest() {
        String authUrl = service.getAuthUrl();
        URI uri = URI.create(authUrl);

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
}
