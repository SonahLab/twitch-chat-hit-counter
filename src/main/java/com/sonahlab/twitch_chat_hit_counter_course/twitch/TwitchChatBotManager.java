package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.TwitchChatEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChannelRedisService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.OptionalInt;
import java.util.Set;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils.USERNAME;


/**
 * Service responsible for interacting with the Twitch Chat Bot API Client.
 */
@Service
public class TwitchChatBotManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatBotManager.class);

    private TwitchClient twitchClient;
    private TwitchChatEventProducer twitchChatEventProducer;
    private TwitchChannelRedisService twitchChannelRedisService;

    // Constructor
    public TwitchChatBotManager(TwitchClient twitchClient,
                                TwitchChatEventProducer twitchChatEventProducer,
                                TwitchChannelRedisService twitchChannelRedisService) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.twitchClient = twitchClient;
        this.twitchChatEventProducer = twitchChatEventProducer;
        this.twitchChannelRedisService = twitchChannelRedisService;
    }

    @PostConstruct
    public void init() throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        twitchClient.getEventManager().onEvent(ChannelMessageEvent.class, this::handleChatMessage);

        initChannelsToJoin();
    }

    public void joinChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        twitchChannelRedisService.addChannel(USERNAME, channelName);
        twitchClient.getChat().joinChannel(channelName);
    }

    public boolean leaveChannel(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        twitchChannelRedisService.removeChannel(USERNAME, channelName);
        return twitchClient.getChat().leaveChannel(channelName);
    }

    public Set<String> getJoinedChannels() {
        /**
         * TODO: Implement as part of Module 5
         * */
        Set<String> channels = twitchChannelRedisService.getJoinedChannels(USERNAME);
        LOGGER.info("Joined channels: {}", channels);
        return channels;
    }

    private void handleChatMessage(ChannelMessageEvent event) {
        LOGGER.debug("Processing event: " + event);

        OptionalInt subscriberMonths = event.getMessageEvent().getSubscriberMonths();
        OptionalInt subscriptionTier = event.getMessageEvent().getSubscriptionTier();

        TwitchChatEvent twitchChatEvent = new TwitchChatEvent(
                event.getEventId(),
                event.getMessage(),
                event.getMessageEvent().getFiredAt().getTimeInMillis(),
                event.getUser().getId(),
                event.getUser().getName(),
                event.getChannel().getId(),
                event.getChannel().getName(),
                subscriberMonths.isPresent() ? subscriberMonths.getAsInt() : 0,
                subscriptionTier.isPresent() ? subscriptionTier.getAsInt() : 0);
        LOGGER.info("Processing TwitchChatEvent={}", twitchChatEvent);

        boolean published = twitchChatEventProducer.publish(twitchChatEvent.eventId(), twitchChatEvent);
        LOGGER.debug("Published={} eventId={} to kafka topic", published, twitchChatEvent.eventId());
    }

    private void initChannelsToJoin() {
        /**
         * TODO: Implement as part of Module 5
         * */
        Set<String> channels = twitchChannelRedisService.getJoinedChannels(USERNAME);

        LOGGER.info("Initial channels to join: {}", channels);
        for (String channelName : channels) {
            twitchClient.getChat().joinChannel(channelName);
        }
    }
}
