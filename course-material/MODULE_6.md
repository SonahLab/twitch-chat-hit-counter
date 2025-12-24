# The Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 6: Frontend Application
### Additional Learning Materials

## Overview

<br>

## Objective
![](assets/module6/images/Overview.svg)<br>

In **Module 5**, you set up the entire E2E aggregation pipeline using some manually hardcoded Twitch channels to connect to.<br>

In **Module 6**, you will add the remaining endpoints that the Frontend React application requires to fully integrate a Reporting dashboard
using our backend data.

**Goals:**
- Add Redis `db3` for Channel management to statefully keep track of what channels your application is connected to
- Implement the `TwitchRestController` which holds all the API endpoints required by the UI

<br>

## File Structure
For `Module 6`, the below file structure are all the relevant files needed.

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
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchConfig.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> dao/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisDao.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatAggregationRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> ChatBotChannelsRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatBotManager.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchHelixService.java<br>
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
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatAggregationRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> ChatBotChannelsRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatBotManagerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchHelixServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> PropertiesApplicationTest.java<br>

<br>

## Exercise 1: Configure Redis
In `application.yml`, add a new property for the Redis channels DB.

```yaml
twitch-chat-hit-counter:
  redis:
    chatbot-channels-database: 3
```

### Testing
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented to test the property above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for method(s): `redisChatbotChannelsDatabaseTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module6
    ```

<br>

#


