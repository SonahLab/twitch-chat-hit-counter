# Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 4: Redis

### Lesson

### Additional Learning Materials





## File Structure
For `Module 4`, the below file structure are all the relevant files needed.
```
twitch-chat-hit-counter/src/
├── main/
│   ├── java/
│   │   └── com.sonahlab.twitch_chat_hit_counter/
│   │       ├── config/
│   │       │   └── RedisConfig.java
│   │       ├── kafka/
│   │       │   └── consumer/
│   │       │       └── GreetingEventConsumer.java
│   │       ├── model/
│   │       │   └── GreetingEvent.java
│   │       ├── redis/
│   │       │   ├── dao/
│   │       │   │   └── RedisDao.java
│   │       │   ├── EventDeduperRedisService.java
│   │       │   └── GreetingRedisService.java
│   │       ├── rest/
│   │       │   └── ApplicationRestController.java
│   │       └── utils/
│   │           └── EventType.java
│   └── resources/
│       └── application.yml
└── test/
    └── java/
        └── java/com.sonahlab.twitch_chat_hit_counter/
            ├── config/
            │   └── RedisConfigTest.java
            ├── redis/
            │   ├── dao/
            │   │   └── RedisDaoTest.java
            │   ├── EventDeduperRedisServiceTest.java
            │   └── GreetingRedisServiceTest.java
            └── rest/
                └── ApplicationRestControllerTest.java
```






## Overview
![](assets/module4/images/Overview4.svg)<br>

> [!NOTE]
> 
> Redis DB are namespaced by index, meaning it starts at db 0, 1, ..., N.

In **Module 4**, we will be adding two Redis DBs.<br>
Redis DB0 will be reserved for **Event Deduplication**.<br>
Redis DB1 will be reserved for our **Greetings News Feed**.<br>





## Setup Local Redis Server
Start our local Redis instance via Docker (https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-stack/docker/)<br>
1. Open and login to **Docker Desktop**
2. Start the Redis Docker container:
```bash
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
```

In **Docker**, you should now see the Redis container running locally.<br>
We should have containers for: Kafka, MySQL, and now Redis.
![](assets/module4/images/docker.jpg)<br>






## Exercise 1: Implement RedisDao.java
Redis is Key-Value DB with a lot of capabilities and can store lots of different data structures: https://redis.io/docs/latest/develop/data-types/.<br>
Read through the `RedisTemplate.java` source code, we will be creating a DAO class to handle ValueOperations, ListOperations, and SetOperations.<br>
https://www.baeldung.com/java-dao-pattern

### Task 1: LIST Operations
#### 1.1: increment
### Example 1: 
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key", 10L);
> ```
> **Output**: None

### Example 2:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key", 10L);
> long output1 = redisDao.get("key");
> long output2 = redisDao.get("nonexistentKey");
> ```
> **Output1**: 10<br>
> **Output2**: null

### Example 3:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key", 10L);
> long output1 = redisDao.get("key");
> long output2 = redisDao.get("nonexistentKey");
> ```
> **Output1**: 10<br>
> **Output2**: null


> <span style="color:#0000008c">RedisDao redisDao = new RedisDao(...);<br></span>
> <span style="color:#0000008c">GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");<br></span>
> <span style="color:#0000008c">GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");<br></span>
> <span style="color:#0000008c">GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");<br></span>
>
> <span style="color:#0000008c">greetingSqlService.insert(event1);<br></span>
> <span style="color:#0000008c">**List\<GreetingEvent> output1 = greetingSqlService.queryAllEvents();** // Should return 1 event<br></span>
>
> <span style="color:#0000008c">greetingSqlService.insert(event2);<br></span>
> <span style="color:#0000008c">**List\<GreetingEvent> output2 = greetingSqlService.queryAllEvents();** // Should return 2 events<br></span>
>
> <span style="color:#0000008c">greetingSqlService.insert(event3);<br></span>
> <span style="color:#0000008c">**List\<GreetingEvent> output3 = greetingSqlService.queryAllEvents();** // Should return 2 events<br></span>
>
> **Output1**:<br>
> ```json
> [
>     {
>         "eventId": "id1",
>         "sender": "Alice",
>         "receiver": "Bob",
>         "message": "Hi Bob, I'm Alice!"
>     }
> ]
> ```



## Exercise 1: Event Deduper

### Task 1: Connect to Redis
https://redis.io/learn/develop/java/redis-and-spring-course/lesson_2

