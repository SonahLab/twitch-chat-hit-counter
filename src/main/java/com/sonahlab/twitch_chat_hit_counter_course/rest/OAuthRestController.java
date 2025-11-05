package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchChatBotManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class OAuthRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    @GetMapping("/oauth2/callback")
    public String handleCallback(
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "error_description", required = false) String errorDescription) {
        LOGGER.info("HELLO WORLD");
        if (error != null) {
            LOGGER.error("OAuth error: " + error + " - " + errorDescription);
            return "Error: " + error + " - " + errorDescription;
        }
        LOGGER.info("Authorization code: " + code);
        return "Authorization code: " + code + "<br>Please copy this code and use it to exchange for a token.";
    }
}
