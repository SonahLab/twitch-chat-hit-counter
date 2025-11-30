package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.github.twitch4j.helix.domain.User;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChatRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchChatBotManager;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchHelixService;
import com.sonahlab.twitch_chat_hit_counter_course.utils.Granularity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * REST Controller for all of our service's backend endpoint required by our Frontend application.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Twitch Chat Hit Counter API", description = "Backend API endpoints needed to interact with our Frontend application")
public class TwitchRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchRestController.class);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    private TwitchChatRedisService twitchChatRedisService;
    private TwitchHelixService twitchHelixService;
    private TwitchChatBotManager twitchChatBotManager;

    // Constructor
    public TwitchRestController(
            TwitchChatRedisService twitchChatRedisService,
            TwitchHelixService twitchHelixService,
            TwitchChatBotManager twitchChatBotManager) {
        /**
         * TODO: Implement as part of Module 6
         * */
        this.twitchChatRedisService = twitchChatRedisService;
        this.twitchHelixService = twitchHelixService;
        this.twitchChatBotManager = twitchChatBotManager;
    }

    @GetMapping("/hitCounter")
    @Operation(summary = "Get chat counter of a streamer", description = "")
    public Map<String, Long> hitCounter(
            @RequestParam(name = "granularity") String granularity,
            @RequestParam(name = "channelName") String channelName,
            @RequestParam(name = "startTimeMillis") long startTimeMillis,
            @RequestParam(name = "endTimeMillis") long endTimeMillis
    ) {
        /**
         * TODO: Implement as part of Module 6
         * */
        Map<String, Long> hitCounter = new HashMap<>();

        LOGGER.info("Get chat counter of a streamer: {}", channelName);
        Granularity granularityEnum;
        if ("MINUTELY".equals(granularity)) {
            granularityEnum = Granularity.MINUTE;
        } else {
            throw new IllegalArgumentException("Unsupported granularity: " + granularity);
        }

        ZonedDateTime startDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(startTimeMillis), ZoneId.of("UTC"));
        ZonedDateTime endDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(endTimeMillis), ZoneId.of("UTC"));

        for (ZonedDateTime currentDateTime = startDateTime; currentDateTime.compareTo(endDateTime) <= 0; currentDateTime = currentDateTime.plusDays(1)) {
            int dateInt = Integer.parseInt(currentDateTime.format(DATE_FORMATTER));
            Map<String, Long> dailyHitCounter = twitchChatRedisService.getHitCounts(granularityEnum, channelName, dateInt);
            hitCounter.putAll(dailyHitCounter);
        }
        return hitCounter;
    }

    @PutMapping("/addChannel")
    public ResponseEntity addChannel(@RequestParam(name = "channelName") String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        LOGGER.info("Adding channel {} to Twitch", channelName);
        twitchChatBotManager.joinChannel(channelName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getChannels")
    public ResponseEntity<Set<String>> getChannels() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return ResponseEntity.ok(twitchChatBotManager.getJoinedChannels());
    }

    @DeleteMapping("/removeChannel")
    public ResponseEntity removeChannel(@RequestParam(name = "channelName") String channelName) {
        /**
         * TODO: Implement as part of Module 6
         * */
        LOGGER.info("Removing channel {}", channelName);
        twitchChatBotManager.leaveChannel(channelName);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getChannelsMetadata")
    public ResponseEntity<Map<String, User>> getChannelsMetadata() {
        /**
         * TODO: Implement as part of Module 6
         * */
        return ResponseEntity.ok(
                twitchHelixService.getChannelInfo(
                        twitchChatBotManager.getJoinedChannels().stream().toList()));
    }
}
