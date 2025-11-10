package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.helix.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwitchHelixService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TwitchHelixService.class);

    // Constructor
    public TwitchHelixService() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    // GET https://api.twitch.tv/helix/users?login={channelName}
    public User getChannelInfo(String channelName) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }
}
