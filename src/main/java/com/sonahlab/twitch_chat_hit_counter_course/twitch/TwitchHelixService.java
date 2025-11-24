package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.domain.User;
import com.github.twitch4j.helix.domain.UserList;
import com.netflix.hystrix.HystrixCommand;
import com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TwitchHelixService {

    public static final Logger LOGGER = LoggerFactory.getLogger(TwitchHelixService.class);

    private TwitchClientManager twitchClientManager;
    private TwitchClient twitchClient;
    private TwitchAuthService twitchAuthService;

    // Constructor
    public TwitchHelixService(TwitchClientManager twitchClientManager,
                              TwitchAuthService twitchAuthService) {
        /**
         * TODO: Implement as part of Module 5
         * */
        this.twitchClientManager = twitchClientManager;
        this.twitchClient = twitchClientManager.getTwitchClient();
        this.twitchAuthService = twitchAuthService;
    }

    // GET https://api.twitch.tv/helix/users?login={channelName}
    public Map<String, User> getChannelInfo(List<String> channelNames) {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, User> channelMap = new HashMap<>();

        try {
            TwitchApiUtils.validateAndRefreshToken(twitchAuthService, twitchClientManager);

            HystrixCommand<UserList> userInfo = twitchClient.getHelix().getUsers(null, null, channelNames);
            List<User> userList = userInfo.execute().getUsers();
            for (User user : userList) {
                channelMap.put(user.getDisplayName(), user);
            }
            return channelMap;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
