package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TwitchHelixService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TwitchHelixService.class);

    private TwitchClient twitchClient;

    // Constructor
    public TwitchHelixService(TwitchClient twitchClient) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.twitchClient = twitchClient;
    }

    // GET https://api.twitch.tv/helix/users?login={channelName}
    public Map<String, User> getChannelsMetadata(List<String> channelNames) {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, User> channelMap = new HashMap<>();

        UserList userList = twitchClient.getHelix().getUsers(null, null, channelNames).execute();
        List<User> users = userList.getUsers();
        for (User user : users) {
            channelMap.put(user.getDisplayName(), user);
        }
        return channelMap;
    }
}
