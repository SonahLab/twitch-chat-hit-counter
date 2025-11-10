package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Tag("Module5")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5
@Disabled
public class TwitchConfigTest {

    @Autowired
    private String twitchClientId;

    @Autowired
    private String twitchClientSecret;

    @Test
    public void testTwitchClientKeys() {
        assertTrue(StringUtils.isNotBlank(twitchClientId));
        assertTrue(StringUtils.isNotBlank(twitchClientSecret));
    }
}
