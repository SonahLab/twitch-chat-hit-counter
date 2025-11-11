package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.Duration;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test_batch_consumer_topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "twitch-chat-hit-counter.kafka.consumer.greeting-topic=test_batch_consumer_topic",
        "twitch-chat-hit-counter.kafka.batch-consumer.group-id=batch-group-id-0",
})
@DirtiesContext
@Tag("Module2")
public class GreetingEventBatchConsumerTest {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoSpyBean
    private GreetingEventBatchConsumer consumer;

    @Captor
    ArgumentCaptor<List<ConsumerRecord<String, byte[]>>> batchCaptor;

    @Test
    public void testProcessMessage() throws Exception {
        List<GreetingEvent> events = List.of(
                new GreetingEvent("id1", "Alice", "Bob", "Hello, Bob!"),
                new GreetingEvent("id2", "Bob", "Charlie", "Good morning, Charlie!"),
                new GreetingEvent("id3", "Eve", "Frank", "Hi Frank, how are you?")
        );

        for (GreetingEvent event : events) {
            byte[] eventBytes = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send("test_batch_consumer_topic", event.sender(), eventBytes).get();
        }

        // Capture batch invocations
        ArgumentCaptor<List<ConsumerRecord<String, byte[]>>> batchCaptor =
                ArgumentCaptor.forClass((Class) List.class);

        // Wait until all messages are processed using Awaitility
        Awaitility.await()
                .atMost(Duration.ofSeconds(15))
                .pollInterval(Duration.ofMillis(200))
                .untilAsserted(() ->
                        Mockito.verify(consumer, Mockito.atLeastOnce())
                                .processMessage(batchCaptor.capture(), Mockito.any())
                );

        // Flatten captured records for content validation
        List<ConsumerRecord<String, byte[]>> allRecords = batchCaptor.getAllValues().stream()
                .flatMap(List::stream)
                .toList();

        // Validate each event
        for (GreetingEvent expected : events) {
            boolean found = allRecords.stream().anyMatch(record -> {
                try {
                    GreetingEvent actual = objectMapper.readValue(record.value(), GreetingEvent.class);
                    return expected.sender().equals(actual.sender()) &&
                            expected.receiver().equals(actual.receiver()) &&
                            expected.message().equals(actual.message());
                } catch (Exception e) {
                    return false;
                }
            });
            Assertions.assertTrue(found, "Missing event: " + expected);
        }
    }
}
