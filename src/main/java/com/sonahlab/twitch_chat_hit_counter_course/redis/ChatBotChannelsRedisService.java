package com.sonahlab.twitch_chat_hit_counter_course.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ChatBotChannelsRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatBotChannelsRedisService.class);

    public ChatBotChannelsRedisService() {
        /**
         * TODO: Implement as part of Module 6
         * */
    }

    /**
     * Fetches the list of twitch channel names that we're listening to twitch chat logs on.
     * */
    public Set<String> getJoinedChannels(String username) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    public Long addChannels(String username, String... channelNames) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    public Long removeChannels(String username, String... channelNames) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }
}
