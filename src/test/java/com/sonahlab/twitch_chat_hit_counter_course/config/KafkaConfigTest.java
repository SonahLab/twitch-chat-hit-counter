package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class KafkaConfigTest {

    @Autowired
    private ProducerFactory<String, byte[]> producerFactory;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, byte[]> consumerFactory;

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<String, byte[]>  kafkaListenerContainerFactory;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 1, Task 2.
    @Disabled
    @Tag("Module2")
    public void testProducerFactoryConfig() {
        Map<String, Object> properties = producerFactory.getConfigurationProperties();

        assertThat(properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertThat(properties.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class.getName());
        assertThat(properties.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArraySerializer.class.getName());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 1, Task 2.
    @Disabled
    @Tag("Module2")
    public void testKafkaTemplateConfig() {
        ProducerFactory<String, byte[]> producerFactory = kafkaTemplate.getProducerFactory();
        Map<String, Object> properties = producerFactory.getConfigurationProperties();

        assertThat(properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertThat(properties.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class.getName());
        assertThat(properties.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArraySerializer.class.getName());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 2, Task 1.
    @Disabled
    @Tag("Module2")
    public void testConsumerFactoryConfig() {
        Map<String, Object> properties = consumerFactory.getConfigurationProperties();

        assertThat(properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertTrue(properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString().toLowerCase().contains( "twitch-chat-hit-counter-group-id-"));
        assertThat(properties.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
        assertThat(properties.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)).isEqualTo("false");
        assertThat(properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)).isEqualTo(StringDeserializer.class.getName());
        assertThat(properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArrayDeserializer.class.getName());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 2, Task 1.
    @Disabled
    @Tag("Module2")
    public void testConcurrentKafkaListenerContainerFactoryConfig() {
        Map<String, Object> properties = kafkaListenerContainerFactory.getConsumerFactory().getConfigurationProperties();

        assertThat(properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertTrue(properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString().toLowerCase().contains( "twitch-chat-hit-counter-group-id-"));
        assertThat(properties.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
        assertThat(properties.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)).isEqualTo("false");
        assertThat(properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)).isEqualTo(StringDeserializer.class.getName());
        assertThat(properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArrayDeserializer.class.getName());

        assertThat(kafkaListenerContainerFactory.getContainerProperties().getAckMode()).isEqualTo(ContainerProperties.AckMode.MANUAL);
    }
}
