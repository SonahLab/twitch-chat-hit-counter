package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test_consumer_topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "twitch-chat-hit-counter.kafka.greeting-topic=test_consumer_topic",
        "spring.kafka.consumer.group-id=test-group-id"
})
@DirtiesContext
@Tag("Module2")
public class GreetingEventConsumerTest {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoSpyBean
    private GreetingEventConsumer consumer;

    @Test
    public void testProcessMessage() throws Exception {
        List<GreetingEvent> events = List.of(
                new GreetingEvent("id1", "Alice", "Bob", "Hello, Bob!"),
                new GreetingEvent("id2", "Bob", "Charlie", "Good morning, Charlie!"),
                new GreetingEvent("id3", "Eve", "Frank", "Hi Frank, how are you?")
        );

        for (GreetingEvent event : events) {
            byte[] eventBytes = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send("test_consumer_topic", event.sender(), eventBytes).get();
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
