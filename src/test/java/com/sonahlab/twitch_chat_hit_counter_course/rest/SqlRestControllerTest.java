package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
@Disabled
@Tag("Module3")
public class SqlRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        registry.add("twitch-chat-hit-counter.sql.greeting-table", () -> "test_greeting_table");
        registry.add("twitch-chat-hit-counter.sql.greeting-batch-table", () -> "test_greeting_table");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("singleGreetingSqlService")
    private GreetingSqlService greetingSqlService;

    @Autowired
    @Qualifier("batchGreetingSqlService")
    private GreetingSqlService batchGreetingSqlService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS test_greeting_table;");
        jdbcTemplate.execute("""
            CREATE TABLE IF NOT EXISTS test_greeting_table (
                event_id VARCHAR(255) PRIMARY KEY,
                sender VARCHAR(255),
                receiver VARCHAR(255),
                message TEXT
            );
        """);
    }

    @Test
    void testQueryEndpoint() throws Exception {
        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");

        jdbcTemplate.execute("""
            INSERT INTO test_greeting_table (event_id, sender, receiver, message)
            VALUES ("id1", "Alice", "Bob", "Hi Bob, I'm Alice!")
            ON DUPLICATE KEY UPDATE event_id = event_id;
        """);
        List<GreetingEvent> result1 = callQueryAllEventsEndpoint();
        assertThat(result1).hasSize(1).containsExactly(event1);

        jdbcTemplate.execute("""
            INSERT INTO test_greeting_table (event_id, sender, receiver, message)
            VALUES ("id2", "Charlie", "David", "Yo.")
            ON DUPLICATE KEY UPDATE event_id = event_id;
        """);
        List<GreetingEvent> result2 = callQueryAllEventsEndpoint();
        assertThat(result2).hasSize(2).containsExactly(event1, event2);

        jdbcTemplate.execute("""
            INSERT INTO test_greeting_table (event_id, sender, receiver, message)
            VALUES ("id1", "Echo", "Frank", "Hello there.")
            ON DUPLICATE KEY UPDATE event_id = event_id;
        """);
        List<GreetingEvent> result3 = callQueryAllEventsEndpoint();
        assertThat(result3).hasSize(2).containsExactly(event1, event2);
    }

    private List<GreetingEvent> callQueryAllEventsEndpoint() throws Exception {
        String jsonResponse = mockMvc.perform(get("/api/sql/queryGreetingEvents")
                .param("tableName", "test_greeting_table"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return MAPPER.readValue(jsonResponse, new TypeReference<List<GreetingEvent>>() {});
    }
}
