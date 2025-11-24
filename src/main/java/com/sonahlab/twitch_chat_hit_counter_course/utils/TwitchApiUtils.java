package com.sonahlab.twitch_chat_hit_counter_course.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.auth.domain.TwitchScopes;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchAuthService;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchClientManager;

import java.util.List;

public class TwitchApiUtils {

    public static final String USERNAME = "sonahlab";
    public static final List<TwitchScopes> CHAT_BOT_SCOPES = List.of(
            TwitchScopes.CHAT_READ,
            TwitchScopes.CHAT_EDIT);

    public static void validateAndRefreshToken(
            TwitchAuthService twitchAuthService,
            TwitchClientManager twitchClientManager) throws JsonProcessingException {
        if (!twitchAuthService.validateOAuthToken()) {
            twitchAuthService.refreshOAuthToken();

            TwitchClient twitchClient = twitchClientManager.getTwitchClient();
            twitchClient.getChat().reconnect();
        }
    }
}
