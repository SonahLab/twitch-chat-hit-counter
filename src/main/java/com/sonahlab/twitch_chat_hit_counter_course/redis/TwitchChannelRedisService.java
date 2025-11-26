package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TwitchChannelRedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChannelRedisService.class);

    private RedisDao redisDao;

    public TwitchChannelRedisService(@Qualifier("chatBotChannelsRedisDao") RedisDao redisDao) {
        /**
         * TODO: Implement as part of Module 6
         * */
        this.redisDao = redisDao;
    }

    /**
     * Fetches the list of twitch channel names that we're listening to twitch chat logs on.
     * */
    public Set<String> getJoinedChannels(String username) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return redisDao.getSetMembers(getKey(username));
    }

    public Long addChannels(String username, String... channelNames) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return redisDao.setAdd(getKey(username), channelNames);
    }

    public Long removeChannels(String username, String... channelNames) {
        /**
         * TODO: Implement as part of Module 6
         * */
        return redisDao.setRemove(getKey(username), channelNames);
    }

    private String getKey(String username) {
        return "channels#" + username;
    }
}
