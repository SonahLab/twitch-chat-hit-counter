# Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 5: Twitch API

### Lesson


### Additional Learning Materials



<br>

## File Structure
For `Module 5`, the below file structure are all the relevant files needed.

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
<img src="assets/common/class.svg" align="center"/> TwitchConfig.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> producer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEventProducer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEvent.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> dao/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisDao.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> EventDeduperRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> OAuthRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatSqlService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchAuthService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatBotManager.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> utils/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/enum.svg" align="center"/> EventType.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/resourcesRoot.svg" align="center"/> resources/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/yaml.svg" align="center"/> application.yml<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/yaml.svg" align="center"/> twitch-key.properties<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> test/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testRoot.svg" align="center"/> java/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> com.sonahlab.twitch_chat_hit_counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchConfigTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatSqlService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatBotManagerTest.java<br>

<br>

## Overview
![](assets/module5/images/Overview.svg)<br>

In **Module 5**, we will now be integrating with the public Twitch API. Instead of triggering the pipeline through HTTP requests, we will setup a pipeline to stream realtime chat messages from Twitch Chat.
We will use everything we've learned up to this point to accomplish this.

<br>

## Create Kafka Topic: `twitch-chat-events`
1. Navigate to the _**Clusters/twitch-chat-hit-counter/Topics**_ folder
2. Click '+' to add a new kafka topic
3. Input kafka topic configs:
    1. **Topic name**: twitch-chat-events<br>
    2. **Partition Count**: 3<br>
    3. **Replica Count**: 1
4. Select our kafka topic in **_Clusters/twitch-chat-hit-counter/Topics/twitch-chat-events_**
5. Change the **Content Types** for the key and value from **'Byte Array'** → **'String'**, and save by clicking **Update**.

![](assets/module2/create_topic.gif)<br>

<br>

## Create SQL Table: `dev_db.twitch_chat_events`
1. Click on **Schemas** tab
2. Navigate to **dev_db** → **Tables**
3. In the **SQL Editor**, run:
```
CREATE TABLE dev_db.twitch_chat_events (
    event_id VARCHAR(255) PRIMARY KEY,
    event_ts BIGINT,
    channel_id VARCHAR(255),
    channel_name VARCHAR(255),
    user_id VARCHAR(255),
    username VARCHAR(255),
    subscription_months INT,
    subscription_tier INT,
    message TEXT
)
```
![](assets/module3/images/mysqlworkbench_create_table.gif)<br>

<br>

## [Getting Started with the Twitch API <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/docs/api/get-started/)
1. Create a [Twitch.tv <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.twitch.tv/) account
2. Login to [Twitch Developers Console <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console)
3. Under **Applications**, click on [Register Your Application <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console/apps/create).
   - **Name**: `Chat Hit Counter`
   - **OAuth Redirect URLs**: `http://localhost:8080/oauth2/callback`
   - **Category**: `Chat Bot`
   - **Client Type**: `Confidential`

![](assets/module5/images/twitch_createApplication.png)<br>

4. You should now see your application created. Click **Manage**.

5. Create a **New Secret**. Copy both of the **Client ID** and the **Client Secret**. This will be needed to access the Twitch API in our application.

![](assets/module5/images/twitch_keys.png)<br>

<br>

## Exercise 1: Configure Twitch API keys
1. In `twitchApiKey.yml`, set the following fields from setting up a new application from the **Twitch Developer Console**:
   1. **twitch-api.client-id**
   2. **twitch-api.client-secret**
2. In `TwitchConfig.java`, implement:
   1. `public String getTwitchApiClientId()`
   2. `public String getTwitchApiClientSecret()`

This yaml file is already added in **.gitignore**, so your keys _**will not and should not**_ be published to Github.

### Testing
- [ ] Open `TwitchConfigTest.java` ─ already implemented and tests that the loaded TwitchConfig matches the values in `twitch-key.properties`.
- [ ] Remove `@Disabled` in `TwitchConfigTest.java` for method(s): `testTwitchClientKeys()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```

