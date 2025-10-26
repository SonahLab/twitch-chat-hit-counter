package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class KafkaConfigTest {

    @Autowired
    private ProducerFactory<String, byte[]> producerFactory;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 1, Task 2.
    @Disabled
    @Tag("Module2")
    public void testProducerFactoryConfig() {
        Map<String, Object> config = producerFactory.getConfigurationProperties();

        assertThat(config.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertThat(config.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class.getName());
        assertThat(config.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArraySerializer.class.getName());
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 2 Exercise 1, Task 2.
    @Disabled
    @Tag("Module2")
    public void testKafkaTemplateConfig() {
        ProducerFactory<String, byte[]> producerFactory = kafkaTemplate.getProducerFactory();
        Map<String, Object> config = producerFactory.getConfigurationProperties();

        assertThat(config.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG)).isEqualTo("localhost:9092");
        assertThat(config.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG)).isEqualTo(StringSerializer.class.getName());
        assertThat(config.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArraySerializer.class.getName());
    }
}
