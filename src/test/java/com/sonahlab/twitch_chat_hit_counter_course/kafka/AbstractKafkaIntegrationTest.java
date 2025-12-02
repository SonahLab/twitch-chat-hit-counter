package com.sonahlab.twitch_chat_hit_counter_course.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.redis.EventDeduperRedisService;
import com.sonahlab.twitch_chat_hit_counter_course.redis.GreetingRedisService;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.errors.UnknownTopicOrPartitionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootTest(
        properties = {
                "logging.level.org.springframework.kafka=warn",
                "logging.level.org.apache.kafka=warn",
                "logging.level.org.testcontainers=info"
        }
)
@Testcontainers
@Execution(ExecutionMode.SAME_THREAD)
public abstract class AbstractKafkaIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractKafkaIntegrationTest.class);

    protected static final String TEST_TOPIC = "test-topic";
    private static final NewTopic NEW_TEST_TOPIC = new NewTopic(TEST_TOPIC, 1, (short) 1);

    @Container
    protected static final ConfluentKafkaContainer KAFKA_CONTAINER =
            new ConfluentKafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.6.0"))
                    .withEnv("KAFKA_AUTO_CREATE_TOPICS_ENABLE", "true");

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");
        registry.add("spring.kafka.consumer.enable-auto-commit", () -> "false");
    }

    @Autowired
    private KafkaAdmin kafkaAdmin;

    @Autowired
    protected KafkaTemplate<String, byte[]> kafkaTemplate;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected EventDeduperRedisService eventDeduperRedisService;

    @MockitoBean
    protected GreetingRedisService greetingRedisService;

    @BeforeEach
    public synchronized void setup() {
        AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());

        // Delete Topic
        try {
            adminClient.deleteTopics(Collections.singleton(TEST_TOPIC)).all().get(10, TimeUnit.SECONDS);
            LOGGER.info("Deleting topic {}", TEST_TOPIC);
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            if (e.getCause() instanceof UnknownTopicOrPartitionException) {
                LOGGER.warn("Topic {} never existed so no need to delete.", TEST_TOPIC);
            }
        }

        // Re-create Topic
        try {
            adminClient.createTopics(Collections.singleton(NEW_TEST_TOPIC)).all().get(10, TimeUnit.SECONDS);
            LOGGER.info("Created topic {}", NEW_TEST_TOPIC);
        } catch (Exception e) {
            if (e.getCause() instanceof org.apache.kafka.common.errors.TopicExistsException) {
                LOGGER.warn("Topic {} already existed (race condition), proceeding.", NEW_TEST_TOPIC);
            }
        }
    }
}