<br>

#





## Exercise 2: Configure `TwitchClient`
In `TwitchConfig.java`, implement `public TwitchClient twitchClient()`.
This should create our instance of Twitch4J's `TwitchClient`, which will be our client proxy to call into Twitch's API.

Twitch4J is an open source Java library that abstracts away a lot of the details when integrating with Twitch's API.

**Requirements:**
1. Initialize the `TwitchClient` object with our Twitch API ClientId, Client Secret.
2. Helix should be enabled
3. Chat should be enabled

```java
TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .build();
```

> [!IMPORTANT]
>
> When integrating with 3P APIs there is _always_ a bit of discovery work involved. Whether it's a publicly exposed API or a private API (direct B2B integration),
> I always spend a large amount of time understanding how to integrate with the API and what data/endpoints are available.
> Best advice is to keep learning by doing and spending time struggling through blocks.








<br>

#

### Exercise 3: Twitch Chat Connection
![](assets/module5/images/ChatOverview.svg)<br>
Now that we have a working OAuth Token cache, we will be mostly focusing on the various Twitch API endpoints we want to integrate with.<br>
It's important to see what products data is even supported publicly and reverse engineer what products you can build out of free data.<br>
In small projects using public API data, you first look at the data available, see what product you can build from it.

Since we want to build a hit counter on different streamers, we will need to learn how to integrate with the Twitch Chat as a [ChatBot <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/docs/chat/send-receive-messages/).<br>

We will focus mainly on receiving Twitch Chat message. The API docs details on different way of connecting to Chats using Webhooks and Websockets,
but that is outside the scope of this project and, tbh, outside of Networking/OS classes I've never once used these in my daily life as a SWE.
To simplify the ease of integration we will leverage a great Java client library that abstracts away much of the lower level details for us. The library is [Twitch4J <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/).<br>

Spend time reading through the Twitch Chat section.


### Task 2: TwitchChatBotManager
Our `TwitchChatBotManager.java` will be a very simple ChatBot in charge of:
- joining/leaving twitch channels
- retrieving all the channels it's connected to
- ingesting in real-time the incoming twitch chat event message

> [!TIP]
> 
> R.T.F.M for [TwitchClient <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/javadoc/com/github/twitch4j/TwitchClient.html)
> to see which methods you should be calling in the various methods you need to implement.

<br>

#

#### Part 1
In `TwitchChatBotManager.java`, implement `public void joinChannel(String channelName)`. This method will allow your ChatBot to join a Twitch stream by the `channelName`.

`TwitchClient` has an API for [TwitchChat <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/javadoc/com/github/twitch4j/chat/TwitchChat.html)
you should use.

#

#### Part 2
In `TwitchChatBotManager.java`, implement `public boolean leaveChannel(String channelName)`. This method will allow our User account to leave a Twitch stream by the `channelName`.

Returns boolean on whether you successfully left the twitch channel.

`TwitchClient` has an API for [TwitchChat <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/javadoc/com/github/twitch4j/chat/TwitchChat.html)
you should use.

#


#### Part 4
In `TwitchChatEvent.java`, implement the data model record.

Twitch4J library has its own event [`ChannelMessageEvent` <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/javadoc/com/github/twitch4j/chat/events/channel/ChannelMessageEvent.html), but this class has a lot of additional metadata fields that we won't need for our project.<br>
We will define our own POJO `TwitchChatEvent.java`, which is a simplified/flattened version of Twitch4J's `ChannelMessageEvent` object.

**Requirements:**
1. `eventId`: (String) eventId for the ChannelMessageEvent
2. `eventTs`: (long) timestamp millis of when the chat message was sent
3. `channelId`: (String) channel id on Twitch
4. `channelName`: (String) channel name on Twitch
5. `userId`: (String) user id on Twitch of the sender of the message
6. `username`: (String) channel name on Twitch
7. `subscriptionMonths`: (int) months the user who is chatting has been subscribed to the channel for
8. `subscriptionTier`: (int) tier level of the user who is chatting has subscribed to (tier1, 2, 3)
9. `message`: (String) message content that the user sent to the chat

