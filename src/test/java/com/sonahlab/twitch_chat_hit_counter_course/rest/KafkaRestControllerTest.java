package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.sonahlab.twitch_chat_hit_counter_course.kafka.producer.GreetingEventProducer;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.*;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KafkaRestController.class)
@Import(GreetingEventProducer.class)
@Tag("Module2")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
@Disabled
class KafkaRestControllerTest {

    @Autowired MockMvc mockMvc;

    @MockitoBean
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @MockitoSpyBean
    private GreetingEventProducer greetingEventProducer;

    @Test
    void publishGreetingEventsEndpointTest() throws Exception {
        doReturn(true)
                .when(greetingEventProducer)
                .publish(any(String.class), any(GreetingEvent.class));
        ArgumentCaptor<String> eventIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<GreetingEvent> eventCaptor = ArgumentCaptor.forClass(GreetingEvent.class);

        List<GreetingEvent> events = List.of(
                // eventId should be generated within the KafkaRestController
                new GreetingEvent(null, "Alice", "Bob", "Hi hi hi"),
                new GreetingEvent(null, "Charlie", "David", "Yo.")
        );

        for (GreetingEvent expectedEvent : events) {
            mockMvc.perform(post("/api/kafka/publishGreetingEvent")
                            .param("sender", expectedEvent.sender())
                            .param("receiver", expectedEvent.receiver())
                            .param("message", expectedEvent.message()))
                    .andExpect(status().isOk())
                    .andExpect(content().string("true"));
        }

        verify(greetingEventProducer, times(events.size())).publish(
                eventIdCaptor.capture(),
                eventCaptor.capture()
        );

        List<GreetingEvent> capturedEvents = eventCaptor.getAllValues();
        assertEquals(events.size(), capturedEvents.size());

        for (int index = 0; index < events.size(); index++) {
            GreetingEvent expected = events.get(index);
            GreetingEvent actual = capturedEvents.get(index);

            // Validate event contents
            assertEquals(expected.sender(), actual.sender());
            assertEquals(expected.receiver(), actual.receiver());
            assertEquals(expected.message(), actual.message());

            assertNotNull(actual.eventId());
            assertEquals(eventIdCaptor.getAllValues().get(index), actual.eventId());
        }
    }
}
