package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.netflix.hystrix.HystrixCommand;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.TwitchChatEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChatRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.twitch4j.TwitchClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.Set;

@Service
public class TwitchChatBotManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    private TwitchClient twitchClient;
    private TwitchChatEventProducer twitchChatEventProducer;
    private TwitchChatRedisService twitchChatRedisService;

    public TwitchChatBotManager(TwitchClient twitchClient,
                                TwitchChatEventProducer twitchChatEventProducer,
                                TwitchChatRedisService twitchChatRedisService) {
        this.twitchClient = twitchClient;
        this.twitchChatEventProducer = twitchChatEventProducer;
        this.twitchChatRedisService = twitchChatRedisService;

        twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, event -> {
            handleMessage(event);
        });

        initChannelsToJoin();
    }

    private void handleMessage(ChannelMessageEvent event) {
        LOGGER.info("Processing event: " + event);
        OptionalInt subscriberMonths = event.getMessageEvent().getSubscriberMonths();
        OptionalInt subscriptionTier = event.getMessageEvent().getSubscriptionTier();
        TwitchChatEvent chatEvent = new TwitchChatEvent(
                event.getEventId(),
                event.getMessage(),
                new TwitchChatEvent.MessageContext(
                        event.getMessageEvent().getChannelId(),
                        event.getMessageEvent().getChannelName().get(),
                        event.getMessageEvent().getFiredAt().getTimeInMillis(),
                        subscriberMonths.isPresent() ? subscriberMonths.getAsInt() : 0,
                        subscriptionTier.isPresent() ? subscriptionTier.getAsInt() : 0
                ),
                new TwitchChatEvent.User(
                        event.getUser().getId(),
                        event.getUser().getName()
                ));
        twitchChatEventProducer.publish(event.getEventId(), chatEvent);
    }

    private void initChannelsToJoin() {
        Set<String> channels = twitchChatRedisService.getLiveChannels();
        for (String channelName : channels) {
            twitchClient.getChat().joinChannel(channelName);
        }
    }

    public void joinChannel(String channelName) {
        twitchClient.getChat().joinChannel(channelName);
        LOGGER.info("Bot has joined channel: " + channelName);
    }

    public boolean leaveChannel(String channelName) {
        boolean result = twitchClient.getChat().leaveChannel(channelName);
        twitchChatRedisService.removeChannel(channelName);
        LOGGER.info("Bot has left channel: " + channelName);
        return result;
    }

    // GET https://api.twitch.tv/helix/users?login={channelName}
    public User getChannelInfo(String channelName) {
        try {
            HystrixCommand<UserList> userInfo = twitchClient.getHelix().getUsers(null, null, Arrays.asList(channelName));
            List<User> userList = userInfo.execute().getUsers();
            LOGGER.info("Found User: {}", userList);
            if (!userList.isEmpty()) {
                return userList.get(0);
            }
        } catch (Exception ex) {
            LOGGER.error("Error fetching channel info for: {}", channelName, ex);
        }
        return null;
    }
}