#

#### Part 3
In `TwitchChatBotManager.java`, implement `private void handleChatMessage(ChannelMessageEvent event)`. This method will be the main handler for each incoming, real-time Twitch chat message for all the channels that our ChatBot is connected to.

**Requirements**:
- Create a `TwitchChatEvent` from the original `ChannelMessageEvent` Twitch4J passes in
- Simply log the `TwitchChatEvent` to **stdout**

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatBotManager service = new TwitchChatBotManager(...);
> ChannelMessageEvent event = new ChannelMessageEvent(
>     new EventChannel("channelId123", "s0mcs"),
>     ,
>     new EventUser("userId123", "Alice"),
>     "Hi s0m, it's Alice"
> );
> service.handleMessage(channelMessageEvent);
> ```
> **stdout**:<br>
> ```json
> 
> ```







#

#### Part 5
In `TwitchChatBotManager.java`, implement `public void handleMessage(ChannelMessageEvent channelMessageEvent)`.<br>

This method will be the main handler for the incoming real-time twitch chat messages. Twitch4J will pass in the `ChannelMessageEvent`.<br>

Your goal is to simply, for now, log the simplified `TwitchChatEvent` to **stdout**.

**Requirements:**
1. In the `@PostConstructor init()` method, make sure to attach the `handleMessage()` method as the [Event Handler <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/events/channel-message-event#write-chat-to-console) for all incoming chat messages
2. Convert the incoming `ChannelMessageEvent` → `TwitchChatEvent`
3. Log `TwitchChatEvent` to _**stdout**_

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatBotManager service = new TwitchChatBotManager(...);
> service.handleMessage(channelMessageEvent);
> ```
> **stdout**:<br>
> 

### Testing

### Integration Testing












<br>

### Exercise 4: Kafka
![](assets/module5/images/KafkaOverview.svg)<br>

> **Relevant Files:**
> 
> `application.yml`<br>
> `KafkaConfigs.java`<br>
> 

Now that we can connect to Twitch chats successfully, we need to build a Kafka producer/consumer to publish these `TwitchChatEvent` to a new separate kafka topic.

This will look very similar to the end state we had in **Module 2** with the Producer/Consumer on the `greeting-events` kafka topic.<br>
This exercise will be kept short, and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. Stream the incoming chat messages from channel(s) using Twitch4J's `TwitchClient`
2. Publish `TwitchChatEvent` to `twitch-chat-events` topic
3. Consume `TwitchChatEvent` from `twitch-chat-events` topic and log them to **stdout**

The main differences from **Module 2** is the producer trigger logic. In **Module 2**, we needed to manually trigger the `POST /api/kafka/publishGreetingEvent` endpoint via Swagger to invoke our Producer to publish event(s).
In this exercise, our `TwitchChatBotManager.handleMessage()` event handler method is the trigger for the `TwitchChatEventProducer.java`. Once we join a channel and attach the event listener,
the TwitchClient will stream, in real-time, the incoming chat messages that we need to publish to `twitch-chat-events` topics. No more manual trigger, fully automated.

### Task 1: add new kafka topic name to `application.yml`
```yaml
twitch-chat-hit-counter:
  kafka:
    twitch-chat-topic:
      twitch-chat-events
```

