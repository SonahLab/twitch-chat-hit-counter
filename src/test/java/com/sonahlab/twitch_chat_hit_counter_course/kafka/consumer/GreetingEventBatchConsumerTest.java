package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.sonahlab.twitch_chat_hit_counter_course.kafka.AbstractKafkaIntegrationTest;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import com.sonahlab.twitch_chat_hit_counter_course.sql.GreetingSqlService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Integration test for GreetingEventBatchConsumer using TestContainers.
 * - Extends AbstractKafkaTestContainersTest for shared Kafka container
 * - Uses real Kafka instead of EmbeddedKafka
 * - Container is reused across test runs for fast execution
 */
@TestPropertySource(properties = {
        "twitch-chat-hit-counter.kafka.greeting-topic=test-topic",
        "spring.kafka.consumer.group-id=test-batch-consumer-group-id"
})
@Tag("Module2")
public class GreetingEventBatchConsumerTest extends AbstractKafkaIntegrationTest {

    @MockitoSpyBean
    private GreetingEventBatchConsumer consumer;

    @MockitoBean
    @Qualifier("batchGreetingSqlService")
    private GreetingSqlService greetingSqlService;

    @BeforeEach
    void setUp() {
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

        CountDownLatch latch = new CountDownLatch(events.size());
        List<GreetingEvent> consumedEvents = new ArrayList<>();

        // Set up a callback to capture consumed events and count down the latch
        Mockito.doAnswer(invocation -> {
            List<ConsumerRecord<String, byte[]>> records = invocation.getArgument(0);
            for (ConsumerRecord<String, byte[]> record : records) {
                try {
                    GreetingEvent event = objectMapper.readValue(record.value(), GreetingEvent.class);
                    synchronized (consumedEvents) {
                        consumedEvents.add(event);
                    }
                    latch.countDown();
                } catch (Exception e) {
                    // Ignore deserialization errors in test
                }
            }
            invocation.callRealMethod();
            return null;
        }).when(consumer).processMessages(any(List.class), any(Acknowledgment.class));

        // Send all events to configured topic
        for (GreetingEvent event : events) {
            byte[] eventBytes = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send(TEST_TOPIC, event.sender(), eventBytes).get();
        }

        // Wait for all messages to be processed (may be in one or multiple batches)
        assertTrue(latch.await(10, TimeUnit.SECONDS), "Not all messages were processed in time");

        // Verify that all events were consumed, regardless of batch count
        Assertions.assertEquals(events.size(), consumedEvents.size(), "Total consumed events mismatch");

        // Validate each expected event was consumed
        for (GreetingEvent expected : events) {
            boolean found = consumedEvents.stream().anyMatch(actual ->
                    expected.sender().equals(actual.sender())
                            && expected.receiver().equals(actual.receiver())
                            && expected.message().equals(actual.message())
            );
            Assertions.assertTrue(found, "Expected event not found in consumed events: " + expected);
        }
    }
}
