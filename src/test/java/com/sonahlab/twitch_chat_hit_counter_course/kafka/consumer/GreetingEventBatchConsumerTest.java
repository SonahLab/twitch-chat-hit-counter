package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.config.KafkaConfig;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.List;

@SpringBootTest(classes = {
        KafkaAutoConfiguration.class,
        KafkaConfig.class,
        GreetingEventBatchConsumer.class
})
@EmbeddedKafka(partitions = 1, topics = {"test_batch_consumer_topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "twitch-chat-hit-counter.kafka.greeting-topic=test_batch_consumer_topic",
        "spring.kafka.consumer.group-id=test-batch-group-id"
})
@DirtiesContext
@Disabled
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

        // Verify all events are processed in a single batch in once processMessage() call
        ArgumentCaptor<List<ConsumerRecord<String, byte[]>>> batchCaptor =
                ArgumentCaptor.forClass(List.class);

        Mockito.verify(consumer, Mockito.timeout(5000).times(1))
                .processMessages(batchCaptor.capture(), Mockito.any(Acknowledgment.class));

        List<ConsumerRecord<String, byte[]>> batchRecords = batchCaptor.getValue();
        Assertions.assertEquals(events.size(), batchRecords.size(), "Batch size mismatch");

        // Deserialize and validate each event
        for (GreetingEvent expected : events) {
            boolean found = batchRecords.stream().anyMatch(record -> {
                try {
                    GreetingEvent actual = objectMapper.readValue(record.value(), GreetingEvent.class);
                    return expected.sender().equals(actual.sender())
                            && expected.receiver().equals(actual.receiver())
                            && expected.message().equals(actual.message());
                } catch (Exception e) {
                    return false;
                }
            });
            Assertions.assertTrue(found, "Expected event not found in batch: " + expected);
        }
    }
}