### Testing
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented to test the properties above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for the test method(s): `kafkaTwitchChatTopicNameTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

### Task 2: Producer
In `TwitchChatEventProducer.java`, fix the constructor and inject the topic name from `application.yml`.
You'll notice that our initial implementation of `AbstractEventProducer.publish()` from **Module 2** takes care of writing events to a generic topic.
This is where our abstract class pays dividends. We don't need to repeat code and can leverage implementing the main logic in one parent class, and all it's children will benefit.

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatEventProducer producer = new TwitchChatEventProducer(...);
> String eventId = "UUID1";
> TwitchChatEvent event = new TwitchChatEvent(eventId, 1767254400000L, "channelId123", "s0mcs", "userId123", "Alice", 12, 1, "Hi s0m, it's alice");
> boolean output1 = producer.publish(eventId, event);
> 
> String eventId2 = "UUID2";
> TwitchChatEvent event2 = new TwitchChatEvent(eventId2, 1767254400000L, "channelId123", "s0mcs", "userId456", "Bob", null, null, "chat gift me a sub");
> boolean output2 = producer.publish(eventId2, event2);
> ```
> **Output1**: <span style="color:#0000008c">true<br></span>
> **Output2**: <span style="color:#0000008c">true<br></span>

#

### Testing
- [ ] Open `TwitchChatEventProducer.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `TwitchChatEventProducer.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5`
    ```

<br>

#

### Task 3: Consumer

<br>














<br>

### Exercise 5: SQL
![](assets/module5/images/SqlOverview.svg)<br>
Now that we are able to stream Twitch chat events and pub/sub events through our new kafka topic, we need to write the `TwitchChatEvent` to a new separate SQL table.

This will look very similar to the end state we had in **Module 3**. This exercise description will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. In **MySQLWorkbench**, create a new SQL table named `twitch_chat_events`
2. Have `TwitchChatEventConsumer.java` write the `TwitchChatEvent` to the new SQL table
3. Implement `TwitchChatSqlService.java` - should look very similar in terms of layout to `GreetingSqlService.java`

### Example 1:
> ```java
> TwitchChatSqlService service = new TwitchChatSqlService(...);
> 
> TwitchChat event1 = new TwitchChat("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> TwitchChat event2 = new TwitchChat("id2", "Charlie", "David", "Yo.");
> TwitchChat event3 = new TwitchChat("id1", "Echo", "Frank", "Hello there.");
>
> int output1 = greetingSqlService.insert(List.of(event1));
> int output2 = greetingSqlService.insert(List.of(event2));
> int output3 = greetingSqlService.insert(List.of(event3));
> ```
> **Output1**: 1<br>
>
> **Output2**: 1<br>
>
> **Output3**: 0<br>
> **Explanation**: event3.eventId() == "id1" already exists in the table<br>

### Testing

### Integration Testing















<br>

### Exercise 6: Redis Hit Counter
![](assets/module5/images/RedisOverview.svg)<br>
**Goals:**
1. Deduplicate `TwitchChatEvent` so we don't process duplicates using the same `db0` we already have
2. Aggregate chat message count at the minutely grain in a new Redis `db2`

> [!TIP]
>
> In Module 4, we built Redis `db1` to be the Greetings New Feed Database. Since we will be building a Twitch Chat Hit Counter our `db4` will look a little different.
>
> In `db4`, this will be our schema:
> Key (String): `"{channelName}#{minuteBoundaryInMillis}"`
> Value (Long): # of chat messages that fall into the minute bucket (rounded down the nearest minute bucket)





### Task 1: Hook up `TwitchChatEventConsumer` to the `EventDeduperRedisService`
![](assets/module5/images/RedisDeduperOverview.svg)<br>
In `TwitchChatEventConsumer.java`, update `TODO()` to now increment the hit count for the channel.

**Consumer Process Flow:**
1. Check Redis to see if the kafka message key is a duplicate
2. If **isDupeEvent == True**:
    1. Do nothing (skip processing the event)
3. If **isDupeEvent == False**:
    1. Write the event to SQL
    2. Update the Redis DB to add the event's key, so that we can skip this event from being processed if we ever see an event with the same key again.

### Testing
TODO?

<br>

