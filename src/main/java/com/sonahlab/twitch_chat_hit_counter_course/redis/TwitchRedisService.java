package com.sonahlab.twitch_chat_hit_counter_course.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class TwitchRedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchRedisService.class);

    public Long incrementMinuteHitCounter(String channelName, long timestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    public Map<String, String> getHitCounter(String channelName, long timestampMs) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
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

    public Long addChannel(String username, String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }

    public Long removeChannel(String username, String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return null;
    }
}
