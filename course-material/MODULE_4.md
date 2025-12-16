# The Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 4: Redis
### Additional Learning Materials

## Overview

<br>

## Objective
![](assets/module4/images/Overview4.svg)<br>

**Recap:**
In **Module 3**, we have a couple things:
- an API endpoint to trigger `GreetingEvent` objects to be published to a Kafka topic
- A single/batch event consumer to consume events from a Kafka topic
- persistent storage to store the `GreetingEvent` in SQL DB

Moving forward, we will be dropping the **Batch Event Pipeline** and just continue to work with the **Single Event Pipeline**.

In **Module 4**, we will be adding two Redis DBs.<br>
Redis **db0** will be reserved for **Event Deduplication**.<br>
Redis **db1** will be reserved for our **Greetings News Feed**.<br>

> [!NOTE]
>
> Redis DB are namespaced by index (not by names) starting at 0, 1, ..., N.

<br>

## Lab Setup
### Setup Local Redis Server
Start our local Redis instance via Docker: [Redis Stack <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/operate/oss_and_stack/install/archive/install-stack/docker/)<br>
1. Open and login to **Docker Desktop**
2. Start the Redis Docker container:
```bash
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
```

In **Docker**, you should now see the Redis container running locally.<br>
We should have containers for: Kafka, MySQL, and now Redis.
![](assets/module4/images/docker.jpg)<br>

### Bookmark Redis Insight
1. Go to `http://localhost:8001/` and bookmark this link

The docker run command above also exposes [Redis Insight <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/insight/) on port 8001. You can use Redis Insight by pointing your browser to localhost:8001.
The `redis/redis-stack` docker image contains both **Redis Stack server** and **Redis Insight**. A two-for-one combo to use Redis and have the Redis UI tool in one container.

In a production environment, the most likely basic setup would be:
- 3 instance(s) for the Leader nodes (M1, M2, M3)
- 3 instance(s) for the Follower nodes (R1, R2, R3)
- 1 instance(s) for the Redis Insight node (RI1)

It wouldn't be advised to have each server run both the Redis Stack Server + Redis Insight as this adds a lot of overhead on each cloud machine.

<br>

## File Structure
For `Module 4`, the below file structure are all the relevant files needed.

<img src="assets/common/module.svg" align="center"/> twitch-chat-hit-counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> src/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> main/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/sourceRoot.svg" align="center"/> java/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> com.sonahlab.twitch_chat_hit_counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisConfig.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/enum.svg" align="center"/> EventType.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEvent.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> dao/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisDao.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> EventDeduperRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/resourcesRoot.svg" align="center"/> resources/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/yaml.svg" align="center"/> application.yml<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> test/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testRoot.svg" align="center"/> java/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> com.sonahlab.twitch_chat_hit_counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> RedisConfigTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> dao/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> RedisDaoTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> EventDeduperRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> RedisRestControllerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> PropertiesApplicationTest.java<br>

<br>

