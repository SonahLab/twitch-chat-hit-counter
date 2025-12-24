//// TODO: uncomment this code when you're ready to test this class
//package com.sonahlab.twitch_chat_hit_counter_course.kafka.producer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.serialization.ByteArrayDeserializer;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.test.EmbeddedKafkaBroker;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.kafka.test.utils.KafkaTestUtils;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.TestPropertySource;
//
//import java.time.Duration;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
///**
// * https://www.baeldung.com/spring-boot-kafka-testing
// * */
//@SpringBootTest
//@EmbeddedKafka(partitions = 1, topics = { "test_twitch_producer_topic" }, brokerProperties = {
//        "listeners=PLAINTEXT://localhost:0", "port=0"
//})
//@TestPropertySource(properties = {
//        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
//        "twitch-chat-hit-counter.kafka.twitch-chat-topic=test_twitch_producer_topic"
//})
//@DirtiesContext
//@Tag("Module5")
//// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
//@Disabled
//public class TwitchChatEventProducerTest {
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private TwitchChatEventProducer producer;
//
//    @Autowired
//    private EmbeddedKafkaBroker embeddedKafkaBroker;
//
//    private KafkaConsumer<String, byte[]> consumer;
//
//    @BeforeEach
//    void setUp() {
//        Map<String, Object> consumerProps = new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "false", embeddedKafkaBroker));
//        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);
//
//        consumer = new KafkaConsumer<>(consumerProps);
//        consumer.subscribe(Collections.singletonList("test_twitch_producer_topic"));
//    }
//
//    @AfterEach
//    void tearDown() {
//        consumer.close();
//    }
//
//    @Test
//    void publishTest() throws Exception {
//        List<TwitchChatEvent> events = List.of(
//                new TwitchChatEvent(
//                        "eventId1",
//                        1767254400000L,
//                        "channelId1",
//                        "channelName1",
//                        "userid1",
//                        "username1",
//                        12,
//                        1,
//                        "kekw omegaLUL"
//                ),
//                new TwitchChatEvent(
//                        "eventId2",
//                        1767255500000L,
//                        "channelId1",
//                        "channelName1",
//                        "userid2",
//                        "username2",
//                        24,
//                        3,
//                        "monkaW pogO"
//                )
//        );
//
//        for (int index = 0; index < events.size(); index++) {
//            String messageId = "test-id-" + index;
//            boolean success = producer.publish(messageId, events.get(index));
//            assertTrue(success);
//        }
//        ConsumerRecords<String, byte[]> records = KafkaTestUtils.getRecords(consumer, Duration.ofSeconds(10));
//
//        List<TwitchChatEvent> consumedEvents = new ArrayList<>();
//        for (ConsumerRecord<String, byte[]> record : records) {
//            TwitchChatEvent event = objectMapper.readValue(record.value(), TwitchChatEvent.class);
//            consumedEvents.add(event);
//        }
//
//        assertEquals(events.size(), consumedEvents.size(), "Number of consumed events does not match");
//
//        // Assert each sent event is contained in consumed events
//        for (TwitchChatEvent expected : events) {
//            assertTrue(consumedEvents.stream().anyMatch(consumed ->
//                    consumed.eventId().equals(expected.eventId()) &&
//                            consumed.eventTs().equals(expected.eventTs()) &&
//                            consumed.channelId().equals(expected.channelId()) &&
//                            consumed.channelName().equals(expected.channelName()) &&
//                            consumed.userId().equals(expected.userId()) &&
//                            consumed.userName().equals(expected.userName()) &&
//                            consumed.subscriptionMonths().equals(expected.subscriptionMonths()) &&
//                            consumed.subscriptionTier().equals(expected.subscriptionTier()) &&
//                            consumed.message().equals(expected.message())
//            ), "Sent event not found in consumed events: " + expected);
//        }
//    }
//}
