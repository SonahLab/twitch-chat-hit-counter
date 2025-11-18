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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
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
    private ApplicationContext context;

    @Autowired
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    @Qualifier("kafkaListenerContainerFactory")
    private ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory;

    @Autowired
    @Qualifier("batchKafkaListenerContainerFactory")
    private ConcurrentKafkaListenerContainerFactory<String, byte[]> batchKafkaListenerContainerFactory;

    @Test
    @Tag("Module2")
    /**
     * IMPORTANT: This bean gets Autoconfigured for us by Spring Kafka
     * {@link KafkaAutoConfiguration}
     *
     * At runtime Spring checks that we never created a {@link KafkaTemplate} Bean and will autoconfigure this bean for us.
     * @Bean
     * @ConditionalOnMissingBean(KafkaTemplate.class)
     * public KafkaTemplate<?, ?> kafkaTemplate(...) {}
     * */
    public void kafkaTemplate_beanTest() {
        // Test that the default 'kafkaTemplate' is autoconfigured by Spring as part of Spring Kafka library
        assertTrue(context.containsBean("kafkaTemplate"));

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
    /**
     * IMPORTANT: This bean gets Autoconfigured for us by Spring Kafka
     * {@link KafkaAutoConfiguration}
     *
     * At runtime Spring checks that we never created a {@link ConsumerFactory} Bean and will autoconfigure this bean for us.
     * <p>
     * @Bean
     * @ConditionalOnMissingBean(ConsumerFactory.class)
     * public DefaultKafkaConsumerFactory<?, ?> kafkaConsumerFactory (...) {}
     * </p>
     *
     * -> Then it goes into {@link org.springframework.boot.autoconfigure.kafka.KafkaAnnotationDrivenConfiguration}
     * -> Then configures {@link ConcurrentKafkaListenerContainerFactoryConfigurer}
     * -> Then back in KafkaAnnotationDrivenConfiguration it creates:
     * @Bean
     * @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
     * ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory
     * */
    public void kafkaListenerContainerFactory_beanTest() {
        // Test that the default 'kafkaListenerContainerFactory' is autoconfigured by Spring as part of Spring Kafka library
        assertTrue(context.containsBean("kafkaListenerContainerFactory"));

        Map<String, Object> properties = kafkaListenerContainerFactory.getConsumerFactory().getConfigurationProperties();
        List<String> bootstrapServers = (List<String>) properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);

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
    /**
     * IMPORTANT: This bean DOES NOT get Autoconfigured for us by Spring Kafka
     * This @Bean is something we should be creating manually in {@link KafkaConfig}.
     *
     * Main differences from the autoconfigured Spring Kafka 'kafkaListenerContainerFactory' bean is this batchKafkaListenerContainerFactory
     * 1. should have a different 'group-id'
     * 2. listener should be true, default is null (meaning autoconfigured listener is single event consumer)
     * */
    public void batchKafkaListenerContainerFactory_beanTest() {
        // Test that the @Bean 'batchKafkaListenerContainerFactory' WE created is set up properly
        assertTrue(context.containsBean("batchKafkaListenerContainerFactory"));

        Map<String, Object> properties = batchKafkaListenerContainerFactory.getConsumerFactory().getConfigurationProperties();
        List<String> bootstrapServers = (List<String>) properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG);

        assertEquals(1, bootstrapServers.size());
        assertTrue(bootstrapServers.contains("localhost:9092"));
        assertTrue(properties.get(ConsumerConfig.GROUP_ID_CONFIG).toString().toLowerCase().contains("twitch-chat-hit-counter-group-id-batch-"));
        assertThat(properties.get(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG)).isEqualTo("earliest");
        assertThat(properties.get(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG)).isEqualTo(Boolean.FALSE);
        assertThat(properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG)).isEqualTo(StringDeserializer.class);
        assertThat(properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG)).isEqualTo(ByteArrayDeserializer.class);
        assertThat(batchKafkaListenerContainerFactory.getContainerProperties().getAckMode()).isEqualTo(ContainerProperties.AckMode.MANUAL);
        assertTrue(batchKafkaListenerContainerFactory.isBatchListener());
    }
}
