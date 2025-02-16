package com.sonahlab.twitch_chat_hit_counter_course.redis;

import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TwitchChatRedisService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatRedisService.class);
    private static final String CHANNELS_KEY = "channels";

    private RedisDao dedupeRedisDao;
    private RedisDao redisDao;

    public TwitchChatRedisService(
            @Qualifier("twitchChatEventDedupeRedisDao") RedisDao dedupeRedisDao,
            @Qualifier("twitchChatRedisDao") RedisDao redisDao) {
        this.dedupeRedisDao = dedupeRedisDao;
        this.redisDao = redisDao;
    }

    public void incrHitCounter(String key) {
        redisDao.increment(key);
    }

    /**
     * Fetches the liste of twitch channel names that we're listening to twitch chat logs on.
     * */
    public Set<String> getLiveChannels() {
        Set<String> channelNames = redisDao.setMembers(CHANNELS_KEY);
        for (String channelName : channelNames) {
            LOGGER.info("We are listening to {}", channelName);
        }
        return channelNames;
    }

    public Long addChannel(String channelName) {
        Long addedChannel = redisDao.setAdd(CHANNELS_KEY, channelName);
        LOGGER.info("ChannelName: {} added to Redis 'channels' key, result was {}", channelName, addedChannel);
        return addedChannel;
    }

    public Long removeChannel(String channelName) {
        Long removedChannel = redisDao.setRemove(CHANNELS_KEY, channelName);
        LOGGER.info("ChannelName: {} removed from Redis 'channels' key, result was {}", channelName, removedChannel);
        return removedChannel;
    }
}
