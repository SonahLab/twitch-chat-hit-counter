package com.sonahlab.twitch_chat_hit_counter_course.redis.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@Tag("Module4")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
@Disabled
public class RedisDaoTest {

    @Container
    private static final RedisContainer REDIS_CONTAINER = new RedisContainer("redis:7.0");

    // Dynamically configure Redis connection for Spring
    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", REDIS_CONTAINER::getFirstMappedPort);
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedisDao redisDao;

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeAll
    static void startContainer() {
        REDIS_CONTAINER.start();
    }

    @AfterEach
    void resetContainer() {
        // This wipes the entire Redis DB after every single test
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.serverCommands().flushDb();
            return null;
        });
    }

    @AfterAll
    static void stopContainer() {
        REDIS_CONTAINER.stop();
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void incrementTest() {
        redisTemplate.opsForValue().increment("key1", 5);

        redisDao.increment("key1");
        redisDao.increment("key2");

        assertEquals(6, Long.valueOf(redisTemplate.opsForValue().get("key1")));
        assertEquals(1, Long.valueOf(redisTemplate.opsForValue().get("key2")));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void incrementByTest() {
        redisTemplate.opsForValue().increment("key1", 5);

        redisDao.incrementBy("key1", 10);
        redisDao.incrementBy("key2", 60);

        assertEquals(15, Long.valueOf(redisTemplate.opsForValue().get("key1")));
        assertEquals(60, Long.valueOf(redisTemplate.opsForValue().get("key2")));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void setAndGetTest() throws JsonProcessingException {
        redisDao.set("key1", String.valueOf(10));
        redisDao.set("key2", "Hello World!");
        redisDao.set("key3", String.valueOf(3.14));
        redisDao.set("key4", OBJECT_MAPPER.writeValueAsString(Map.of(
                "firstName", "Jane",
                "lastName", "Doe")));

        assertEquals("10", redisDao.get("key1"));
        assertEquals("Hello World!", redisDao.get("key2"));
        assertEquals("3.14", redisDao.get("key3"));
        Map<String, String> value4 = OBJECT_MAPPER.readValue(redisDao.get("key4"), Map.class);
        assertEquals("Jane", value4.get("firstName"));
        assertEquals("Doe", value4.get("lastName"));
        assertEquals(null, redisDao.get("nonexistentKey"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void listAddTest() {
        long output1 = redisDao.listAdd("key1", "Hello");
        long output2 = redisDao.listAdd("key1", "World");

        // catch exception thrown by RedisTemplate when attempting to add value to a non List value
        boolean exceptionCaught = false;
        redisTemplate.opsForValue().increment("nonListKey");
        try {
            redisDao.listAdd("nonListKey", "item1");
        } catch (Exception e) {
            exceptionCaught = true;
        }

        assertEquals(1, output1);
        assertEquals(2, output2);
        assertTrue(exceptionCaught);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void listGetTest() {
        redisDao.listAdd("key1", "Hello");
        redisDao.listAdd("key1", "World");

        assertThat(redisDao.listGet("key1")).hasSize(2).containsExactly("Hello", "World");
        assertThat(redisDao.listGet("nonexistentKey")).hasSize(0);

        // catch exception thrown by RedisTemplate when attempting to get a List when the value is not a List
        boolean exceptionCaught = false;
        redisDao.increment("nonListKey");
        try {
            List<String> output3 = redisDao.listGet("nonListKey");
        } catch (Exception e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void setAddTest() {
        assertEquals(2, redisDao.setAdd("key1", "Alice", "Bob"));
        assertEquals(0, redisDao.setAdd("key1", "Alice"));
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void setRemoveTest() {
        redisDao.setAdd("key1", "Alice");
        redisDao.setAdd("key1", "Bob");

        assertEquals(1, redisDao.setRemove("key1", "Bob", "Charlie"));
        assertEquals(0, redisDao.setRemove("nonexistentKey", "Charlie"));

        // catch exception thrown by RedisTemplate when attempting to remove a member from a Set when the value is not a Set
        boolean exceptionCaught = false;
        redisDao.increment("key2");
        try {
            long output3 = redisDao.setRemove("key2", "Charlie");
        } catch (Exception e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void getSetMembersTest() {
        redisDao.setAdd("key1", "Alice");
        redisDao.setAdd("key1", "Bob");

        assertThat(redisDao.getSetMembers("key1")).hasSize(2).contains("Alice", "Bob");
        assertThat(redisDao.getSetMembers("nonexistentKey")).hasSize(0);

        // catch exception thrown by RedisTemplate when attempting to get a Set when the value is not a Set
        boolean exceptionCaught = false;
        redisDao.increment("key2");
        try {
            Set<String> output3 = redisDao.getSetMembers("key2");
        } catch (Exception e) {
            exceptionCaught = true;
        }
        assertTrue(exceptionCaught);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void hashIncrByTest() {
        redisDao.hashIncrBy("student#Alice#coursesTaken", "semester1", 5);
        redisDao.hashIncrBy("student#Alice#coursesTaken", "semester2", 4);

        String output1 = redisTemplate.opsForHash().get("student#Alice#coursesTaken", "semester1").toString();
        String output2 = redisTemplate.opsForHash().get("student#Alice#coursesTaken", "semester2").toString();

        assertEquals("5", output1);
        assertEquals("4", output2);
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 4.
    @Disabled
    void hashGetAllTest() {
        redisDao.hashIncrBy("student#Alice#coursesTaken", "semester1", 5);
        redisDao.hashIncrBy("student#Alice#coursesTaken", "semester2", 4);

        Map<String, String> output1 = redisDao.hashGetAll("student#Alice#coursesTaken");
        assertThat(output1).hasSize(2)
                .containsEntry("semester1", "5")
                .containsEntry("semester2", "4");

        Map<String, String> output2 = redisDao.hashGetAll("student#Bob");
        assertThat(output2).hasSize(0);
    }
}
