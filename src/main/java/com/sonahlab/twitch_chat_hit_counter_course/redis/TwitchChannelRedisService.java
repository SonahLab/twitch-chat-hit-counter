package com.sonahlab.twitch_chat_hit_counter_course.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public class TwitchChannelRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChannelRedisService.class);

    public TwitchChannelRedisService(RedisTemplate<String, String> redisTemplate) {
        /**
         * TODO: Implement as part of Module 5
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