## Task 2: Setup db3 Redis @Beans
> [!TIP]
>
> Read through [Multiple Redis Connections in Spring Boot <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://medium.com/@raphael3213/multiple-redis-connections-in-spring-boot-37f632e8e64f)

This is very similar to our initial setup for `DB0`, `DB1`, and `DB2` in `RedisConfig.java`.

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
1. Inject the properties from `application.yml`: `chatbot-channels-database`
2. Update the list of indexes to include `3` (should be `List.of(0, 1, 2, 3)`)

### Part 2
In `RedisConfig.java`, implement
```java
@Bean
@Qualifier("chatBotChannelsRedisDao")
public RedisDao chatBotChannelsRedisDao() {}
```

This RedisDao will be **dedicated** to handling operations on `DB2`.

**Requirements:**
1. Inject the `redisTemplateFactory` we just implemented in the previous task
2. Inject the `chatbot-channels-database` index
3. Create a new `RedisDao` with the correct `RedisTemplate` from the factory

### Testing
- [ ] Open `RedisConfigTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `RedisConfigTest.java` for method(s): `chatBotChannelsRedisDaoTest`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module6
    ```

<br>

#

## Exercise 3: ChatBotChannelsRedisService
### Task 1
In `ChatBotChannelsRedisService`, implement the constructor `public ChatBotChannelsRedisService()`.

**Requirements:**
1. Inject the correct RedisDao

### Task 2
In `ChatBotChannelsRedisService`, implement `public Set<String> getJoinedChannels(String username)`.

Return the Set of channel names our ChatBot is connected to.

**Requirements:**
1. Key Template: `"user#{username}#channels"`<br>
    It may seem like overkill since your username will be the only row in this DB, but it still sets up good separation
    practices. 
2. Value should be a `Set<String>` to maintain a unique set of channels that we want to connect to.

### Example 1:
> **Input**:<br>
> ```java
> ChatBotChannelsRedisService service = new ChatBotChannelsRedisService(...);
> 
> // Assume our Redis DB3 looks like this:
> // {
> //   "user#Alice#channels": ["s0mcs", "shroud"],
> //   "user#Bob#channels": ["k3soju"]
> // }
> Set<String> output = service.getJoinedChannels("Alice");
> ```
> **Output**:<br>
> ```
> ["s0mcs", "shroud"]
> ```
> **Explanation**: Alice's ChatBot should be connected to s0mcs's and shroud's stream

### Example 2:
> **Input**:<br>
> ```java
> ChatBotChannelsRedisService service = new ChatBotChannelsRedisService(...);
>
> Set<String> output = service.getJoinedChannels("NonExistentUser");
> ```
> **Output**:<br>
> ```
> []
> ```
> **Explanation**: No key for "user#NonExistentUser#channels" in Redis so it should be treated as an empty Set.


### Testing
- [ ] Open `ChatBotChannelsRedisServiceTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `ChatBotChannelsRedisServiceTest.java` for method(s): `getJoinedChannelsTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module6
    ```


### Task 3
In `ChatBotChannelsRedisService`, implement `public Long addChannels(String username, String... channelNames)`.

Return the Long length of the channels Set after you've attempted to add the `channelNames`.

**Requirements:**


### Example 1:
> **Input**:<br>
> ```java
> ChatBotChannelsRedisService service = new ChatBotChannelsRedisService(...);
>
> Long output1 = service.addChannels("Alice", "s0mcs", "shroud");
> Long output2 = service.addChannels("Alice", "s0mcs");
> ```
> **Output1**: 2<br>
> **Explanation**: "user#Alice#channel" key never existed so it should add 2 channels: ["s0mcs", "shroud"]
> 
> **Output2**: 0<br>
> **Explanation**:<br>
> The entry when `.addChannels()` is called a second time is: {"user#Alice#channel": ["s0mcs", "shroud"]}<br>
> Since the channel "s0mcs" already exists in the Set value object, nothing gets added to the Set

### Testing
- [ ] Open `ChatBotChannelsRedisServiceTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `ChatBotChannelsRedisServiceTest.java` for method(s): `addChannelsTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module6
    ```


### Task 3
In `ChatBotChannelsRedisService`, implement `public Long removeChannels(String username, String... channelNames)`.

Return the Long length of the channels Set after you've attempted to remove the `channelNames`.

**Requirements:**

### Example 1:
> **Input**:<br>
> ```java
> ChatBotChannelsRedisService service = new ChatBotChannelsRedisService(...);
>
> service.addChannels("Alice", "s0mcs", "shroud");
> Long output = service.removeChannels("Alice", "shroud", "nonexistentChannelName");
> ```
> **Output**: 1<br>
> **Explanation**:<br>
> The entry when `.addChannels()` is called is: {"user#Alice#channel": ["s0mcs", "shroud"]}<br>
> "shroud" should be successfully removed from the Set (count = 1).<br>
> "nonexistentChannelName" was never a part of the Set so nothing gets removed (count stays 1).

### Example 1:
> **Input**:<br>
> ```java
> ChatBotChannelsRedisService service = new ChatBotChannelsRedisService(...);
>
> Long output = service.removeChannels("nonexistentUser", "shroud");
> ```
> **Output**: 0<br>
> **Explanation**:<br>
> The key "user#nonexistentUser#channels" doesn't exist in Redis, so attempting to remove any values from a non-existing key just treats the value as an empty Set.

### Testing
- [ ] Open `ChatBotChannelsRedisServiceTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `ChatBotChannelsRedisServiceTest.java` for method(s): `removeChannelsTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module6
    ```


## Exercise 4: Update TwitchChatBotManager
### Task 1: Update the constructor

**Requirements:**
1. Inject the ChatBotChannelsRedisService into `TwitchChatBotManager`

### Task 2: Update the joinChannel()

**Requirements**:
1. Make sure to update the Redis DB for your `USERNAME` to maintain the channelName you've joined

### Task 3: Update the leaveChannel()

**Requirements**:
1. Make sure to update the Redis DB for your `USERNAME` to maintain the channelName you've left

### Task 4: Update the getJoinedChannels()

**Requirements**:
1. Make sure to fetch the Set of joined channels maintained in Redis


## Exercise 5: HelixService
### Part 1
In `HelixService.java`, implement `public HelixService()`.

**Requirements:**
1. Inject the `TwitchClient` bean defined in `TwitchConfig` into the constructor

### Part 2
In `HelixService.java`, implement `public Map<String, User> getChannelsMetadata(List<String> channelNames)`.

Return a Map<String, User> that maps, for all joined channels, the channelName to the Twitch Streamer's User metadata.

**Requirements:**
1. Use the `TwitchHelix` API to get all user channel information


## Exercise 6: TwitchRestController
### Part 1: Update the constructor

**Requirements:**
1. Inject the `TwitchChatAggregationRedisService`
2. Inject the `TwitchChatBotManager`
3. Inject the `TwitchHelixService`

### Part 2: Implement HTTP request endpoints
In `TwitchRestController`, implement `@GetMapping("/hitCounter")`.

This should call the helper method in `TwitchChatAggregationRedisService`.

#

In `TwitchRestController`, implement `@PutMapping("/addChannel")`.

This should call the helper method in `TwitchChatBotManager`.

#

In `TwitchRestController`, implement `@DeleteMapping("/removeChannel")`.

This should call the helper method in `TwitchChatBotManager`.

#

In `TwitchRestController`, implement `@GetMapping("/getChannels")`.

This should call the helper method in `TwitchChatBotManager`.

#

In `TwitchRestController`, implement `@GetMapping("/getChannelsMetadata")`.

This should call the helper method in `TwitchHelixService`.
