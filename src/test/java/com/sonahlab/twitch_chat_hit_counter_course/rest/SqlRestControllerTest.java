package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SqlRestController.class)
@Tag("Module3")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 3.
@Disabled
public class SqlRestControllerTest {

    private static final String SINGLE_GREETING_EVENT_TABLE_NAME = "test_single_greeting_table";
    private static final String BATCH_GREETING_EVENT_TABLE_NAME = "test_batch_greeting_table";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JdbcTemplate jdbcTemplate;

    @MockitoBean
    @Qualifier("singleGreetingSqlService")
    private GreetingSqlService greetingSqlService;

    @MockitoBean
    @Qualifier("batchGreetingSqlService")
    private GreetingSqlService batchGreetingSqlService;

    private final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    void testQueryEndpoint() throws Exception {
        when(greetingSqlService.sqlTableName()).thenReturn(SINGLE_GREETING_EVENT_TABLE_NAME);
        when(batchGreetingSqlService.sqlTableName()).thenReturn(BATCH_GREETING_EVENT_TABLE_NAME);

        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");

        when(greetingSqlService.queryAllEvents()).thenReturn(List.of(event1));
        List<String> response = callQueryAllEventsEndpoint(SINGLE_GREETING_EVENT_TABLE_NAME);
        List<GreetingEvent> result1 = new ArrayList<>();
        for (String eventStr : response) {
            result1.add(MAPPER.readValue(eventStr, GreetingEvent.class));
        }
        assertThat(result1).hasSize(1).containsExactly(event1);

        when(greetingSqlService.queryAllEvents()).thenReturn(List.of(event1, event2));
        List<String> response2 = callQueryAllEventsEndpoint(SINGLE_GREETING_EVENT_TABLE_NAME);
        List<GreetingEvent> result2 = new ArrayList<>();
        for (String eventStr : response2) {
            result2.add(MAPPER.readValue(eventStr, GreetingEvent.class));
        }
        assertThat(result2).hasSize(2).containsExactly(event1, event2);
    }

    @Test
    void testQueryEndpoint_batch() throws Exception {
        when(greetingSqlService.sqlTableName()).thenReturn(SINGLE_GREETING_EVENT_TABLE_NAME);
        when(batchGreetingSqlService.sqlTableName()).thenReturn(BATCH_GREETING_EVENT_TABLE_NAME);

        GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
        GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");

        when(greetingSqlService.queryAllEvents()).thenReturn(List.of(event1, event2));
        List<String> response = callQueryAllEventsEndpoint(BATCH_GREETING_EVENT_TABLE_NAME);
        List<GreetingEvent> result = new ArrayList<>();
        for (String eventStr : response) {
            result.add(MAPPER.readValue(eventStr, GreetingEvent.class));
        }
        assertThat(result).hasSize(2).containsExactly(event1, event2);
    }

    private List<String> callQueryAllEventsEndpoint(String tableName) throws Exception {
        String jsonResponse = mockMvc.perform(get("/api/sql/queryGreetingEvents")
                .param("tableName", tableName))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return MAPPER.readValue(jsonResponse, new TypeReference<>() {});
    }
}
