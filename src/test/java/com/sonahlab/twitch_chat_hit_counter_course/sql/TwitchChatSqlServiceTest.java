//// TODO: uncomment this code when you're ready to test this class
//package com.sonahlab.twitch_chat_hit_counter_course.sql;
//
//import com.sonahlab.twitch_chat_hit_counter_course.config.SqlConfig;
//import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@JdbcTest
//@Import({SqlConfig.class})
//@Testcontainers
//@Tag("Module5")
//public class TwitchChatSqlServiceTest {
//
//    @Container
//    static MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0")
//            .withDatabaseName("test-db")
//            .withUsername("username")
//            .withPassword("password");
//
//    @DynamicPropertySource
//    static void overrideProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
//        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
//        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
//        registry.add("spring.datasource.driver-class-name", MYSQL_CONTAINER::getDriverClassName);
//        registry.add("twitch-chat-hit-counter.sql.twitch-chat-table", () -> "test_twitch_chat_table");
//    }
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private TwitchChatSqlService twitchChatSqlService;
//
//    @BeforeEach
//    void setup() {
//        jdbcTemplate.execute("DROP TABLE IF EXISTS test_twitch_chat_table;");
//        jdbcTemplate.execute("""
//            CREATE TABLE IF NOT EXISTS test_twitch_chat_table (
//                event_id VARCHAR(255) PRIMARY KEY,
//                event_ts BIGINT,
//                channel_id VARCHAR(255),
//                channel_name VARCHAR(255),
//                user_id VARCHAR(255),
//                username VARCHAR(255),
//                subscription_months INT,
//                subscription_tier INT,
//                message TEXT
//            );
//        """);
//    }
//
//    @Test
//    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
//    @Disabled
//    void insertTest() {
//        TwitchChatEvent event1 = new TwitchChatEvent(
//                "eventId1",
//                1767254400000L,
//                "channelId1",
//                "channelName1",
//                "userid1",
//                "username1",
//                12,
//                1,
//                "kekw omegaLUL"
//        );
//        TwitchChatEvent event2 = new TwitchChatEvent(
//                "eventId2",
//                1767255500000L,
//                "channelId1",
//                "channelName1",
//                "userid2",
//                "username2",
//                24,
//                3,
//                "monkaW pogO"
//        );
//        // This event is a duplicate (same eventId as event2), will be ignored by SQL
//        TwitchChatEvent event3 = new TwitchChatEvent(
//                "eventId2",
//                1767255500001L,
//                "channelId1",
//                "channelName1",
//                "userid3",
//                "username3",
//                24,
//                3,
//                "hello hello"
//        );
//
//        int insert1 = twitchChatSqlService.insert(List.of(event1));
//        int insert2 = twitchChatSqlService.insert(List.of(event2));
//        int insert3 = twitchChatSqlService.insert(List.of(event3));
//
//        assertEquals(1, insert1);
//        assertEquals(1, insert2);
//        assertEquals(0, insert3);
//
//        List<TwitchChatEvent> results = jdbcTemplate.query(
//                "SELECT * FROM test_twitch_chat_table",
//                (rs, rowNum) -> new TwitchChatEvent(
//                        rs.getString("event_id"),
//                        rs.getBigDecimal("event_ts"),
//                        rs.getString("channel_id"),
//                        rs.getString("channel_name"),
//                        rs.getString("user_id"),
//                        rs.getString("username"),
//                        rs.getInt("subscription_months"),
//                        rs.getInt("subscription_tier"),
//                        rs.getString("message")
//                )
//        );
//
//        assertEquals(2, results.size());
//        assertTrue(results.contains(event1));
//        assertTrue(results.contains(event2));
//    }
//
//    @Test
//    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
//    @Disabled
//    void queryTest() {
//        TwitchChatEvent event1 = new TwitchChatEvent(
//                "eventId1",
//                1767254400000L,
//                "channelId1",
//                "channelName1",
//                "userid1",
//                "username1",
//                12,
//                1,
//                "kekw omegaLUL"
//        );
//        TwitchChatEvent event2 = new TwitchChatEvent(
//                "eventId2",
//                1767255500000L,
//                "channelId1",
//                "channelName1",
//                "userid2",
//                "username2",
//                24,
//                3,
//                "monkaW pogO"
//        );
//        // This event is a duplicate (same eventId as event2), will be ignored by SQL
//        TwitchChatEvent event3 = new TwitchChatEvent(
//                "eventId2",
//                1767255500001L,
//                "channelId1",
//                "channelName1",
//                "userid3",
//                "username3",
//                24,
//                3,
//                "hello hello"
//        );
//
//        twitchChatSqlService.insert(List.of(event1));
//        List<TwitchChatEvent> output1 = twitchChatSqlService.queryAllEvents();
//        twitchChatSqlService.insert(List.of(event2));
//        List<TwitchChatEvent> output2 = twitchChatSqlService.queryAllEvents();
//        twitchChatSqlService.insert(List.of(event3));
//        List<TwitchChatEvent> output3 = twitchChatSqlService.queryAllEvents();
//
//        assertEquals(1, output1.size());
//        assertEquals(2, output2.size());
//        assertEquals(2, output3.size());
//
//        assertTrue(output1.contains(event1));
//        assertTrue(output2.contains(event1));
//        assertTrue(output2.contains(event2));
//        assertTrue(output3.contains(event1));
//        assertTrue(output3.contains(event2));
//        assertFalse(output3.contains(event3));
//    }
//}
