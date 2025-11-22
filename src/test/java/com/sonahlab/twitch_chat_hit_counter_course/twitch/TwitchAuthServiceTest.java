package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.sonahlab.twitch_chat_hit_counter_course.config.TwitchConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;

@SpringBootTest
public class TwitchAuthServiceTest {

    @Autowired
    private TwitchAuthService service;

    @Autowired
    private TwitchConfig config;

    @Test
    @Tag("Module5")
    public void getAuthUrlTest() {
        String authUrl = service.getAuthUrl("state1");
        URI uri = URI.create(authUrl);

        Assertions.assertEquals("https", uri.getScheme());
        Assertions.assertEquals("id.twitch.tv", uri.getHost());
        Assertions.assertEquals("/oauth2/authorize", uri.getPath());

        String query = uri.getQuery();
        Assertions.assertTrue(query.contains("response_type=code"));
        Assertions.assertTrue(query.contains("client_id=" + config.getTwitchApiClientId()));
        Assertions.assertTrue(query.contains("redirect_uri=http://localhost:8080/oauth2/callback"));
        Assertions.assertTrue(query.contains("scope=chat:read"));
        Assertions.assertTrue(query.contains("state=state1"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void createOAuthTokenSuccessTest() {

    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void createOAuthTokenFailTest() {

    }
}
