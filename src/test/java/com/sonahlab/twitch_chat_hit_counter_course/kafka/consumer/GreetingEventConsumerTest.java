package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.kafka.AbstractKafkaIntegrationTest;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestPropertySource(properties = {
        "twitch-chat-hit-counter.kafka.greeting-topic=test-topic",
        "spring.kafka.consumer.group-id=test-group-id"
})
@Tag("Module2")
@Execution(ExecutionMode.SAME_THREAD)
public class GreetingEventConsumerTest extends AbstractKafkaIntegrationTest {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoSpyBean
    private GreetingEventConsumer consumer;

    @MockitoBean
    @Qualifier("singleGreetingSqlService")
    private GreetingSqlService greetingSqlService;

    @BeforeEach
    void setUp() {
        super.setup();
        // Configure SQL mock
        when(greetingSqlService.insert(any(List.class))).thenAnswer(invocation -> {
            List<?> events = invocation.getArgument(0);
            return events.size();
        });
    }

    @Test
    public void testProcessMessage() throws Exception {
        List<GreetingEvent> events = List.of(
                new GreetingEvent("id1", "Alice", "Bob", "Hello, Bob!"),
                new GreetingEvent("id2", "Bob", "Charlie", "Good morning, Charlie!"),
                new GreetingEvent("id3", "Eve", "Frank", "Hi Frank, how are you?")
        );

        for (GreetingEvent event : events) {
            byte[] eventBytes = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send(TEST_TOPIC, event.sender(), eventBytes).get();
        }

        // Verify all events processed
        for (GreetingEvent event : events) {
            Mockito.verify(consumer, Mockito.timeout(5000).atLeast(1))
                    .processMessage(Mockito.argThat(record -> {
                        try {
                            GreetingEvent actual = objectMapper.readValue(record.value(), GreetingEvent.class);
                            return actual.sender().equals(event.sender()) &&
                                    actual.receiver().equals(event.receiver()) &&
                                    actual.message().equals(event.message());
                        } catch (Exception e) {
                            return false;
                        }
                    }), Mockito.any(Acknowledgment.class));
        }
    }
}
