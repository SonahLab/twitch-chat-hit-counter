package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EmbeddedKafka(
        partitions = 1,
        topics = { "test_controller_topic" },
        brokerProperties = { "listeners=PLAINTEXT://localhost:0", "port=0" }
)
@DirtiesContext
@TestPropertySource(properties = {
        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "twitch-chat-hit-counter.kafka.greeting-topic=test_controller_topic"
})
@Tag("Module2")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2.
@Disabled
class KafkaRestControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @Autowired EmbeddedKafkaBroker embeddedKafkaBroker;

    @Value("${twitch-chat-hit-counter.kafka.greeting-topic}")
    String topic;

    private KafkaConsumer<String, byte[]> consumer;

    @BeforeEach
    void setUp() {
        Map<String, Object> props = new HashMap<>(
                KafkaTestUtils.consumerProps("controller-test-group", "false", embeddedKafkaBroker)
        );
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); // CRITICAL

        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
    }

    @AfterEach
    void tearDown() {
        if (consumer != null) {
            consumer.close();
        }
    }

    @Test
    void publishGreetingEventsEndpointTest() throws Exception {
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
                    // Validate the HTTP POST Response returns True for the successful writes to Kafka
                    .andExpect(content().string("true"));

            // Read back the event written to the test kafka topic
            ConsumerRecords<String, byte[]> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10));
            assertThat(records).hasSize(1);

            ConsumerRecord<String, byte[]> record = records.iterator().next();
            GreetingEvent actualEvent = objectMapper.readValue(record.value(), GreetingEvent.class);

            // Validate event contents
            try {
                UUID.fromString(actualEvent.eventId());
            } catch (Exception e) {
                throw new RuntimeException("Invalid UUID eventId: " + actualEvent.eventId(), e);
            }
            assertThat(actualEvent.sender()).isEqualTo(expectedEvent.sender());
            assertThat(actualEvent.receiver()).isEqualTo(expectedEvent.receiver());
            assertThat(actualEvent.message()).isEqualTo(expectedEvent.message());
        }
    }
}
