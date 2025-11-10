package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service responsible for interacting with the Twitch Chat Bot API Client.
 */
@Service
public class TwitchChatBotManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    // Constructor
    public TwitchChatBotManager() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    @PostConstruct
    public void init() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public void joinChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    public boolean leaveChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return false;
    }

    private void initChannelsToJoin() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }
}
