package com.sonahlab.twitch_chat_hit_counter_course.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.sonahlab.twitch_chat_hit_counter_course.redis.dao.RedisDao;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.List;
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
public class Config {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    // TODO: Add Kafka configs here
    @Bean
    public ConsumerFactory<String, byte[]> consumerFactory(
            @Value("${spring.kafka.consumer.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId,
            @Value("${spring.kafka.consumer.auto-offset-reset}") String autoOffsetReset,
            @Value("${spring.kafka.consumer.enable-auto-commit}") String enableAutoCommit,
            @Value("${spring.kafka.consumer.key-deserializer}") String keyDeserializer,
            @Value("${spring.kafka.consumer.value-deserializer}") String valueDeserializer
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> kafkaListenerContainerFactory(
            ConsumerFactory<String, byte[]> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, byte[]> batchConsumerFactory(
            @Value("${spring.kafka.consumer.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id-batch}") String groupId,
            @Value("${spring.kafka.consumer.auto-offset-reset}") String autoOffsetReset,
            @Value("${spring.kafka.consumer.enable-auto-commit}") String enableAutoCommit,
            @Value("${spring.kafka.consumer.key-deserializer}") String keyDeserializer,
            @Value("${spring.kafka.consumer.value-deserializer}") String valueDeserializer,
            @Value("${spring.kafka.consumer.max-poll-records}") int maxPollRecords
    ) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> batchKafkaListenerContainerFactory(
            ConsumerFactory<String, byte[]> batchConsumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(batchConsumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ProducerFactory<String, byte[]> producerFactory(
            @Value("${spring.kafka.producer.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.producer.key-serializer}") String keySerializer,
            @Value("${spring.kafka.producer.value-serializer}") String valueSerializer
    ) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializer);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializer);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate(ProducerFactory<String, byte[]> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }


    // TODO: Add Redis configs here
    @Bean
    public Map<Integer, RedisTemplate<String, String>> redisTemplateFactory(
            @Value("${spring.redis.host}") String hostName,
            @Value("${spring.redis.port}") int port,
            @Value("${spring.redis.event-dedupe-database}") int databaseIndexDb0,
            @Value("${spring.redis.greeting-feed-database}") int databaseIndexDb1,
            @Value("${spring.redis.twitch-chat-hit-counter-database}") int databaseIndexDb2
    ) {
        Map<Integer, RedisTemplate<String, String>> factory = new HashMap<>();

        for (int databaseIndex : List.of(databaseIndexDb0, databaseIndexDb1, databaseIndexDb2)) {
            RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(hostName, port);
            config.setDatabase(databaseIndex);
            LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(config);
            connectionFactory.afterPropertiesSet();
            connectionFactory.start();

            RedisTemplate<String, String> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new StringRedisSerializer());
            template.afterPropertiesSet();

            factory.putIfAbsent(databaseIndex, template);
        }

        return factory;
    }

    @Bean
    @Qualifier("eventDedupeRedisDao")
    public RedisDao eventDedupeRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${spring.redis.event-dedupe-database}") int databaseIndex
    ) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("greetingRedisDao")
    public RedisDao greetingRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${spring.redis.greeting-feed-database}") int databaseIndex) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    @Qualifier("twitchChatRedisDao")
    public RedisDao twitchChatRedisDao(
            Map<Integer, RedisTemplate<String, String>> redisTemplateFactory,
            @Value("${spring.redis.twitch-chat-hit-counter-database}") int databaseIndex) {
        return new RedisDao(redisTemplateFactory.get(databaseIndex));
    }

    @Bean
    public TwitchClient twitchClient() {
        // Get from Twitch Dev Console
        String CLIENT_ID = "kx29yhv9hva3kqpenhpdmc9oayc4vy";
        String CLIENT_SECRET = "ee04a6tx7sz58qg39pgho8fumi9q27";
        // Generate from https://twitchtokengenerator.com or do it programmatically
        String OAUTH_TOKEN = "d60cjy0t2kf5kjcuwcxbhsswod2bp7";
        TwitchClient twitchClient = TwitchClientBuilder.builder()
                .withClientId(CLIENT_ID)
                .withClientSecret(CLIENT_SECRET)
                .withEnableHelix(true)
                .withEnableChat(true)
                .withChatAccount(new OAuth2Credential("twitch", OAUTH_TOKEN))
                .build();

        return twitchClient;
    }
}
