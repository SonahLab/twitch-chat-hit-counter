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
│   ├── java/com.sonahlab.twitch_chat_hit_counter/
│   │   ├── config/
│   │   │   └── RedisConfig.java
│   │   ├── kafka/
│   │   │   └── consumer/
│   │   │       └── GreetingEventConsumer.java
│   │   ├── model/
│   │   │   └── GreetingEvent.java
│   │   ├── redis/
│   │   │   ├── dao/
│   │   │   │   └── RedisDao.java
│   │   │   ├── EventDeduperRedisService.java
│   │   │   └── GreetingRedisService.java
│   │   ├── rest/
│   │   │   └── ApplicationRestController.java
│   │   └── utils/
│   │       └── EventType.java
│   └── resources/
│       └── application.yml
└── test/
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
> ![](assets/module4/images/Overview4.svg)<br>

> [!NOTE]
> Redis namespacing is index based, meaning it starts at db 0, ..., n.

In **Module 4**, we will be adding two Redis DBs.<br>
Redis DB0 will be reserved for **Event Deduplication**.<br>
Redis DB1 will be reserved for our **Greetings News Feed**.<br>
