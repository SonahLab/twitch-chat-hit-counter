package com.sonahlab.twitch_chat_hit_counter_course.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * Config class for all of our application's beans. The beans defined here are singletons that we
 * can use and inject throughout any other classes in our app.
 *
 * Recommended learning materials to learn about how Spring Boot sets up Configurations:
 *  - Configuration (https://docs.spring.io/spring-framework/reference/core/beans/java/configuration-annotation.html)
 *  - Bean (https://docs.spring.io/spring-framework/reference/core/beans/java/bean-annotation.html)
 */
@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> batchKafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            @Value("${twitch-chat-hit-counter.kafka.batch-consumer.group-id}") String batchGroupId
    ) {
        // Need to copy over the props into a new map as ConsumerFactory config properties map is unmodifiable
        Map<String, Object> props = new HashMap<>(consumerFactory.getConfigurationProperties());
        // Need to explicitly overwrite the group-id that gets injected
        props.put(ConsumerConfig.GROUP_ID_CONFIG, batchGroupId);
        ConsumerFactory copy = new DefaultKafkaConsumerFactory(props);
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(copy);
        // Need to explicitly set this listener as BATCH (spring kafka defaults it to SINGLE)
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
