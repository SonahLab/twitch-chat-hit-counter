//// TODO: uncomment this code when you're ready to test this class
//package com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.sonahlab.twitch_chat_hit_counter_course.kafka.consumer.TwitchChatEventConsumer;
//import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Tag;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//import org.springframework.test.annotation.DirtiesContext;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
//
//import java.util.List;
//
//@SpringBootTest
//@EmbeddedKafka(partitions = 1, topics = {"test_twitch_consumer_topic"}, brokerProperties = {"listeners=PLAINTEXT://localhost:9092"})
//@TestPropertySource(properties = {
//        "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
//        "twitch-chat-hit-counter.kafka.greeting-topic=test_twitch_consumer_topic",
//        "spring.kafka.consumer.group-id=test-group-id"
//})
//@DirtiesContext
//@Tag("Module5")
//// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
//@Disabled
//public class TwitchChatEventConsumerTest {
//
//    @Autowired
//    private KafkaTemplate<String, byte[]> kafkaTemplate;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockitoSpyBean
//    private TwitchChatEventConsumer consumer;
//
//    @Test
//    public void testProcessMessage() throws Exception {
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
//                ),
//                new TwitchChatEvent(
//                        "eventId3",
//                        1767255500001L,
//                        "channelId1",
//                        "channelName1",
//                        "userid3",
//                        "username3",
//                        24,
//                        3,
//                        "hello hello"
//                )
//        );
//
//        for (TwitchChatEvent event : events) {
//            byte[] eventBytes = objectMapper.writeValueAsBytes(event);
//            kafkaTemplate.send("test_twitch_consumer_topic", event.sender(), eventBytes).get();
//        }
//
//        // Verify all events processed
//        for (TwitchChatEvent event : events) {
//            Mockito.verify(consumer, Mockito.timeout(5000).atLeast(1))
//                    .processMessage(Mockito.argThat(record -> {
//                        try {
//                            TwitchChatEvent actual = objectMapper.readValue(record.value(), TwitchChatEvent.class);
//                            return actual.eventId().equals(event.eventId()) &&
//                                    actual.eventTs().equals(event.eventTs()) &&
//                                    actual.channelId().equals(event.channelId()) &&
//                                    actual.channelName().equals(event.channelName()) &&
//                                    actual.userId().equals(event.userId()) &&
//                                    actual.userName().equals(event.userName()) &&
//                                    actual.subscriptionMonths().equals(event.subscriptionMonths()) &&
//                                    actual.subscriptionTier().equals(event.subscriptionTier()) &&
//                                    actual.message().equals(event.message());
//                            return false;
//                        } catch (Exception e) {
//                            return false;
//                        }
//                    }), Mockito.any(Acknowledgment.class));
//        }
//    }
//}
