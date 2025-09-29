package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ExtendWith(SpringExtension.class)
@Tag("Module3")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
@Disabled
public class GreetingSqlServiceTest {

    @Container
    static MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("test-db")
            .withUsername("username")
            .withPassword("password");

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", MYSQL_CONTAINER::getDriverClassName);
        registry.add("twitch-chat-hit-counter.sql.greeting-table", () -> "test_greeting_table");
        registry.add("twitch-chat-hit-counter.sql.greeting-batch-table", () -> "test_greeting_table");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private GreetingSqlService greetingSqlService;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_greeting_table (
                event_id VARCHAR(255) PRIMARY KEY,
                sender VARCHAR(255),
                receiver VARCHAR(255),
                message VARCHAR(255)
            );
        """);
    }

    @Test
    void insertTest() {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hello");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Diana", "Hi!");

        int insert1 = greetingSqlService.insert(event1);
        int insert2 = greetingSqlService.insert(event2);

        assertEquals(1, insert1);
        assertEquals(1, insert2);

        List<GreetingEvent> results = greetingSqlService.queryAllEvents();

        assertEquals(2, results.size());
        assertTrue(results.contains(event1));
        assertTrue(results.contains(event2));
    }

    @Test
    void insertBatchTest() {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hello");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Diana", "Hi!");

        int result = greetingSqlService.insertBatch(List.of(event1, event2));

        assertEquals(2, result);

        List<GreetingEvent> results = greetingSqlService.queryAllEvents();

        assertEquals(2, results.size());
        assertTrue(results.contains(event1));
        assertTrue(results.contains(event2));
    }
}
