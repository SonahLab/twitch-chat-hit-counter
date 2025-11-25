package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("Module5")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5
@Disabled
public class TwitchConfigTest {

    @Autowired
    private TwitchConfig twitchConfig;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5
    @Disabled
    public void testTwitchClientKeys() {
        Properties props = new Properties();
        try (var stream = getClass().getClassLoader()
                .getResourceAsStream("twitch-key.properties")) {
            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String expectedClientId = props.getProperty("twitch-api.client-id");
        String expectedClientSecret = props.getProperty("twitch-api.client-secret");
        String expectedRedirectUrl = props.getProperty("twitch-api.redirect-url");

        assertTrue(StringUtils.isNotBlank(twitchConfig.getTwitchApiClientId()));
        assertTrue(StringUtils.isNotBlank(twitchConfig.getTwitchApiClientSecret()));
        assertEquals(expectedClientId, twitchConfig.getTwitchApiClientId());
        assertEquals(expectedClientSecret, twitchConfig.getTwitchApiClientSecret());
        assertEquals(expectedRedirectUrl, twitchConfig.getTwitchApiRedirectUrl());
        assertEquals("http://localhost:8080/oauth2/callback", twitchConfig.getTwitchApiRedirectUrl());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5
    @Disabled
    public void testTwitchIdentityProvider() {
        // Twitch4J's TwitchIdentityProvider has the internal fields as 'protected' variables so it's not easy to test it's values
        // This just tests that the @Bean is created successfully.
        // You should set a breakpoint here and run this test through the debugger to manually validate your:
        // client-id, client-secret, and redirect-url.
        assertNotNull(twitchConfig.twitchIdentityProvider());
    }
}
