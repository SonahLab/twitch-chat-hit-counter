package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChannelRedisService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils.USERNAME;

/**
 * Service responsible for interacting with the Twitch Chat Bot API Client.
 */
@Service
public class TwitchChatBotManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    private TwitchClient twitchClient;
    private TwitchChannelRedisService twitchChannelRedisService;

    // Constructor
    public TwitchChatBotManager(
            TwitchClient twitchClient,
            TwitchChannelRedisService twitchChannelRedisService) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.twitchClient = twitchClient;
        this.twitchChannelRedisService = twitchChannelRedisService;
    }

    @PostConstruct
    public void init() {
        /**
         * TODO: Implement as part of Module 5
         * */
        twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, this::handleChatMessage);

        // Initialize our ChatBot to connect to some default channels
        Set<String> channels = getJoinedChannels(USERNAME);
        for (String channel : channels) {
            joinChannel(channel);
        }
    }

    public void joinChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        twitchClient.getChat().joinChannel(channelName);
    }

    public boolean leaveChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return twitchClient.getChat().leaveChannel(channelName);
    }

    public Set<String> getJoinedChannels(String username) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return twitchChannelRedisService.getJoinedChannels(username);
    }

    private void handleChatMessage(ChannelMessageEvent event) {
        /**
         * TODO: Implement as part of Module 5
         * */
        LOGGER.info("Received channel message: {}", event.getMessage());
    }
}