## Exercise 1: Implement Data Access Object (DAO)
> [!TIP]
>
> [Data Access Object <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](DAO) Pattern(https://www.geeksforgeeks.org/system-design/data-access-object-pattern/)
Redis is Key-Value DB with a lot of capabilities and can store lots of different [Data Types <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/develop/data-types/)<br>

Peek at the [RedisTemplate.java <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html) API source code.
We will be creating a [DAO <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/java-dao-pattern) class to handle a very small subset of API calls on the RedisTemplate, more specifically the
[ValueOperations <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html),
[ListOperations <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ListOperations.html),
and [SetOperations <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/SetOperations.html) needed for our application.

<br>

### Task 1: Value Operations
### Implement [INCR <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/incr/) (Increment)
In `RedisDao.java`, implement `public Long increment(String key)`.<br>
Return a Long of the updated value after the key is incremented.

**Description**: Increments the number stored at key by one.<br>
**Library API Call**: RedisTemplate.opsForValues().[increment(key) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html#increment(K))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key1", 5L);
>
> long output1 = redisDao.increment("key1");
> long output2 = redisDao.increment("key2");
> ```
> **Output1**: 6L<br>
> **Explanation**: "key1" is incremented by +1, but the "key1" already exists in redis with a value of 5. So the +1 increment gets added to the already existing value.
>
> **Output2**: 1L<br>
> **Explanation**: "key2" doesn't exist in redis so the initial value is treated as 0. The increment method only adds +1 value and essentially acts equivalent to calling .set("key2", 1L).

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `incrementTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

### Implement [INCRBY <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/incrby/) (Increment By)
In `RedisDao.java`, implement `public Long incrementBy(String key, long delta)`.<br>
Return a Long of the updated value after the key is incremented.

**Description**: Increments the number stored at key by `increment`.<br>
**Library API Call**: RedisTemplate.opsForValues().[increment(key, delta) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html#increment(K,long))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key1", 5);
>
> long output1 = redisDao.incrementBy("key1", 10);
> long output2 = redisDao.incrementBy("key2", 60);
> ```
> **Output1**: 15<br>
> **Explanation**: "key1" is incremented by +10, but the "key1" already exists in redis with a value of 5. So the +10 increment gets added to the already existing value.
>
> **Output2**: 60L<br>
> **Explanation**: "key2" doesn't exist in redis so the initial value is treated as 0. The increment value of 60 essentially acts equivalent to calling .set("key2", 60L).

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `incrementByTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Implement [SET <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/set/)
In `RedisDao.java`, implement `public void set(String key, V value)`.<br>
Sets the KV pair by adding (if key is new) or overriding any previously set value.<br>

**Description**: Set key to hold the string value. If key already holds a value, it is overwritten, regardless of its type. Any previous time to live associated with the key is discarded on successful SET operation.<br>
**Library API Call**: RedisTemplate.opsForValues().[set(key, value) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html#set(K,V))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.set("key1", 10L);
> redisDao.set("key2", "Hello World!");
> redisDao.set("key3", 3.14);
> redisDao.set("key4", Map.of("firstName", "Jane", "lastName", "Doe"));
> ```
> **Output**: None<br>
> **Explanation**: The underlying redis library api method for `.set()` has no return value, so after calling the underlying Redis API set method, as long as no exception is thrown we can assume the operation succeeded.

<br>

#

### Implement [GET <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/get/)
In `RedisDao.java`, implement `public String get(String key)`.<br>
Return the String representation of the stored value. The value stored in Redis doesn't strictly need to be a String, but Redis will store the data type represented as a String.

**Description**: Get the value of key. If the key does not exist the special value nil is returned. An error is returned if the value stored at key is not a string, because GET only handles string values.<br>
**Library API Call**: RedisTemplate.opsForValues().[get(key) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ValueOperations.html#get(java.lang.Object))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> 
> redisDao.set("key1", 10L);
> redisDao.set("key2", "Hello World!");
> redisDao.set("key3", 3.14);
> redisDao.set("key4", Map.of("firstName", "Jane", "lastName", "Doe"));
> 
> long output1 = redisDao.get("key1");
> long output2 = redisDao.get("key2");
> long output3 = redisDao.get("key3");
> long output4 = redisDao.get("key4");
> long output5 = redisDao.get("nonexistentKey");
> ```
> **Output1**: "10"<br>
> **Output2**: "Hello World!"<br>
> **Output3**: "3.14"<br>
> **Output4**: "{\"firstName\": \"Jane\", \"lastName\": \"Doe\"}"<br>
> **Output5**: null<br>

> [!NOTE]
>
> Notice that all outputs from our `redisDao.get(key)` method are all Strings.<br>
> If you look at the Redis API method: `RedisTemplate.opsForValue().get(key)`, this library will always return a String representation of the value that is stored in that key.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `setAndGetTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 2: List Operations
### Implement [RPUSH <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/rpush/) (Right Push)
In `RedisDao.java`, implement `public Long listAdd(String key, String value)`.<br>
Return a Long of the length of the list **after** the value is appended.

**Description**: Insert all the specified values at the head of the list stored at key. If key does not exist, it is created as empty list before performing the push operations. When key holds a value that is not a list, an error is returned.<br>
**Library API Call**: RedisTemplate.opsForList().[rightPush(key, value) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ListOperations.html#rightPush(K,V))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> 
> long output1 = redisDao.listAdd("key1", "Hello");
> long output2 = redisDao.listAdd("key1", "World");
> 
> redisDao.incr("nonListKey");
> long output3 = redisDao.listAdd("nonListKey", "item1");
> ```
> **Output1**: 1<br>
> **Explanation**: {"key1": ["Hello"]}
> 
> **Output2**: 2<br>
> **Explanation**: {"key1": ["Hello", "World"]}
> 
> **Output3**: Exception is thrown<br>
> **Explanation**:<br>
> The Redis mapping is: {"nonListKey": "1"}. Adding "item1" to a Long is invalid, so an error is thrown.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `listAddTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Implement [LRANGE <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/lrange/) (Range)
In `RedisDao.java`, implement `public Long listGet(String key)`.<br>
Return the List\<String> stored at the key.

**Description**: Returns the specified elements of the list stored at key. The offsets start and stop are zero-based indexes, with 0 being the first element of the list (the head of the list), 1 being the next element and so on.<br>
These offsets can also be negative numbers indicating offsets starting at the end of the list. For example, -1 is the last element of the list, -2 the penultimate, and so on.<br>
**Library API Call**: RedisTemplate.opsForList().[range(key, start, end) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/ListOperations.html#range(K,long,long))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> 
> redisDao.listAdd("key1", "Hello");
> redisDao.listAdd("key1", "World");
> redisDao.incr("nonListKey");
> 
> List<String> output1 = redisDao.listGet("key1");
> List<String> output2 = redisDao.listGet("nonexistentKey");
> List<String> output3 = redisDao.listGet("nonListKey");
> ```
> **Output1**: ["Hello", "World"]<br>
> **Explanation**: {"key1": ["Hello", "World"]} (valid case)
> 
> **Output2**: []<br>
> **Explanation**: "nonexistentKey" doesn't exist in Redis DB so it return an empty list
> 
> **Output3**: Exception is thrown<br>
> **Explanation**:<br>
> The Redis mapping is: {"nonListKey": "1"}. Getting a List from a Long value is invalid, so an error is thrown.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `listGetTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 3: Set Operations

### Implement [SADD <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/sadd/) (Set Add)
In `RedisDao.java`, implement `public Long setAdd(String key, String... values)`.<br>
Return a Long for the length of the set **after** the value(s) are added.

**Description**: Add the specified members to the set stored at key. Specified members that are already a member of this set are ignored. If key does not exist, a new set is created before adding the specified members.<br>
An error is returned when the value stored at key is not a set.<br>
**Library API Call**: RedisTemplate.opsForSet().[add(key, values) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/SetOperations.html#add(K,V...))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> 
> long output1 = redisDao.setAdd("key1", "Alice", "Bob");
> long output2 = redisDao.setAdd("key1", "Alice");
> ```
> **Output1**: 2<br>
> **Explanation**: [] → ["Alice", "Bob"]. "Alice" and "Bob" were successfully added to the initially empty "key1"
> 
> **Output2**: 0<br>
> **Explanation**: "Alice" was already added to the set previously so attempting to add this value to the set again has no effect, and the set size remains 2.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `setAddTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Implement [SREM <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/srem/) (Set Remove)
In `RedisDao.java`, implement `public Long setRemove(String key, String... value)`.<br>
Return a Long for the number of removed elements.

**Description**: Remove the specified members from the set stored at key. Specified members that are not a member of this set are ignored. If key does not exist, it is treated as an empty set and this command returns 0.<br>
An error is returned when the value stored at key is not a set.<br>
**Library API Call**: RedisTemplate.opsForSet().[remove(key, values) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/SetOperations.html#remove(K,java.lang.Object...))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> 
> redisDao.setAdd("key1", "Alice");
> redisDao.setAdd("key1", "Bob");
> redisDao.increment("key2");
> 
> long output1 = redisDao.setRemove("key1", "Bob", "Charlie");
> long output2 = redisDao.setRemove("nonexistentKey", "Charlie");
> long output3 = redisDao.setRemove("key2", "Charlie");
> ```
> **Output1**: 1<br>
> **Explanation**:<br>
> ["Alice", "Bob"] → ["Alice"] w/ 1 value(s) removed.<br>
> "Bob" is successfully removed from the Set.<br>
> "Charlie" was never in the Set so there is nothing to remove.
> 
> **Output2**: 0<br>
> **Explanation**: "nonexistentKey" doesn't exist in Redis DB so the Redis library treats the key as am empty set, nothing is removed.
> 
> **Output4**: Exception is thrown<br>
> **Explanation**:<br>
> {"key2": "1"}, the value at "key2" is a Long type so attempting to remove a value from a Long type is invalid. Redis library will throw an error.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `setRemoveTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Implement [SMEM <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/docs/latest/commands/smem/) (Set Members)
In `RedisDao.java`, implement `public Set<String> getSetMembers(String key)`.<br>
Return the Set\<String> stored at the key.

**Description**: Returns all the members of the set value stored at key.<br>
**Library API Call**: RedisTemplate.opsForSet().[members(key) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/SetOperations.html#members(K))

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> redisDao.setAdd("key1", "Alice");
> redisDao.setAdd("key1", "Bob");
> 
> Set<String> output1 = redisDao.getSetMembers("key1");
> Set<String> output2 = redisDao.getSetMembers("nonexistentKey");
> 
> redisDao.incr("key2");
> Set<String> output3 = redisDao.getSetMembers("key2");
> ```
> **Output1**: ["Alice", "Bob"]<br>
> **Explanation**: key="key1" has a set of ["Alice", "Bob"].
> 
> **Output2**: []<br>
> **Explanation**: key="nonexistentKey" is not in the DB, Redis will treat this as an empty Set,
> 
> **Output3**: Exception is thrown<br>
> **Explanation**: key="key2" has a value of 1, so attempting to get the Set from a value that isn't a set will throw an exception.

#

### Testing
- [ ] Open `RedisDaoTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `RedisDaoTest.java` for the test method(s): `getSetMembersTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

## Lesson: Redis Autoconfiguration
![](assets/module4/images/connect.png)<br>

https://github.com/spring-projects/spring-boot/blob/main/module/spring-boot-data-redis/src/main/java/org/springframework/boot/data/redis/autoconfigure/DataRedisAutoConfiguration.java

Spring Data Redis autoconfigures the RedisTemplate for us, but if you follow the trail of how RedisProperties is created it'll use some default values:
- `spring.data.redis.database`: 0
- `spring.data.redis.host`: localhost
- `spring.data.redis.port`: 6379

We will set the host/port, but since we're creating more than 1+ RedisTemplates we will need to manually create some @Beans so that any operations on these separate templates will interact with the correct DB.

Set the properties in application.yml.

Create the Map<Integer, RedisTemplate> bean.

## Exercise 2: Event Deduper
![](assets/module4/images/EventDeduper.svg)<br>
Zooming in on the diagram from the **[Objective <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_3.md#objective)** section, we want our `GreetingEventConsumer` to be "smart" by adding some deduplication logic.
Anytime we see an event from the Kafka topic, we should only process the event if we've never processed it before.

This is where KV Stores like Redis/Memcache come into play. Being able to quickly read/write from an in-memory cache acting as a Deduper is common across all systems in Big Tech (and just one of many many of KV datastore usecases).

Think of Redis as a distributed **HashMap (Java) or Dictionary (Python)** that can handle large amounts reads/writes.

Let's dive in.

<br>

#

### Task 1: Setup Spring Data Redis properties
> [!IMPORTANT]
>
> Read through [Spring Data Redis <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://redis.io/learn/develop/java/redis-and-spring-course/lesson_2).
> We will be following this integration guide.

In `application.yml` we will need to set 2 properties to auto-configure our application to connect to our Redis server.
```yaml
spring:
  redis:
    host: localhost
    port: 6379
```

We will also designate `db0` for purposes of deduping all events in our application.
```yaml
twitch-chat-hit-counter:
  redis:
    event-dedupe-database: 0
```

#

### Testing
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for method(s): `springRedisConfigsTest()` and `redisDeduperDatabaseTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 2: Setup db0 Redis @Beans
> [!TIP]
>
> Read through [Multiple Redis Connections in Spring Boot <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://medium.com/@raphael3213/multiple-redis-connections-in-spring-boot-37f632e8e64f)

Because Spring Boot Redis only configures one `RedisTemplate` (limiting aspect of autoconfiguration), you will need to manually configure most of the beans yourself.

### Part 1
In `RedisConfig.java`, implement `public Map<Integer, RedisTemplate<String, String>> redisTemplateFactory()`. This factory @Bean will setup a mapping between our intended:<br>
```{
  dbIndex: RedisTemplate<String, String>
}
```

Example:
```
    0: RedisTemplate (object that will operate on DB0),
    1: RedisTemplate (object that will operate on DB1),
    ...,
    N: RedisTemplate (object that will operate on DBN),
```

**Requirements:**
1. Inject the properties from `application.yml`: host/port and `event-dedupe-database`
2. For each index (should just be a List<Integer> containing our only database index `0`):
   1. Create a new `RedisStandaloneConfigure` and set the unique database index on it.
   2. Create a new `LettuceConnectionFactory` using the `RedisStandaloneConfigure`
      1. Manually configure `.afterPropertiesSet()`
      2. Manually configure `.start()`
   3. Create a new `RedisTemplate<String, String>`:
      1. Set `.setConnectionFactory(lettuceConnectionFactory)`
      2. Set `.setKeySerializer(new StringRedisSerializer())`
      3. Set `.setValueSerializer(new StringRedisSerializer())`
      4. Manually configure `.afterPropertiesSet()`
   4. Add the mapping for the {databaseIndex: RedisTemplate} in the factory `Map<Integer, RedisTemplate<String, String>>`

### Part 2
In `RedisConfig.java`, implement
```java
@Bean
@Qualifier("eventDedupeRedisDao")
public RedisDao eventDedupeRedisDao() {}
```

This RedisDao will be **dedicated** to handling operations on `DB0`.

**Requirements:**
1. Inject the `redisTemplateFactory` we just implemented in the previous task
2. Inject the `event-dedupe-database` index
3. Create a new `RedisDao` with the correct `RedisTemplate` from the factory

### Testing
- [ ] Open `RedisConfigTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `RedisConfigTest.java` for method(s): `eventDedupeRedisDaoTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 3: Implement processEvent()
In `EventDeduperRedisService.java`, implement `public void processEvent(EventType eventType, String eventId)`.<br>

You will generate a Redis Key entry based on the input **EventType** and **eventId**, and increment the value.

**Requirements:**
1. The Redis key should be generated using this template: `"{EventType}#{eventId}"`
2. The Redis value should be incremented by `1`.

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> EventDeduperRedisService eventDeduper = new EventDeduperRedisService(redisDao);
> 
> eventDeduper.processEvent(EventType.GREETING_EVENT, "id1");
> String output = redisDao.get("GREETING_EVENT#id1");
> ```
> **Output**: "1"<br>
> **Explanation**: After .processEvent(...) is called, our Redis DB should now be
> ```json
> {
>   "GREETING_EVENT#id1": "1"
> }
> ```

#

### Testing
- [ ] Open `EventDeduperRedisServiceTest.java` ─ already implemented with the example(s) above
- [ ] Remove `@Disabled` in `EventDeduperRedisServiceTest.java` for method(s): `processEventTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 4: Implement isDupeEvent()
In `EventDeduperRedisService.java`, implement `public boolean isDupeEvent(EventType eventType, String eventId)`.<br>

Return a boolean whether the key (generated by **EventType** and **eventId**) already exists in our Redis DB.<br>
**True:** if the Redis Key already exists, then the event we're processing **IS** a duplicate.<br>
**False:** if the Redis Key does not exist, then the event we're processing **IS NOT** a duplicate.<br>

**Requirements:**
1. The Redis key is generated using this template: `"{EventType}#{eventId}"`
2. The Redis value at the key should be `1`

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> EventDeduperRedisService eventDeduper = new EventDeduperRedisService(redisDao);
> 
> // Set some initial keys
> redisDao.incr("GREETING_EVENT#id2");
> 
> boolean output1 = eventDeduper.isDupeEvent(EventType.GREETING_EVENT, "id1");
> boolean output2 = eventDeduper.isDupeEvent(EventType.GREETING_EVENT, "id2");
> ```
> **Output1**: false<br>
> **Explanation**: no key="GREETING_EVENT#id1" exists, so the incoming eventId **IS NOT** a duplicate.<br>
>
> **Output2**: true<br>
> **Explanation**: key="GREETING_EVENT#id2" exists, so the incoming eventId **IS** a duplicate.<br>

#

### Testing
- [ ] Open `EventDeduperRedisServiceTest.java` ─ already implemented with the example(s) above
- [ ] Remove `@Disabled` in `EventDeduperRedisServiceTest.java` for method(s): `isDupeEventTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 5: Hook up `GreetingEventConsumer` to the `EventDeduperRedisService`
In `GreetingEventConsumer.java`, pass in the `EventDeduperRedisService.java` class into the constructor.

In `GreetingEventConsumer.processMessage(..)`, before we store a `GreetingEvent` into our SQL table, we will only process events that are not in our deduper Redis database (`db0`).

**Consumer Process Flow:**
1. Check Redis to see if the kafka message key is a duplicate
2. If **isDupeEvent == True**:
    1. Do nothing (skip processing the event)
3. If **isDupeEvent == False**:
    1. Write the event to SQL
    2. Update the Redis DB to add the event's key, so that we can skip this event from being processed if we ever see an event with the same key again.

#

### Testing
- [ ] TODO

#

### Integration Testing
- [ ] TODO (how to test this since UUID is generated uniquely for each publishing of kafka event)
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `/api/kafka/publishGreetingEvent`
- [ ] Play around with **Kafka API**: `/api/kafka/publishGreetingEvent` for a duplicate event
- [ ] In **Redis UI**, verify that the `GreetingEvent` key is written into the Redis DB.

<br>

## Exercise 3: Greetings News Feed
![](assets/module4/images/GreetingNewsfeed.svg)<br>
`GreetingEventConsumer` will now only process `GreetingEvent` that have not been processed before.<br>

Let's build a Twitter-like news feed for all greetings. On a Twitter newsfeed, each user has the relevant tweets on their timeline based on everyone they follow and all tagged posts.

Our **Greetings News Feed** will create a similar feed for each `receiver`, aggregating a List\<GreetingEvent> for all Greetings directed towards them.

At **time0**, Alice greets Bob, "Hi Bob, I'm Alice".<br>
At **time1**, Charlie greets Bob, "Hey Bob, it's been a while.".<br>

```
      Bob
    ↗     ↖
Alice     Charlie
```

**Bob's feed** will have this List\<GreetingEvent>:
```json
{
  "receiver#Bob": [
    {
      "eventId": "id1",
      "sender": "Alice",
      "receiver": "Bob",
      "message": "Hi Bob, I'm Alice!"
    },
    {
      "eventId": "id2",
      "sender": "Charlie",
      "receiver": "Bob",
      "message": "Hey Bob, it's been a while."
    }
  ]
}
```
![](assets/module4/images/feedmock.png)<br>

#

### Task 1: Configure `application.yml`
In `application.yml`, we will need to designate `db1` for purposes of aggregating the Greetings for each `receiver`.
```yaml
twitch-chat-hit-counter:
  redis:
    greeting-feed-database: 1
```

#

### Testing
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented with the property test above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for method(s): `redisGreetingFeedDatabaseTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 2: Setup db1 Redis @Beans
> [!TIP]
>
> Read through [Multiple Redis Connections in Spring Boot <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://medium.com/@raphael3213/multiple-redis-connections-in-spring-boot-37f632e8e64f)

This is very similar to our initial setup for `DB0` in `RedisConfig.java`. After implementing the support needed for `DB1`, you should the benefits of why I've set up the `redisTemplateFactory` the way I did.
It becomes very easy to add and remove database indexes without requiring a lot of verbose, duplicate code as the original Medium article above shows. 

### Part 1
In `RedisConfig.java`, update `public Map<Integer, RedisTemplate<String, String>> redisTemplateFactory()`.

**Example:**
```
    0: RedisTemplate (object that will operate on DB0),
    1: RedisTemplate (object that will operate on DB1),
    ...,
    N: RedisTemplate (object that will operate on DBN),
```

**Requirements:**
1. Inject the properties from `application.yml`: `greeting-feed-database`
2. Update the list of indexes to include `1` (should be `List.of(0, 1)`)

### Part 2
In `RedisConfig.java`, implement
```java
@Bean
@Qualifier("greetingFeedRedisDao")
public RedisDao greetingFeedRedisDao() {}
```

This RedisDao will be **dedicated** to handling operations on `DB1`.

**Requirements:**
1. Inject the `redisTemplateFactory` we just implemented in the previous task
2. Inject the `greeting-feed-database` index
3. Create a new `RedisDao` with the correct `RedisTemplate` from the factory

### Testing
- [ ] Open `RedisConfigTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `RedisConfigTest.java` for method(s): `greetingFeedRedisDaoTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```


<br>

#

### Task 3: Implement GreetingRedisService
In `GreetingRedisService`, implement `public Long addGreetingToFeed(GreetingEvent event)`.<br>
Return a Long that represents the length of the List after the `GreetingEvent` is appended to the list stored at the key for the `GreetingEvent.receiver()`.

**Requirements:**
1. The Redis key should be generated using this template: `"receiver#{receiver}"`
2. The Redis value should be a List\<String> adding the `GreetingEvent` represented as a JSON String

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> GreetingRedisService greetingRedisService = new GreetingRedisService(redisDao);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
> GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");
> 
> Long output1 = greetingRedisService.addGreetingToFeed(event1);
> Long output2 = greetingRedisService.addGreetingToFeed(event2);
> Long output3 = greetingRedisService.addGreetingToFeed(event3);
> ```
> **Output1**: 1<br>
> **Explanation**: **receiver#Bob** has a feed of length 1, greeting(s) from "Alice"
>
> **Output2**: 2<br>
> **Explanation**: **receiver#Bob** has a feed of length 2, greeting(s) from "Alice" and "Charlie"
>
> **Output3**: 1<br>
> **Explanation**: **receiver#David** has a feed of length 1, greeting(s) from "Charlie"

#

### Testing
- [ ] Open `GreetingRedisServiceTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingRedisServiceTest.java` for method(s): `addGreetingToFeedTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 4: Update Consumer
In `GreetingEventConsumer.java`, update dat.

<br>

#

### Exercise 4: Redis API
![](assets/module4/images/redisController.svg)<br>

<br>

### Task 1: Query News Feed from Redis
In `GreetingRedisService`, implement `public List<GreetingEvent> getGreetingFeed(String name)`.<br>
Return the List\<GreetingEvent> stored in `db1` using the input `name`.

**Requirements:**
1. The Redis key should be generated using this template: `"receiver#{name}"`
2. Redis value is stored as a List\<String>, but we want to output a List\<GreetingEvent> so you will need to deserialize each JSON String back to a `GreetingEvent` object

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> GreetingRedisService greetingRedisService = new GreetingRedisService(redisDao);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
> GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");
> 
> greetingRedisService.addGreetingToFeed(event1);
> greetingRedisService.addGreetingToFeed(event2);
> greetingRedisService.addGreetingToFeed(event3);
>
> List<GreetingEvent> output1 = greetingRedisService.getGreetingFeed("Bob");
> List<GreetingEvent> output2 = greetingRedisService.getGreetingFeed("David");
> List<GreetingEvent> output3 = greetingRedisService.getGreetingFeed("Alice");
> ```
> **Output1**:<br>
> ```json
> [
>   {
>     "eventId": "id1",
>     "sender": "Alice",
>     "receiver": "Bob",
>     "message": "Hi Bob, I'm Alice!"
>   },
>   {
>     "eventId": "id2",
>     "sender": "Charlie",
>     "receiver": "Bob",
>     "message": "Hey Bob, it's been a while."
>   }
> ]
> ```
> **Explanation**: **receiver#Bob** has a feed of length 2, holding greeting(s) from "Alice" and "Charlie"
>
> **Output2**:<br>
> ```json
> [
>   {
>     "eventId": "id3",
>     "sender": "Charlie",
>     "receiver": "David",
>     "message": "Yo."
>   }
> ]
> ```
> **Explanation**: **receiver#David** has a feed of length 1, holding greeting(s) from "Charlie"
>
> **Output3**: `[]`<br>
> **Explanation**: **receiver#Alice** has a feed of length 0, no one has greeted her

#

### Testing
- [ ] Open `GreetingRedisServiceTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingRedisServiceTest.java` for method(s): `getGreetingFeedTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

<br>

#

### Task 2: Hook up `RedisRestController` to `GreetingRedisService`
In `RedisRestController.java`, implement `public List<GreetingEvent> getRedisGreetingFeed(String name)`.<br>

Return the List\<GreetingEvent> from the `getGreetingFeed(name)` you implemented in the previous task.

### Example 1:
> ```java
> 
> RedisDao redisDao = new RedisDao(...);
> GreetingRedisService greetingRedisService = new GreetingRedisService(redisDao);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "Bob", "Hey Bob, it's been a while.");
> GreetingEvent event3 = new GreetingEvent("id3", "Charlie", "David", "Yo.");
> 
> greetingRedisService.addGreetingToFeed(event1);
> greetingRedisService.addGreetingToFeed(event2);
> greetingRedisService.addGreetingToFeed(event3);
>
> WebClient webClient = WebClient.builder()
>         .baseUrl("http://localhost:8080/api/")
>         .build();
>
> boolean output1 = webClient.post()
>         .uri(uriBuilder -> uriBuilder
>                 .path("/redis/queryGreetingFeed")
>                 .queryParam("name", "Bob")
>                 .build())
>         .retrieve()
>         .bodyToMono(List.class)
>         .block();
> 
> boolean output2 = webClient.post()
>         .uri(uriBuilder -> uriBuilder
>                 .path("/redis/queryGreetingFeed")
>                 .queryParam("name", "Alice")
>                 .build())
>         .retrieve()
>         .bodyToMono(List.class)
>         .block();
> ```
> **Output1**:<br>
> ```json
> [
>   {
>     "eventId": "id1",
>     "sender": "Alice",
>     "receiver": "Bob",
>     "message": "Hi Bob, I'm Alice!"
>   },
>   {
>     "eventId": "id2",
>     "sender": "Charlie",
>     "receiver": "Bob",
>     "message": "Hey Bob, it's been a while."
>   }
> ]
> ```
> 
> **Output2**:<br>
> ```json
> []
> ```

#

### Testing
- [ ] Open `RedisRestControllerTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `RedisRestControllerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

#

### Integration Testing
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] **Redis API**: `GET /api/kafka/queryGreetingFeed`
- [ ] In **Redis UI**, verify that the HTTP Response output greeding feed is the same as the feed saved in Redis `db1`
