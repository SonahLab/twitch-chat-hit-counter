# The Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 2: Kafka
### Recommended Learning Materials
- [Official Apache Kafka Docs <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://kafka.apache.org/intro)
- [Cloud Karafka's Kafka Overview <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.cloudkarafka.com/blog/part1-kafka-for-beginners-what-is-apache-kafka.html)
- [Confluent's Kafka Overview <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/kafka/introduction.html)
- [Official Spring Boot Kafka Docs <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://spring.io/projects/spring-kafka)
- [Baeldung's Spring Kafka Guide <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/spring-kafka)
- [GeeksForGeek's Spring Boot Kafka Guide <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.geeksforgeeks.org/spring-boot-integration-with-kafka/)

### Table of Contents
- [Overview](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#module-2-kafka)
- [Objective](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#objective)
- [Lab Setup](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#lab-setup)
    - [Setup Local Kafka Server](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#setup-local-kafka-server)
    - [Create Kafka Topic](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#create-kafka-topic-greeting-events)
- [File Structure](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#file-structure)
- [Exercise 1: Spring Kafka](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#exercise-1-configure-spring-kafka-within-our-application)
- [Exercise 2: Kafka Producer](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#exercise-2-kafka-producer)
- [Exercise 3: Kafka Consumer](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#exercise-3-single-message-kafka-consumer)
- [Lesson: IO Operations](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#lesson-inputoutput-io-operations)
- [Exercise 4: Batch Kafka Consumer](https://github.com/SonahLab/twitch-chat-hit-counter/blob/main/course-material/MODULE_2.md#exercise-4-batch-message-kafka-consumer)

<br>

## Overview [TODO: Pretty word vomitty right now and scattered. MAJOR CLEANUP needed. Turn this into a streamlined story about Tech today and scale, Case Study, and then how Kafka solves this]
I highly recommend reading through and watching the various **Kafka** and **Spring Kafka** articles/videos above to learn:
1. What Kafka is
2. How Kafka works
3. Why Kafka is used all throughout real-time, streaming Backend systems
4. How to integrate Spring Kafka in a Spring Boot Java application

**Kafka**, or any message task queue, is used ubiquitously in large distributed systems in almost all tech companies dealing with large amounts of real-time streamed data.

**Data is King, Speed is Law.**<br>
Think about any big tech company and the sheer scale of users that use their platform on a daily basis. The expectation is that a service should be **ALWAYS ON**, **ALWAYS AVAILABLE**, and **ALWAYS WORKING**.

Take Youtube as an example. **2.5B monthly active users** (MAU). 1B+ hours of watch time a day.

Just think about the cardinality of data this amounts to in terms of data collection.
Let's attempt to approximate the number of [View](https://views4you.com/blog/how-does-youtube-count-views/#:~:text=A%20Detailed%20Guide,-Written%20by:&text=YouTube%20counts%20views%20when%20a,Read%20on%20to%20find%20out!) impressions as a function of:
```
total_view_impressions = num_hours_of_content_watched * 3600 (seconds/hr) / 30 (seconds/impression)
= 1,000,000,000 (hours) * 3600 (sec/hr) * 1 (impression) / 30 (sec)
= 3,600,000,000,000 seconds * 1 (impression) / 30 (sec)
= 120,000,000,000 VIEW impressions/day for all DAUs
```
This is a back-of-the-envelope (BOTEC) estimate for the scale of viewing impressions in a single day. Think about the scale of data for impressions related to:
- START
- STOP
- PAUSE
- LIKE
- SUBSCRIBE
- COMMENT

There's a metric ton of data that scales with the amount of users on a platform. This data powers all down-funnel teams who's main goal is to:
1. Recommend better content for Users based on their watch history and engagement
   1. Does the algo recommended videos/channels lead to higher subscriptions for Youtubers?
2. Ad Placements right before an exciting moment in a video to build suspense
   1. Does this cliff hanger effect boost engagement by shown by users sitting through the ad or does data show that Users get upset and click out of the video?
3. Content creation optimizations via analysis on content viewing metrics
   1. Do Users like Long-form content?
   2. What's the optimal video length?
   3. At what point do Users disengage?
4. Channel growth metrics
   1. Did a Youtuber do something that caught mass adoption?
   2. What types of videos did the viewer base seem most attracted by?
5. A/B Tests for all different new features
   1. Planning a UI revamp, are users in the treatment group showing higher/lower engagement
   2. Ad Creative formats, are users liking one type of Nike commercial with Michael Jordan over another Nike commercial with LeBron James?
6. ... `Insert ton of other use cases here`

Data is energy to a company, it powers everything. From decision making, revenue growth, and where to invest funding and resources to.
Speed is also very important. _____help me____

### Anecdotal Examples:
- Netflix is a huge advocate for open source. We use Kafka as the go to streaming tool.
- Snapchat is very coupled with Google Cloud (GCP). We mostly used Google Pub/Sub, which is charactertiscally different from Kafka but similar in that it acted as a task queue the way my team used it.

**TL;DR:** Read any Tech blogs from major tech companies and you'll see just how prevalent message log/queues are for streaming a large amount of events.

#

<ins>Kafka</ins> is a distributed, stateful message log, temporarily storing events for decoupled event processing.<br>
<ins>Kafka producers</ins> don't need to wait for any consumers to ack that they've received the data, producers simply write data to kafka topics, which durably store events for a configured period of time.<br>
<ins>Kafka consumers</ins> don't need to interact with the producers at all, they can just poll kafka topics to check if there's new data. Kafka is able to statefully track consumer group ids (offsets), so that consumers can read data while checkpointing where they are in the log of events so events.

Kafka has the ability to let you go back in time and to replay/backfill data by allowing consumers to start reading earlier offsets.

Kafka is not generally known to be used as a DB, think of it more as an **ephemeral holding area** where producers/consumers come and go as they please to write/read packages.

> [!NOTE]
> 
> Also, the higher the TTL, the more costly it becomes on your server maintenance costs to store events for 24 hours vs 365 days.
>
> I normally see TTLs closer to 24 hours during normal operations, and extended up to 1-7 day(s) during operational issues to give teams a bit of buffer to deal with real production issues (time to **root cause**, **debug**, **deploy fixes**, and potentially **backfill** data).


#### Case Study
Imagine we work at Netflix, and support streaming on several playback devices (i.e.: Web, Mobile (iOS/Android), and TV).</br>
For this case study, let's just divide responsibility of teams by device.
- **iOS Team**: collects all user events from all iOS devices.
- **Android Team**: collects all user events from all android devices.
- **Web Team**: collects all user events from all Web devices.
- **TV Team**: collects all user events from TV devices.

Let's call ourselves the **UserEventing Team**, in charge of processing all events from the upstream client teams into **UserEvents** for all of our downstream teams to use as the **SoT** for all user data.

#### Downstream stakeholder(s):
- **Content Recommendation Team**: Analyzes user engagement data to see what a particular User likes to watch so we can better recommend shows/movies in their Top 10 list that are more tailored to their taste (i.e.: Rom Com, Horror, Thriller, Comedy).
- **User Growth Team**: Analyzes user growth with DAU/MAUs, maybe driving new initiatives like push notifications about new releases to encourage stale users to get back on the platform.
- **Ads Team**: Analyzes ad performance to show advertisers improved ROAS on their campaigns, driving increased ad spend to Netflix vs other streaming platforms.

This a just a hypothetical example of how teams power each other with data to help drive business growth by, first, gathering and making sense of the data, and then using the findings to drive new products or initiatives.

**TL;DR:**
In a large scale organization where many teams and systems are interconnected, decoupling as much as possible is essential to scalability. We can set up kafka topics as the intermediary link between teams where the producer will publish/write events to, and consumers will subscribe/read events from as they come in. Zero **direct** dependency between any two teams. **TeamA** doesn't need to wait on **TeamB** to confirm they received the data, and vice versa.

<br>

## Objective
![](assets/module2/images/Module2_Overview.svg)<br>
**Module 1** was a brief overview of HTTP requests as the backbone of one of the most common communication protocol between applications.

**Module 2** is all about Apache Kafka.

**Goals:** <br>
1. Setting up an HTTP request endpoint that will process a User submitted greeting
2. Translating the User inputted greeting into a `GreetingEvent` DTO (Data Transfer Object)
3. PubSub the event through a kafka topic
   1. Producers will write event(s) to a topic
   2. Consumers will read event(s) from a topic and log the event to **stdout**

<br>

## Lab Setup
> [!NOTE]
>
> All of the open source tools we use in this course should be run locally on your own machine. In a real production environment, you could imagine that a cloud provider like Amazon hosts your servers through in their data centers in various regions around the world (i.e.: "us-east-1", "us-west-1"). This, however, incurs a real cost to maintain so running locally-hosted, free servers is great to learn, build, and play around with.
>
> When hosting servers locally, think of your computer as your own personal data center that you don't need to pay a cloud provider, like Amazon, Google, or Microsoft, to host for you.

### Setup Local Kafka Server
Start our local Kafka instance via Docker: [Kafka Quickstart <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://kafka.apache.org/quickstart)<br>
1. Open and login to **Docker Desktop**
2. Get the Docker image:
    ```bash
    docker pull apache/kafka:4.1.0
    ```
3. Start the Kafka Docker container:
    ```bash
    docker run -p 9092:9092 apache/kafka:4.1.0
    ```

<br>

In **Docker**, you should now see the kafka container running locally.
![](assets/module2/images/Docker_kafka.jpg)

<br>

In **Offset Explorer 3**, connect to our Kafka cluster running in Docker.
1. Input cluster configs:
    1. **Cluster name:** twitch-chat-hit-counter
    2. **Bootstrap servers:** localhost:9092
2. Click '**Test**' to verify that OE3 is able to connect to the Docker container
3. Double-click on the newly created cluster to connect to the instance

![](assets/module2/OffsetExplorer3.gif)<br>

<br>

### Create Kafka Topic: `greeting-events`
1. Navigate to the _**Clusters/twitch-chat-hit-counter/Topics**_ folder
2. Click '+' to add a new kafka topic
3. Input kafka topic configs:
    1. **Topic name**: `greeting-events`<br>
    2. **Partition Count**: `3`<br>
    3. **Replica Count**: `1`
4. Select our kafka topic in **_Clusters/twitch-chat-hit-counter/Topics/greeting-events_**
5. Change the **Content Types** for the key and value from **'Byte Array'** → **'String'**, and save by clicking **Update**.

![](assets/module2/create_topic.gif)<br>

<br>

## File Structure
For `Module 2`, the below file structure are all the relevant files needed.

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
<img src="assets/common/class.svg" align="center"/> KafkaConfig.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> AbstractEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventBatchConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> producer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> AbstractEventProducer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventProducer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEvent.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> KafkaRestController.java<br>
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
<img src="assets/common/testClass_newui.svg" align="center"/> KafkaConfigTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingEventBatchConsumerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingEventConsumerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> producer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingEventProducerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> KafkaRestControllerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> PropertiesApplicationTest.java<br>

<br>

### Lesson: Autoconfiguration [TODO: pretty word vomitty right now]
> [!IMPORTANT]
>
> Autoconfiguration is a core aspect of Spring Boot.<br>
> _"Spring Boot’s auto-configuration feature is one of its standout functionalities, allowing developers to build applications with minimal boilerplate code"_
>
> Links:
> - [How Spring Boot Auto-Configuration Works [Medium] <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://medium.com/@AlexanderObregon/how-spring-boot-auto-configuration-works-68f631e03948)<br>
> - [Understanding Auto-Configured Beans [Spring Docs] <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-boot/reference/features/developing-auto-configuration.html#features.developing-auto-configuration.understanding-auto-configured-beans)<br>
> - [Spring Boot Auto-Configuration [GeeksForGeeks] <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.geeksforgeeks.org/java/spring-boot-auto-configuration/)<br>

[KafkaAutoConfiguration.java <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-boot/api/java/org/springframework/boot/autoconfigure/kafka/KafkaAutoConfiguration.html) is the class that autoconfigures Kafka behind the scenes.
Spring Kafka library will build all the relevant Beans on our behalf

Here's a small list of **Spring Kafka** properties I'm **requiring** you to set for this course.

| Property                                                                                                                                                                                                                                                             | Required?    | Role         | Supported/Example Values                                                                                     | Description                                                                                                                                    |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|--------------|--------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|
| `spring.kafka.bootstrap-servers`                                                                                                                                                                                                                                     | **Required** | **Both**     | i.e.: "host:port"                                                                                            | Specifies the Kafka broker(s) to connect to. No connection without it.                                                                         |
| `spring.kafka.consumer.group-id`                                                                                                                                                                                                                                     | **Required** | **Consumer** | i.e.: "applicationName-group-id-0"                                                                           | Defines the consumer group name. Kafka uses this to track message consumption. Multiple consumers with the same group ID share the message load. |
| `spring.kafka.consumer.`[auto-offset-reset <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html#auto-offset-reset)   | Optional     | **Consumer** | `latest` **(default)**<br>`earliest`<br> `none`                                                              | Controls where to start reading if no offset is committed. Default: `latest`.                                                                  |
| `spring.kafka.consumer.`[enable-auto-commit <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html#enable-auto-commit) | Optional     | **Consumer** | `true` **(default)**<br>`false`                                                                              | Whether to auto-commit offsets.<br>Default: `true`. Use `false` for manual acks.                                                               |
| `spring.kafka.consumer.`[key-deserializer <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html#key-deserializer)     | **Required** | **Consumer** | https://kafka.apache.org/21/javadoc/org/apache/kafka/common/serialization/Deserializer.html                  | Converts incoming kafka message key back to object (e.g., `String`).<br>Must match key-serializer on producer.                                 |
| `spring.kafka.consumer.`[value-deserializer <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/consumer-configs.html#value-deserializer) | **Required** | **Consumer** | https://kafka.apache.org/21/javadoc/org/apache/kafka/common/serialization/Deserializer.html                  | Converts incoming kafka message value back to object (e.g., `String`).<br>Must match value-serializer on producer.                                |
| `spring.kafka.listener.`[ack-mode <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-kafka/api/org/springframework/kafka/listener/ContainerProperties.AckMode.html)              | Optional     | **Consumer** | `BATCH` **(default)**<br>`COUNT`<br>`COUNT_TIME`<br>`MANUAL`<br>`MANUAL_IMMEDIATE`<br>`RECORD`<br>`TIME`<br> | The offset commit behavior enumeration.                                                                                                        |
| `spring.kafka.producer.`[key-serializer <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html#key-serializer)         | **Required** | **Producer** | https://kafka.apache.org/0102/javadoc/org/apache/kafka/common/serialization/Serializer.html                  | Converts produced kafka message key to object (e.g., `String`).<br>Must match key-deserializer on consumer.                                       |
| `spring.kafka.producer.`[value-serializer <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html#value-serializer)     | **Required** | **Producer** | https://kafka.apache.org/0102/javadoc/org/apache/kafka/common/serialization/Serializer.html                  | Converts produced kafka message value to object (e.g., `String`).<br>Must match value-deserializer on consumer.                                   |
- List of [Spring Kafka supported fields <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://gist.github.com/geunho/77f3f9a112ea327457353aa407328771)<br>

### Bootstrap Servers (a.k.a Brokers' address)
> [!IMPORTANT]
>
> Couple of good reads on Kafka Consumer Groups:
> - [Kafka Broker <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.cloudkarafka.com/blog/part1-kafka-for-beginners-what-is-apache-kafka.html)
> - [What is a consumer group in Kafka <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://codingharbour.com/apache-kafka/what-is-a-consumer-group-in-kafka/)
> - [Configuring Kafka Consumer Group Ids <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.confluent.io/blog/configuring-apache-kafka-consumer-group-ids/)

![](assets/module2/images/kafka-broker-beginner.png)<br>
`spring.kafka.bootstrap-servers` — a list of "addresses" of the Kafka cluster brokers the application will connect to.

Example:
```yaml
spring:
  kafka:
    bootstrap-servers:
      b-1.clustername1.y15j9n.c2.kafka.us-west-1.amazonaws.com:9098, b-2.clustername1.y15j9n.c2.kafka.us-west-1.amazonaws.com:9098
```

These are sample bootstrap servers if I had used **Amazon's MSK** (Managed Streaming for Apache Kafka) to spin up a fully manager Kafka cluster hosted by AWS.
As long as my application has an AWS IAM role with permissions to connect to my AWS MSK cluster, my application should be able to connect with the brokers at these addresses.


### Consumer Properties
- `spring.kafka.consumer.group-id` — is the unique group-id "checkpoint" for a Consumer.
- `spring.kafka.consumer.auto-offset-reset` — is the behavior on **WHERE** consumers start reading from a Kafka log if no group-id commit exists.<br>
  > ```
    > partition0: [0, 1, 2, ..., 99] (Assume no prior commit exists for our group-id)
    >              │              └─ latest
    >              └─ earliest
    > ```
  > If there's 100 messages in a partition and our consumer has never read from the topic before, these are how reading events is behaved:
  > - `latest` (default): consumer starts reading from latest kafka message (offset=99)
  > - `earliest`: consumer starts reading from the earliest kafka message (offset=0)
  > - `none`: throws exception because consumer has no previous offset
- `spring.kafka.consumer.enable-auto-commit` — If `true`, the consumer’s offset will be periodically committed in the background. If `false`, developers have direct control over offset management.
- `spring.kafka.listener.ack-mode` — offset commit behavior. `MANUAL` gives Consumers responsibility for ACK'ing and updating offsets


### Consumer Group Id
![](assets/module2/images/consumer_offset.png)<br>
![](assets/module2/images/mario_checkpoint.png)<br>


An easy way to think about **group-id**s is with **Super Mario World**.<br>
When a player plays **Super Mario World**, passes a checkpoint, and dies, they respawn at the latest checkpoint.
- The Kafka Consumer (Mario) — the worker that pulls data from the Kafka broker and executes some application logic.
- The Offset (checkpoint gate) — the integer location marker that confirms the last message the Consumer successfully processed.
- The Consumer Group ID — unique name to help brokers track groups of consumers and their offsets.
- The Kafka Broker Group Coordinator (Game Console's Memory) — manages the Offset (checkpoint position) associated with a Consumer Group ID.

The consumers read events from a topic's partitions in order and must send an **Offset Commit Request** back to the Broker so the Group Coordinator knows that a _"Consumer group just finished processing up to point X, let's update their latest Offset checkpoint"_.

### Example 1:
Say we have a single Kafka broker with a single topic split into 2 partitions. Producers have stored 100 messages spread across our partitions:
```
Partition 0: [0, 1, 2, ..., 50]
Partition 1: [0, 1, 2, ..., 50]
```

**Team A's** group-id=`group-id-A` and have 2 consumers (1 consumer per partition).<br>
**Team B's** group-id=`group-id-B` and have 2 consumers (1 consumer per partition).<br>

Assume **Team A** is able to read all 100 messages from the kafka topic, the offsets committed are:
```
Partition 0: [0, 1, 2, ..., 50]
                             └─ group-id-A
Partition 1: [0, 1, 2, ..., 50]
                             └─ group-id-A
```

Assume **Team B** is able to read 30 events from **Partition 0** and 20 events from **Partition 1**, the offsets committed are:
```
Partition 0: [0, 1, 2, ................, 30, 31, ..., 50]
                                          └─ group-id-B
Partition 1: [0, 1, 2, ..., 20, 21, ..., 30, 31, ..., 50]
                             └─ group-id-B
```

Assume **Team B's** consumers batch read up to 100 messages at a time:
- **Consumer 1 (P0)**: Requests data starting from offset=31. The broker responds with the 20 messages remaining (31 through 50).
- **Consumer 2 (P1)**: Requests data starting from offset=21. The broker responds with the 30 messages remaining (21 through 50).
- Both consumers have done some application processes (i.e.: storing the events into some other DB)
- Both consumers get ready to send the **Offset Commit Request** (**ACK**) to the Kafka brokers to update their new checkpoint, but the servers crash.
- Kafka Broker's Group Coordinator was waiting for the **ACK** to update the consumers' positions (P0: `30 → 50`; P1: `20 → 50`), but never receive it.
- **Team B** gets their servers back up and the consumers start consuming from the topic partitions again
- Both consumers look up their last committed offset (P0: `30`; P1: `20`) and pull data starting from those positions, and the brokers respond by sending the same messages prior to the crash:
    - The consumers never committed that they've finished processing the previous events.
    - The Kafka brokers simply fulfill the new fetch Request sent by the consumers starting from the last known good Offset.

The **Super Mario World** equivalent is:
- Mario passes the checkpoint flag at the 50% mark of the map (Consumers send **Offset Commit Request** back to broker)
- Mario is at 99% complete but dies from a Goomba (Consumers pull new events and process events, but an outage occurs)
- At this point, the game's memory only knows that:
  1. Mario hasn't beat the level (Offset was never committed)
  2. Mario was last confirmed state was at the 50% mark (last committed offset of an older state)
- Mario respawns at 50% mark of the map (consumers pull events from the latest committed offsets according to the broker)


The final state for both consumer groups at the point in which (1) **Team A** is fully caught up and (2) **Team B** has just crashed before committing new offset would be:
```
Partition 1: [0, 1, 2, ................, 30, 31, ..., 50]
                                          │            └─ group-id-A
                                          └─ group-id-B
Partition 2: [0, 1, 2, ..., 20, 21, ..., 30, 31, ..., 50]
                             │                         └─ group-id-A
                             └─ group-id-B
```

### Scalability through Decoupling
Producers scale independently of consumers as they write data to Kafka with 0 care of who's reading the events.

Consumers scale independently of producers/other consumers as they read data from Kafka with 0 care of who else is reading from the same topic.

Consumer `group-ids` are unique per consumer group. The fact that **Team A** is fully caught up has 0 impact on **Team B's** ability to catch up. The fact that **Team B's** service crashed has 0 impact on **Team A's** ability to read new incoming events that get queued onto the topic.<br>



### Serializers/Deserializers
![](assets/module2/images/ser_deser.svg)<br>
These properties control how the `key/value` of a Kafka message is serialized onto a topic (on write) and deserialized from a topic (on read).

- `spring.kafka.producer.key-serializer`/`spring.kafka.producer.value-serializer`
- `spring.kafka.consumer.key-deserializer`/`spring.kafka.consumer.value-deserializer`

> [!NOTE]
>
> In cross team environments where shared events are flowing through multiple services (upstream team → your team → downstream team), usually you'd share a universal, version-locked schema in a shared library and align on the SerDeser contract for how events are being handed off and what data types are being used. The three teams would all align that kafka events would be stored using `String (keys)` and `ByteArrays (values)`, so nobody is surprised when getting failures when attempting to read the keys as `Long` values instead of `String` values.

<br>

#

## Exercise 1: Spring Kafka
![](assets/module2/images/connect.png)<br>

In `build.gradle`, I've already imported Spring Kafka library:
```groovy
implementation 'org.springframework.kafka:spring-kafka'
```

### Task 1: Spring Kafka Properties
In `application.yml`, set the **Spring Kafka** properties according to the requirements below. Some properties are marked **OPTIONAL** because the **Spring Kafka** library has default settings on these fields, but I'm requiring specific configurations for this course.

#### Requirements:
1. **bootstrap-server (REQUIRED)**: Set it to the default bootstrap-server [`localhost:9092` <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/spring-projects/spring-boot/blob/2e52c3c35e0bd44ec35dceaeaed1737905a00196/module/spring-boot-kafka/src/main/java/org/springframework/boot/kafka/autoconfigure/KafkaProperties.java#L71)
2. **group-id (REQUIRED)**: Set it to follow this format `{application_name}-group-id-{number}`
3. **auto-offset-reset (OPTIONAL)**: Set it to `earliest`
4. **enable-auto-commit (OPTIONAL)**: Set it to `true` so we have full control in processing/ack'ing each message
5. **key-serializer/value-serializer (REQUIRED)**: Set the correct class path to write kafka messages as **<K (String), V (ByteArray)>** pairs
6. **key-deserializer/value-deserializer (REQUIRED)**: Set the correct class path to read kafka messages as **<K (String), V (ByteArray)>** pairs
7. **ack-mode (OPTIONAL)**: Set it `MANUAL` so we have full control over acking the messages in our Listener consumer functions.

> [!NOTE]
>
> There are a lot more Spring Kafka properties, that even I don't fully know of, that I let Spring Kafka default on my behalf. Usually, you'd only override properties if you had a specific use-case in mind that Spring Kafka doesn't enable by default.

#

### Unit Tests
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented, testing each property against the expected values we want for this course.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest::springKafkaConfigsTest`
- [ ] Open `KafkaConfigTest.java` ─ already implemented, testing the autoconfigured Beans that Spring Kafka injects for us.
- [ ] Remove `@Disabled` in `KafkaConfigTest::testKafkaTemplateConfig` and `KafkaConfigTest::kafkaListenerContainerFactory_beanTest`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

<br>

#

### Task 2: Application Kafka Properties
In `application.yml`, set the property for the `greeting-events` topic name you just created in **Offset Explorer 3**.

The updated `application.yml` should look like this:
```yaml
spring:
  ...

twitch-chat-hit-counter:
  kafka:
    greeting-topic:
      greeting-events
```

#

### Unit Tests
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented, testing each property against the expected values we want for this course.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest::kafkaGreetingTopicNameTest`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

<br>

## Exercise 2: Kafka Producer
![](assets/module2/images/exercise1.svg)<br>

<br>

### Task 1: AbstractEventProducer
![](assets/module2/images/abstractproducer.svg)<br>

Our `AbstractEventProducer.java` is the parent class for writing any type of Event object into a kafka topic.
Core principle of good programming: D.R.Y (Don't Repeat Yourself). All child classes that `extend AbstractEventProducer`,
don't need to worry about the kafka topic write logic once it's defined in the parent.

In `AbstractEventProducer.java`, implement:
- the constructor: `public AbstractEventProducer()`
- `public boolean publish(String key, T event)`. The method expects a `String key` and a generic `T event`, and writes a new message into the kafka topic.<br>

Return the boolean status of the kafka topic write operation.

**Requirements:**
1. Inject the autoconfigured `KafkaTemplate` Bean into the constructor of `AbstractEventProducer.java`.
2. Write the topic message to the `topicName()` Kafka topic. (Assume this method is already implemented)
3. Write the topic message key as a String (should be a NO-OP since our `key` is already a String)
4. Write the topic message value as a ByteArray (you're given a generic POJO event but need to convert it to ByteArray)

> [!TIP]
>
> Get familiar with the [KafkaTemplate <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-kafka/api/org/springframework/kafka/core/KafkaTemplate.html)
> class' source code ─ this class handles all IO operations to/from kafka topics.<br>
> In the source code, you will find this helpful method: `kafkaTemplate.send(String topic, @Nullable V data)`.

<br>

#

### Task 2: GreetingEventProducer
In `GreetingEventProducer.java`, implement:
- the constructor: `public GreetingEventProducer.java`
- `protected String topicName()`

**Requirements**:
1. DI the `KafkaTemplate` into the constructors of all subclass of `AbstractEventProducer.java`. (`GreetingEventProducer.java`, `TwitchChatEventProducer.java`)
2. DI the `greeting-events` from `application.yml` into the constructor of `GreetingEventProducer.java`, and that value should be returned in `protected String topicName()`

<br>

### Example 1:
> **Input**:<br>
> ```java
> GreetingEventProducer producer; // Assume initialized
> 
> String eventId = "UUID1";
> GreetingEvent event = new GreetingEvent(eventId, "Alice", "Bob", "Hi Bob, I'm Alice!");
> boolean output1 = producer.publish(eventId, event);
> 
> String eventId2 = "UUID2";
> GreetingEvent event2 = new GreetingEvent(eventId2, "Charlie", "David", "Yo.");
> boolean output2 = producer.publish(eventId2, event2);
> ```
> **Output1**: true
> 
> **Output2**: true

#

### Integration Tests
- [ ] Open `GreetingEventProducerTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingEventProducerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

<br>

#

### Task 3: Kafka API
![](assets/module2/images/kafkaApi.svg)<br>

In `KafkaRestController.java`, implement:
- `public KafkaRestController()` (constructor)
- `public Boolean produceKafkaGreetingEvent(@RequestParam String sender, @RequestParam String receiver, @RequestParam String message)` (endpoint)

**Requirements:**
1. DI the `GreetingEventProducer` into the constructor
2. Generate a unique `eventId` ([UUID <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/java-uuid)) per greeting request
3. Create a `GreetingEvent` using the: generated `eventId` and the user input parameters
4. Call `GreetingEventProducer.publish()` to handle actual publishing of the kafka message

<br>

### Example 1:
```bash
$ curl -X POST "http://localhost:8080/api/kafka/publishGreetingEvent?sender=Alice&receiver=Bob&message=Hi%20Bob%2C%20I%27m%20Alice%21"
true

$ curl -X POST "http://localhost:8080/api/kafka/publishGreetingEvent?sender=Charlie&receiver=David&message=Yo."
true
````

#

### Unit Tests
- [ ] Open `KafkaRestControllerTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `KafkaRestControllerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

#

### E2E Tests
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `POST /api/kafka/publishGreetingEvent`
- [ ] Check **Offset Explorer 3** to see that your GreetingEvent is actually published to your kafka topic
![](assets/module2/images/kafkaSwagger.png)<br>

<br>

#

## Exercise 3: Kafka Consumer
![](assets/module2/images/exercise2.svg)<br>

#

### Task 1: AbstractEventConsumer
![](assets/module2/images/abstractconsumer.svg)<br>


Our `AbstractEventConsumer.java` is the parent class for reading any type of Event object from a kafka topic.
Core principle of good programming: D.R.Y (Don't Repeat Yourself). All child classes that `extend AbstractEventConsumer`,
don't need to worry about the kafka topic read logic once it's defined in the parent. They will only focus on the core logic that may differ between different consumers.

In `AbstractEventConsumer.java`, implement:
- the constructor: `public AbstractEventConsumer()`
- `protected T convertRecordToEvent(ConsumerRecord<String, byte[]> record)`: this method will convert a generic `ConsumerRecord<String, byte[]> record` value from a `byte[]` → a generic class `T` event.
- `public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack)`: The method will be called once our container picks up a new kafka record.
  - Convert the raw record back to a generic `T event`

**Requirements:**


<br>

#

### Task 2: GreetingEventConsumer
In `GreetingEventConsumer.java`, implement `public void processMessage(ConsumerRecord<String, byte[]> record, Acknowledgment ack)`.

The main goal for now is to simply **log or print** the kafka message that was read from the kafka topic to **stdout** (our application logs).

**Requirements:**
- Add the [@KafkaListener <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-kafka/reference/kafka/receiving-messages/listener-annotation.html) annotation to `processMessage()` method signature
  - `topics` is the only required parameter and should directly inject `twitch-chat-hit-counter.kafka.greeting-topic`

### Example 1:
> **Input**:<br>
> ```java
> GreetingEventConsumer consumer; // Assume initialized.
>
> consumer.processMessage(
>     // ConsumerRecord(String topic, int partition, long offset, K key, V value)
>     new ConsumerRecord(
>         "greeting-events",
>         0,
>         0,
>         "id1",
>         new GreetingEvent("id1", "Alice", "Bob", "Hello, Bob!").getBytes()));
> 
> consumer.processMessage(
>     new ConsumerRecord(
>         "greeting-events",
>         0,
>         1,
>         "id2",
>         new GreetingEvent("id2", "Bob", "Charlie", "Good morning, Charlie!").getBytes()));
> 
> consumer.processMessage(
>     new ConsumerRecord(
>         "greeting-events",
>         0,
>         2,
>         "id3",
>         new GreetingEvent("id3", "Eve", "Frank", "Hi Frank, how are you?").getBytes()));
> ```
> **stdout**:<br>
> ```
> INFO GreetingEventConsumer: Received event=GreetingEvent[eventId=id1, sender=Alice, receiver=Bob, message=Hello, Bob!]
> INFO GreetingEventConsumer: Received event=GreetingEvent[eventId=id2, sender=Bob, receiver=Charlie, message=Good morning, Charlie!]
> INFO GreetingEventConsumer: Received event=GreetingEvent[eventId=id3, sender=Eve, receiver=Frank, message=Hi Frank, how are you?]
> ```

#

### Integration Tests
- [ ] Open `GreetingEventConsumerTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingEventConsumerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

#

### E2E Tests
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `POST /api/kafka/publishGreetingEvent`
- [ ] In **Offset Explorer 3**, validate your `GreetingEvent` is actually published to the kafka topic
- [ ] Verify application **stdout** logs are actually receiving the newly written kafka records

#

## Lesson: Input/Output (IO) Operations
![](assets/module2/images/IO.svg)<br>
_Quick overview on what happens BTS when our application calls read/write IOs to a server.<br>_

### Write IOs:
> → Microservice (**client**) tells Data Center (**server**) to store data to persistent storage.<br>
> → Data Center stores data in disk and responds to the client who made the request with a success/fail response.

### Read IOs:
> → Microservice (**client**) asks Data Center (**server**) to fetch some data.<br>
> → Data Center reads data from disk and responds to the client with the data.

### Case Study
![](assets/module2/images/IOworld.svg)<br>

**Setting:**
- **Team A's** microservice is hosted in **Los Angeles**.
- **Team B's** microservice is hosted in **San Francisco**.
- **Data Center** (Kafka) is hosted in **New York**.

**Roles:**
- **Team A** (producer) writes events to Kafka.
- **Team B** (consumer) reads events from Kafka.

**Problem:**<br>
Every request/response must travel round-trip across the US network for a single call **(CA→NY→CA)**.<br>

**Constraint**<br>
**Team B's** consumers needs to process 1 million events per day.

**Optimization**<br>
- Option 1: Consumers reads individual events
- Option 2: Consumers reads batched events

How many read IOs would need to be issued to read 1M kafka events?<br>
- Option 1: 1,000,000 events / 1 event(s) per read IO = 1,000,000 read IOs
- Option 2: 1,000,000 events / 500 event(s) per read IO = 2,000 read IOs

In other words, instead of travelling between CA/NY **1,000,000x**, we only need to make the trip **2,000x**.

This simple example (pushed to the extreme) highlights the difference your application code can have on performance overall.<br>


## Quiz: Large Scale Distributed Systems
The previous **Case Study** is rarely ever how 

### Question 1: Region Routing (Latency)
Available Amazon Data Centers regions: 
- `us-east-1` (N. Virginia)
- `us-west-2` (Oregon)
- `eu-west-1` (Ireland)
- `ap-northeast-1` (Tokyo)

A company just launched their services in Korea, which region should be used to handle all traffic?<br>
A. `us-east-1`<br>
B. `us-west-2`<br>
C. `eu-west-1`<br>
D. `ap-northeast-1`<br>

[//]: # (Solution: D. Korea is closest to the Tokyo Data Center [locality], so ap-northeast-1 should handle the traffic. AWS does have a Seoul, Korea data center, but thats not in the list of available regions.)

### Question 2: Data Partitioning (Sharding)
Available Amazon Data Centers regions:
- `us-east-1` (N. Virginia): handles all `userId % 4 = 0`
- `us-west-2` (Oregon): handles all `userId % 4 = 1`
- `eu-west-1` (Ireland): handles all `userId % 4 = 2`
- `ap-northeast-1` (Tokyo): handles all `userId % 4 = 3`

Alice lives in Los Angeles and we want all of her events to be directed to `us-west-2`.<br>
What **userId** ensures that her data is partitioned to this region?
A. 1234567890<br>
B. 1234567891<br>
C. 1234567892<br>
D. 1234567893<br>

[//]: # (Solution: D. 1234567893 % 4 == 1)

### Question 3: Cluster Sizing (Capacity Planning)
Available Amazon Data Centers regions:
- `us-east-1` (N. Virginia)
- `us-west-2` (Oregon)
- `eu-west-1` (Ireland)
- `ap-northeast-1` (Tokyo)

A company's user base demographic looks as follows:
- 100M DAUs in the U.S. (80M East coast, 20M West coast)
- 40M DAUs in the U.K.
- 10M DAUs in the Asia Pacific

Which region order best represents the most appropriate cluster size priority (biggest → smallest) to support the user DAUs?<br>
A. `us-east-1` > `us-west-2` > `eu-west-1` > `ap-northeast-1`<br>
B. `us-west-2` > `us-east-1` > `eu-west-1` > `ap-northeast-1`<br>
C. `us-east-1` > `eu-west-1` > `us-west-2` > `ap-northeast-1`<br>
D. `us-east-1` > `eu-west-1` > `ap-northeast-1` > `us-west-2`<br>

[//]: # (Solution: C. 80M [us-east-1] > 40M [eu-west-1] > 20M [us-west-2] > 10M [ap-northeast-1])


#

## Exercise 4: Kafka Batch Consumer
![](assets/module2/images/exercise3.svg)<br>

Spring Kafka autoconfigures `KafkaTemplate` and `ConcurrentKafkaListenerContainerFactory` for us. If Spring sees that the developer doesn't set a field, it usually uses _some_ default value.
If we take a look at the default
[Listener <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/spring-projects/spring-boot/blob/2e52c3c35e0bd44ec35dceaeaed1737905a00196/module/spring-boot-kafka/src/main/java/org/springframework/boot/kafka/autoconfigure/KafkaProperties.java#L971)
object, you can see that the default is set to `SINGLE`, meaning the default `ConcurrentKafkaListenerContainerFactory` will process one event at a time.
We want to create a consumer that will poll and batch multiple records at once.

Spring Kafka autconfiguration is helpful for setting up some default/quick setup, but as an application's use cases evolve and expand,
it's up to developers to tailor Beans further than what autoconfigurations usually allow for. So we will need to setup our own `ConcurrentKafkaListenerContainerFactory` explicitly for the batch consumer.

### Task 1: Configure application.yml
Take a look at the configuration below for `kafkaListenerContainerFactory` and `batchKafkaListenerContainerFactory`.

```yaml
# kafkaListenerContainerFactory (handles pulling Kafka events 1 event at a time)
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: twitch-chat-hit-counter-group-id-X
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.ByteArrayDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.ByteArraySerializer
    listener:
      type: SINGLE # property is not set in our application.yml because it's the default setting used by Spring Kafka
      ack-mode: MANUAL

# batchKafkaListenerContainerFactory (handles pulling Kafka events 1+ events at a time)
spring:
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: twitch-chat-hit-counter-group-id-batch-X # This field should be different from the single consumer group-id
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.ByteArrayDeserializer
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.ByteArraySerializer
    listener:
      type: BATCH # This field should be different from the single consumer listener.type default to enable the batch listener
      ack-mode: MANUAL
```

The only fields that differ between the `kakfaListenerContainerFactory` and `batchKakfaListenerContainerFactory` Beans are:
- `spring.kafka.consumer.group-id`
- `spring.kafka.listener.type`

The `spring.kafka.listener.type` fundamentally changes how the consumer pulls events from Kafka. It allows the consumer to request 1+ events from the Kafka broker instead of just 1 event.<br>
The limitation of Spring Kafka autoconfiguration is that only one basic Bean `ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory` is created on our behalf. In order to create multiple `ConcurrentKafkaListenerContainerFactory`, we need to manually configure them in our application ourselves.

Your job is to create new Spring properties that will be used to create our `batchKakfaListenerContainerFactory` Bean.

**Requirements:**
1. `twitch-chat-hit-counter.kafka.batch-consumer.group-id`: {application_name}-group-id-**batch**-{num}
2. `twitch-chat-hit-counter.kafka.batch-consumer.listener.type`: `BATCH`

> [!NOTE]
> 
> Notice that these custom properties aren't configured under the `spring.kafka.*` prefix. This is because these properties won't be picked up by Spring Kafka's Autoconfiguration logic, these properties will be used by you to manually configure a new type of `ConcurrentKafkaListenerContainerFactory`.

#

### Unit Tests
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented, testing that the two requirements above are met.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest::kafkaBatchConfigsTest`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

#

### Task 2: Kafka BATCH Consumer Bean
Because of Spring Kafka's autoconfiguration we were able to (with no manual set up) use the:
- `KafkaTemplate kafkaTemplate` used by the EventProducer class
- `ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory` used by the Single EventConsumer class

Now we need to create a new `@Bean ConcurrentKafkaListenerContainerFactory` named **batchKafkaListenerContainerFactory** for our Batch EventConsumer class.

In `KafkaConfigs.java`, implement `public ConcurrentKafkaListenerContainerFactory<String, byte[]> batchKafkaListenerContainerFactory()` to be exactly the same as the autoconfigured `kafkaListenerContainerFactory`,
with the two changes being that the batchKafkaListenerContainerFactory's `group-id` and `listener.type` are what's defined in the `application.yml`.

**Requirements:**
- DI the autoconfigured `ConsumerFactory consumerFactory` into the method signature
- DI the batch `group-id` property from `application.yml` into the method signature
- DI the `listener.type` property from `application.yml` into the method signature
- Copy over the consumerFactory's properties to a new map and replace the `group-id` field with the batch `group-id`
- Create a new instance of a `ConsumerFactory` and use the new property map as the base
- Create a new instance of a `ConcurrentKafkaListenerContainerFactory` and `.setConsumerFactory()` with the new `ConsumerFactory`
- Set the `factory.setListener(true/false)` depending on the value of the listenerType
- Set the `factory.getContainerProperties().setAckMode()` to `MANUAL` (as this property is not copied over in the ConsumerFactory properties)

#

### Unit Tests
- [ ] Open `KafkaConfigTest.java` ─ already implemented, testing a new @Bean named `batchKafkaListenerContainerFactory` is properly configured with different `group-id` and `listener.type`
- [ ] Remove `@Disabled` in `KafkaConfigTest::batchKafkaListenerContainerFactory_beanTest`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

#

### Task 3: AbstractEventConsumer
In `AbstractEventConsumer.java`, implement `public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack)`
which accepts a `List<ConsumerRecord<String, byte[]>> records` to process. This will be invoked for any Batch consumers we want to define.

**Requirements:**
1. Process all the raw kafka records and convert them back to a generic `T event` using the already implemented `convertRecordToEvent()`
2. Aggregate all `T event` into a list
3. Log the events to _stdout_
4. **ACK** the `Acknowledgment ack` object

<br>

#

### Task 4: BatchGreetingEventConsumer
In `GreetingEventBatchConsumer.java`, implement:
- `protected EventType eventType()`
- `protected Class<GreetingEvent> eventClass()`
- `public void processMessages(List<ConsumerRecord<String, byte[]>> records, Acknowledgment ack)`: KafkaListener + super() invocation

This task will be nearly identical with the previous `GreetingEventConsumer.java`.

### Example 1:
> **Input**:<br>
> ```java
> GreetingEventConsumer consumer; // Assume initialized.
>
> consumer.processMessages(
>     List.of(
>         // ConsumerRecord(String topic, int partition, long offset, K key, V value)
>         new ConsumerRecord("greeting-events", 0, 0, "id1", new GreetingEvent("id1", "Alice", "Bob", "Hello, Bob!").getBytes()),
>         new ConsumerRecord("greeting-events", 0, 1, "id2", new GreetingEvent("id2", "Bob", "Charlie", "Good morning, Charlie!").getBytes()),
>         new ConsumerRecord("greeting-events", 0, 2, "id3", new GreetingEvent("id3", "Eve", "Frank", "Hi Frank, how are you?").getBytes())
>     ));
> ```
> **std**:<br>
> ```
> INFO GreetingEventBatchConsumer: Received 3 events in the batch, events=[
>     GreetingEvent[eventId=UUID1, sender=Alice, receiver=Bob, message=Hello, Bob!],
>     GreetingEvent[eventId=UUID2, sender=Bob, receiver=Charlie, message=Good morning, Charlie!],
>     GreetingEvent[eventId=UUID3, sender=Eve, receiver=Frank, message=Hi Frank, how are you?]
> ]
> ```

#

### Integration Tests
- [ ] Open `GreetingEventBatchConsumerTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingEventBatchConsumerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module2
    ```

#

### E2E Tests
- [ ] Try to have multiple kafka events already stored in your kafka topic (~3 events per partition is good enough)
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
[//]: # (- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />]&#40;http://localhost:8080/swagger-ui/index.html&#41;<br>)
[//]: # (- [ ] Play around with **Kafka API**: `POST /api/kafka/publishGreetingEvent`)
[//]: # (- [ ] Check **Offset Explorer 3** to see that your GreetingEvent is actually published to our kafka topic)
- [ ] Verify application **stdout** logs to see the consumer is actually processing multiple events in a single `processMessages()` call

#
