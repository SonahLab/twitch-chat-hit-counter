package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("Module5")
public class TwitchConfigTest {

    @Autowired
    private TwitchConfig twitchConfig;

    @Test
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

        assertTrue(StringUtils.isNotBlank(twitchConfig.getTwitchApiClientId()));
        assertTrue(StringUtils.isNotBlank(twitchConfig.getTwitchApiClientSecret()));
        assertEquals(expectedClientId, twitchConfig.getTwitchApiClientId());
        assertEquals(expectedClientSecret, twitchConfig.getTwitchApiClientSecret());
    }
}