### Task 2: TwitchChatRedisService
![](assets/module5/images/RedisAggOverview.svg)<br>
In `TwitchChatRedisService.java`, implement `public Long incrementMinuteHitCounter(String channelName, long eventTimestampMs)`.

Return the updated count after we increment the key.

If you notice the key template: `"{channelName}#{minuteBoundaryInMillis}"`, you will need to figure out how to mathematically
round the raw event ts to the nearest minute timestamp. Look at the example below for an explanation.

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> TwitchChatRedisService service = new TwitchChatRedisService(redisDao);
> 
> long eventTs1 = 1767254439000; // Thu Jan 01 2026 08:00:39 GMT+0000
> long eventTs2 = 1767254445000; // Thu Jan 01 2026 08:00:45 GMT+0000
>
> long output1 = service.incrementMinuteHitCounter("s0mcs", eventTs1);
> long output2 = service.incrementMinuteHitCounter("s0mcs", eventTs2);
> Map<String, String> output3 = service.getHitCount("s0mcs");
> ```
> **Output1**: 1<br>
> **Explanation**: After we increment the hit count for s0mcs's channel with the timestamp at 1767254439000, the minutely "bucket" it gets rounded down to is 1767254400000. Then it increments that key value.<br>
> ```json
> {
>   "s0mcs#1767254400000": 1
> }
> ```
> 
> **Output2**: 2<br>
> **Explanation**: After we increment the hit count for s0mcs's channel with the timestamp at 1767254445000, the minutely "bucket" it gets rounded down to is 1767254400000. Then it increments that key value.<br>
> ```json
> {
>   "s0mcs#1767254400000": 2
> }
> ```

### Testing

#

In `TwitchChatRedisService.java`, implement `public Map<String, String> getHitCounts(String channelName)`.

Return a Map<String, String> of **ALL** minutely bucket hit counts for a specified channelName by utilizing the `RedisDao.scanKeys()`.

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> TwitchChatRedisService service = new TwitchChatRedisService(redisDao);
> 
> long eventTs1 = 1767254439000; // Thu Jan 01 2026 08:00:39 GMT+0000
> long eventTs2 = 1767254445000; // Thu Jan 01 2026 08:00:45 GMT+0000
> long eventTs3 = 1767254545000; // Thu Jan 01 2026 08:02:25 GMT+0000
>
> service.incrementMinuteHitCounter("s0mcs", eventTs1);
> service.incrementMinuteHitCounter("s0mcs", eventTs2);
> service.incrementMinuteHitCounter("s0mcs", eventTs3);
> Map<String, String> output3 = service.getHitCount("s0mcs");
> ```
> **Output1**: 1<br>
> ```json
> {
>   "s0mcs#1767254400000": 2,
>   "s0mcs#1767254520000": 1
> }
> ```
> **Explanation**:<br>
> eventTs1 = `1767254439000` (2026-01-01 08:**00:39** UTC) → `1767254400000` (2026-01-01 08:**00:00** UTC)<br>
> eventTs2 = `1767254445000` (2026-01-01 08:**00:45** UTC) → `1767254400000` (2026-01-01 08:**00:00** UTC)<br>
> eventTs3 = `1767254545000` (2026-01-01 08:**02:25** UTC) → `1767254520000` (2026-01-01 08:**02:00** UTC)<br>

### Testing

<br>

#

### Task 3: Hook up `TwitchChatEventConsumer` to the `TwitchChatRedisService`
In `TwitchChatEventConsumer.java`, update `TODO()` to now increment the hit count for the channel.

**Consumer Process Flow:**
1. Check Redis to see if the kafka message key is a duplicate
2. If **isDupeEvent == True**:
    1. Do nothing (skip processing the event)
3. If **isDupeEvent == False**:
    1. Write the event to SQL
    2. **(NEW)** Increment the channel's hit count by 1
    3. Update the Redis DB to add the event's key, so that we can skip this event from being processed if we ever see an event with the same key again.

### Testing
TODO?

<br>

#
