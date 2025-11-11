package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KafkaConfigTest {

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    private ConsumerFactory<String, Object> consumerFactory;

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<?, ?>  kafkaListenerContainerFactory;

    @Autowired
    private ConcurrentKafkaListenerContainerFactory<?, ?>  batchkafkaListenerContainerFactory;

    @Autowired
    private ApplicationContext context;

    @Test
    @Tag("Module2")
    public void testKafkaTemplateConfig() {
        ProducerFactory<String, byte[]> producerFactory = kafkaTemplate.getProducerFactory();
        Map<String, Object> properties = producerFactory.getConfigurationProperties();
        List<String> bootstrapServers = (List<String>) properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);

        assertEquals(1, bootstrapServers.size());
        assertTrue(bootstrapServers.contains("localhost:9092"));
        assertThat(properties.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class);
        assertThat(properties.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArraySerializer.class);
    }

    @Test
    @Tag("Module2")
    public void testConcurrentKafkaListenerContainerFactoryConfig() {
        Map<?, ?> properties = kafkaListenerContainerFactory.getConsumerFactory().getConfigurationProperties();
        List<String> bootstrapServers = (List<String>) properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);

        assertEquals(1, bootstrapServers.size());
        assertTrue(bootstrapServers.contains("localhost:9092"));
        assertTrue(properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString().toLowerCase().contains("twitch-chat-hit-counter-group-id-"));
        assertThat(properties.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
        assertThat(properties.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)).isEqualTo(Boolean.FALSE);
        assertThat(properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)).isEqualTo(StringDeserializer.class);
        assertThat(properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArrayDeserializer.class);
        assertThat(kafkaListenerContainerFactory.getContainerProperties().getAckMode()).isEqualTo(ContainerProperties.AckMode.MANUAL);
        assertNull(kafkaListenerContainerFactory.isBatchListener());
    }

    @Test
    @Tag("Module2")
    public void testBatchConcurrentKafkaListenerContainerFactoryConfig() {
        Map<?, ?> properties = batchkafkaListenerContainerFactory.getConsumerFactory().getConfigurationProperties();
        List<String> bootstrapServers = (List<String>) properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG);

        assertEquals(1, bootstrapServers.size());
        assertTrue(bootstrapServers.contains("localhost:9092"));
        assertTrue(properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString().toLowerCase().contains("twitch-chat-hit-counter-group-id-"));
        assertThat(properties.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
        assertThat(properties.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)).isEqualTo(Boolean.FALSE);
        assertThat(properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)).isEqualTo(StringDeserializer.class);
        assertThat(properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArrayDeserializer.class);
        assertThat(kafkaListenerContainerFactory.getContainerProperties().getAckMode()).isEqualTo(ContainerProperties.AckMode.MANUAL);
        assertTrue(batchkafkaListenerContainerFactory.isBatchListener());
    }
}
