package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.twitch4j.helix.domain.User;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.GreetingEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.GreetingRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.TwitchChatRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import com.sonahlab.twitch_chat_hit_counter_course.twitch.TwitchChatBotManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * REST Controller for all of our service's backend endpoints. These endpoints will be accessed by
 * SwaggerUI and the React frontend app.
 *
 * Recommended Learning materials to learn more about REST:
 * - RESTful service in Spring Boot (https://spring.io/guides/gs/rest-service)
 * - SwaggerUI in Spring Boot (https://springdoc.org/)
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Twitch Chat Hit Counter API", description = "Backend API endpoints controller")
public class ApplicationRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationRestController.class);

    private ObjectMapper objectMapper;
    private GreetingEventProducer greetingEventProducer;
    private GreetingSqlService greetingSqlService;
    private GreetingRedisService greetingRedisService;
    private RedisDao redisDAO;
    private TwitchChatRedisService twitchChatRedisService;
    private TwitchChatBotManager twitchChatBotManager;

    public ApplicationRestController(
            ObjectMapper objectMapper,
            GreetingEventProducer greetingEventProducer,
            GreetingSqlService greetingSqlService,
            GreetingRedisService greetingRedisService,
            @Qualifier("flinkTwitchChatRedisDao") RedisDao redisDAO,
            TwitchChatRedisService twitchChatRedisService,
            TwitchChatBotManager twitchChatBotManager) {
        this.objectMapper = objectMapper;
        this.greetingEventProducer = greetingEventProducer;
        this.greetingSqlService = greetingSqlService;
        this.greetingRedisService = greetingRedisService;
        this.redisDAO = redisDAO;
        this.twitchChatRedisService = twitchChatRedisService;
        this.twitchChatBotManager = twitchChatBotManager;
    }

    /**
    * HTTP GET request handler for endpoint /api/hello. Endpoint that will receive an input name and
    * return a custom greeting.
    *
    * @param name The name of the person to greet
    * @return String greeting message
    */
    @GetMapping("/hello")
    @Operation(summary = "Say Hello", description = "Returns a greeting message")
    public String sayHello(@RequestParam String name) {
        /**
         * TODO: Implement as part of Module 1
         * */
        return null;
    }

    /**
    * HTTP POST request handler for endpoint /api/publishGreetingEvent. Endpoint that will:
    * receive some input parameters, create a data model POJO GreetingEvent, produce the event to
    * a kafka topic.
    *
    * @param sender the name of the sender
    * @param receiver the name of the receiver
    * @param message the greeting message
    * @return Boolean status of kafka producer send() operation
    */
    @PostMapping("/publishGreetingEvent")
    @Operation(summary = "Publish Kafka Event", description = "Publish a GreetingEvent")
    public Boolean produceKafkaGreetingEvent(@RequestParam String sender, @RequestParam String receiver, @RequestParam String message) {
        /**
         * TODO: Implement as part of Module 2
         * */
        String eventId = UUID.randomUUID().toString();
        GreetingEvent event = new GreetingEvent(eventId, sender, receiver, message);
        boolean isPublished = greetingEventProducer.publish(eventId, event);
        return isPublished;
    }

    /**
     * TODO: Implement more API endpoints below
     * */
    @GetMapping("/queryGreetingEventsFromSQL")
    @Operation(summary = "Query all events from dev_db.greeting_events SQL table", description = "Returns a List<GreetingEvent>")
    public List<GreetingEvent> getSqlGreetingEvents() {
        return greetingSqlService.queryAllEvents();
    }

    @GetMapping("/queryGreetingFeedFromRedis")
    @Operation(summary = "Query all events from redis DB=0 for a user's Greeting feed", description = "Returns a List<GreetingEvent> of all incoming greetings from another sender")
    public List<GreetingEvent> getRedisGreetingFeed(String name) {
        return greetingRedisService.getGreetingFeed(name);
    }

    @GetMapping("/hitCounter")
    @Operation(summary = "Get chat counter of a streamer", description = "")
    public Map<String, Long> hitCounter(@RequestParam String channelName) {
        Map<String, Long> result = redisDAO.keys(channelName + "#*");
        LOGGER.info("RESULT: " + result);
        return result;
    }

    @GetMapping("/hitCounterMinute")
    @Operation(summary = "Get chat counter of a streamer", description = "")
    public Long hitCounter(@RequestParam String channelName, @RequestParam Long minuteTs) {
        Long result = redisDAO.get(channelName + "#" + minuteTs);
        LOGGER.info("RESULT: " + result);
        return result;
    }

    @GetMapping("/addChannel")
    @Operation(summary = "", description = "")
    public ResponseEntity<String> addChannel(@RequestParam String channelName) {
        User user = twitchChatBotManager.getChannelInfo(channelName.toLowerCase());
        if (user != null) {
            Long result = twitchChatRedisService.addChannel(channelName);
            twitchChatBotManager.joinChannel(channelName);
            LOGGER.info("addChannel: " + result);
            return ResponseEntity.ok("Successfully joined the channel: " + channelName);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Channel \"" + channelName + "\" does not exist.");
    }

    @GetMapping("/getChannels")
    @Operation(summary = "", description = "")
    public Set<String> getChannels() {
        Set<String> followingChannels = twitchChatRedisService.getLiveChannels();
        LOGGER.info("getChannels: " + followingChannels);
        return followingChannels;
    }

    @GetMapping("/removeChannel")
    @Operation(summary = "", description = "")
    public boolean removeChannel(@RequestParam String channelName) {
        boolean removeChannel = twitchChatBotManager.leaveChannel(channelName);
        LOGGER.info("removeChannel: " + removeChannel);
        return removeChannel;
    }
}
