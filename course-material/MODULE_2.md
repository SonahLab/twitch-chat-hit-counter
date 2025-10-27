# twitch-chat-hit-counter

### Module 2: Kafka
#### Recommended Learning Materials
- **Kafka**: https://kafka.apache.org/intro
- **Confluent**: https://docs.confluent.io/kafka/introduction.html
- **Spring Boot Kafka**: https://spring.io/projects/spring-kafka
- **Baeldung Spring Kafka:** https://www.baeldung.com/spring-kafka
- Spring Boot Kafka Integration: https://www.geeksforgeeks.org/spring-boot-integration-with-kafka/

#### Overview
I highly recommend reading through the Kafka Introduction above to learn what Kafka is, how it works, and why it’s used ubiquitously in large distributed systems.

Kafka is a distributed message log, where it temporarily holds incoming events.
Imagine we work at Netflix, and support streaming on several playback devices (i.e.: Web, Mobile (iOS/Android), and TV).</br>
For this case study, let's just divide responsibility of teams by device.
- iOS Team: collects all user events from all iOS devices.
- Android Team: collects all user events from all android devices.
- Web Team: collects all user events from all Web devices.
- TV Team: collects all user events from TV devices.
Imagine that we now own 3 products: Google Search, YouTube, and Google Photos.

We are the UserEventing Team, in charge of processing all events into UserEvents for all of our downstream teams to use as the Source of Truth for all user data.

Downstream we might have:
User Insights/Engagement Team: in charge of analyzing user engagement data to
User Identity Team: in charge of building the Day-over-day or Month-over-Month metrics to analyze DAU/MAU over time.
User Bonus Team: in charge of rewarding the power users of our product with bonuses in cash outs or special perks.

In a large scale organization where many teams and systems depend on each other, decoupling is essential to scalability, reliability. We can setup kafka topics as the intermediary link between teams where the producer will publish/write any events, and consumers will subscribe/read any new events as they come in.

A bad example is Team A sends data directly to Team B through some endpoint. What if either side fails in this handoff? What if Team B fails to process an event, will Team A know to re-process an old event?

Think of Kafka as a temporary message queue where events are stored with an expiration date of, usually <24 hours. The longer the TTL on the events, the more cost is incurred in storing and managing older events.

*** My rule of thumb: if you work at a big tech company and are in charge of a realtime event pipeline, and you don’t have mechanisms in place to catch/fix issues within 24 hours before old events are purged, this is a clear engineering diff, and there are big gaps in the system. Kafka isn’t meant for long-term storage, so you shouldn’t have super long TTL’s on the events, git gud and fix your system.


### File Structure
For `Module 2`, the below file structure are all the relevant files needed.
```
twitch-chat-hit-counter/src/
├── main/
│   └── java/com.sonahlab.twitch_chat_hit_counter/
│       ├── config/
│       │   └── KafkaConfig.java
│       ├── kafka/
│       │   ├── consumer/
│       │   │   ├── GreetingEventBatchConsumer.java
│       │   │   └── GreetingEventConsumer.java
│       │   └── producer/
│       │       └── GreetingEventProducer.java
│       ├── model/
│       │   └── GreetingEvent.java
│       └── rest/
│           └── ApplicationRestController.java
├── resources/
│   └── application.yml
└── test/
    └── java/com.sonahlab.twitch_chat_hit_counter/
        └── kafka/
            ├── consumer/
            │   ├── GreetingEventBatchConsumerTest.java
            │   └── GreetingEventConsumerTest.java
            └── producer/
                └── GreetingEventProducerTest.java
```
## Objective
![](assets/module2/images/Module2_Overview.svg)<br>

### Setup Local Kafka Server
Let’s start our local Kafka instance via Docker (https://kafka.apache.org/quickstart)</br>
1. Open and login to Docker Desktop
2. Get the Docker image:
> ```$ docker pull apache/kafka:4.1.0```<br>
3. Start the Kafka Docker container:
> ```$ docker run -p 9092:9092 apache/kafka:4.1.0```<br>

In Docker, you should now see the kafka container running locally.
![](assets/module2/images/Docker_kafka.jpg)<br>

In Offset Explorer 3, connect to our Kafka cluster running in Docker.
1. Input cluster configs:
> **Cluster name:** twitch-chat-hit-counter<br>
> **Bootstrap servers:** localhost:9092
2. Click 'Test' to verify that OE3 is able to connect to the Docker container
3. Double-click on the new created cluster to connect (red circle -> green circle)
![](assets/module2/OffsetExplorer3.gif)<br>

Create our first kafka topic:
1. Navigate to the Clusters/twitch-chat-hit-counter/Topics folder
2. Click '+' to add a new kafka topic
3. Input kafka topic configs:
> **Topic name**: greeting-events<br>
> **Partition Count**: 3<br>
> **Replica Count**: 1
4. Select our kafka topic Clusters/twitch-chat-hit-counter/Topics/greeting-events
5. Change key and value from 'Byte Array' to 'String', and save by clicking 'Update'
![](assets/module2/create_topic.gif)<br>
> <picture>
>   <source media="(prefers-color-scheme: light)" srcset="https://raw.githubusercontent.com/Mqxx/GitHub-Markdown/main/blockquotes/badge/light-theme/info.svg">
>   <img alt="Info" src="https://raw.githubusercontent.com/Mqxx/GitHub-Markdown/main/blockquotes/badge/dark-theme/info.svg">
> </picture><br>
> You can also download the Kafka release and do everything in your Command Line (CLI), but I find Offset Explorer 3 an intuitive, easy-to-use visual tool.

Quick Aside:<br>
Partition Count - number of partitioned logs that make up an entire kafka dataset. Think of this as multiple assembly lines in a factory.
This significantly speeds up the amount of messages to be processed, but too many partitions can be detrimental. Everything in distributed systems, has a tradeoff and is a bit of a balancing act.<br>
Replica Count - number of data copies in a partition. <br>
Example:<br>
Say you _**only**_ have one copy of your data, and Elon Musk comes in and cuts the server wires.
Your application will not be able to read/write to your kafka topic and it's partitions.
If you had 3 copies of your data, and Elon Musk cuts the server wires, you _**ideally**_ have copies in different servers.
If the leader node was in the server that Elon Musk cut, then Kafka would promote any of the follower nodes to act as the new leader.
Main Idea: Never have any system have a single point of failure.

Kafka Topics ARE NOT databases, they are Write Ahead Logs (WAL). They are temporary message queues.
The partition configs default `delete.retention.ms=86400000` (1 day). The longer the data retention, the more costly it is on infra costs for Kafka to store your events.
This data retention depends on the scale of data you are dealing with. If you store 1000 events this is pretty small, if you store 100s of billions of events you will want a lower retention policy.
In a big tech company, you want a retention policy that gives you enough time to re-process errors and not lose events if something catastrophic happens.


### Exercise 1: Implement a Kafka Message Producer
![](assets/module2/images/exercise1.svg)<br>
`application.yml` ─ our service's configuration file<br>
`KafkaConfig.java` ─ Configuration class to store our Kafka related Beans</br>
`GreetingEvent.java` ─ the POJO class that encapsulates a simple greeting object.</br> 
`GreetingEventProducer.java` ─ the class that publishes `GreetingEvent` objects to our dedicated kafka topic `greeting_topic` 

Hopefully, you've spent some time to brush up on Kafka and the role of the producer. If not, go and do that now.</br>

Implement the `public boolean publish(String messageId, GreetingEvent event) {}` method, which takes a kafka `messageId` and a `GreetingEvent`, writes a new kafka message into the kafka topic.</br>
Return the status of the kafka topic write operation.</br>

#### Example 1:
> **Input**:<br> <span style="color:#0000008c">
> messageId = "UUID1",<br>
> greetingEvent = new GreetingEvent(eventId="UUID1", sender="Alice", receiver="Bob", message="Hi Bob")<br></span>
> **Output**: <span style="color:#0000008c">true<br></span>
> **Explanation**:
```java
GreetingEventProducer producer = new GreetingEventProducer();
String eventId = "UUID1";
GreetingEvent event = new GreetingEvent(eventId, "Alice", "Bob", "Hi Bob, I'm Alice!");
boolean result = producer.publish(eventId, event);
// 'result' boolean depends on successfully publishing a kafka message onto the topic. 
```

#### Quick Lesson on AutoConfiguration in Spring Boot
**Spring Boot Auto-configuration lifecycle** is a core mechanism that allows Spring Boot to automatically configure beans and settings based on the classpath, properties, and conditions.<br>
Auto-configuration in Spring Boot is driven by the @EnableAutoConfiguration (or @SpringBootApplication, which includes it) annotation and follows a well-defined lifecycle:
> 1. Application startup: 
> The Spring Boot application starts via SpringApplication.run(...).
     @SpringBootApplication triggers: @ComponentScan @EnableAutoConfiguration

#### Task 1: Configure application.yml
| Property                                   | Required?    | Role         | Supported/Example Values                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | Description                                                                                                                                      |
|--------------------------------------------|--------------|--------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------|
| `spring.kafka.bootstrap-servers`           | **Required** | **Both**     | i.e.: "host:port"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | Specifies the Kafka broker(s) to connect to. No connection without it.                                                                           |
| `spring.kafka.consumer.group-id`           | **Required** | **Consumer** | i.e.: "applicationName-group-id-0"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    | Defines the consumer group name. Kafka uses this to track message consumption. Multiple consumers with the same group ID share the message load. |
| `spring.kafka.consumer.auto-offset-reset`  | Optional     | **Consumer** | `latest` **(default)**<br>`earliest`<br> `none`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Controls where to start reading if no offset is committed. Default: `latest`.                                                                    |
| `spring.kafka.consumer.enable-auto-commit` | Optional     | **Consumer** | `true` **(default)**<br>`false`                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       | Whether to auto-commit offsets. Default: `true`. Use `false` for manual acks.                                                                    |
| `spring.kafka.consumer.key-deserializer`   | **Required** | **Consumer** | <ul><li>`org.apache.kafka.common.serialization.StringDeserializer`</li><li>`org.apache.kafka.common.serialization.IntegerDeserializer`</li><li>`org.apache.kafka.common.serialization.LongDeserializer`</li><li>`org.apache.kafka.common.serialization.DoubleDeserializer`</li><li>`org.apache.kafka.common.serialization.FloatDeserializer`</li><li>`org.apache.kafka.common.serialization.ByteArrayDeserializer`</li><li>`org.apache.kafka.common.serialization.UUIDDeserializer`</li><li>`org.springframework.kafka.support.serializer.JsonDeserializer`</li></ul> | Converts incoming kafka message key back to object (e.g., `String`). Must match key-serializer on producer.                                      |
| `spring.kafka.consumer.value-deserializer` | **Required** | **Consumer** | Same list as `key-deserializer` (most common: `StringDeserializer`, `JsonDeserializer`, `ByteArrayDeserializer`).<br> Converts incoming value bytes back to object. Must match serializer.                                                                                                                                                                                                                                                                                                                                                                            | Converts incoming kafka message value back to object (e.g., `String`). Must match value-serializer on producer.                                  |
| `spring.kafka.producer.key-serializer`     | **Required** | **Producer** | <ul><li>`org.apache.kafka.common.serialization.StringSerializer`</li><li>`org.apache.kafka.common.serialization.IntegerSerializer`</li><li>`org.apache.kafka.common.serialization.LongSerializer`</li><li>`org.apache.kafka.common.serialization.DoubleSerializer`</li><li>`org.apache.kafka.common.serialization.FloatSerializer`</li><li>`org.apache.kafka.common.serialization.ByteArraySerializer`</li><li>`org.apache.kafka.common.serialization.UUIDSerializer`</li><li>`org.springframework.kafka.support.serializer.JsonSerializer`</li></ul>                 | Converts produced kafka message key to object (e.g., `String`). Must match key-deserializer on consumer.                                         |
| `spring.kafka.producer.value-serializer`   | **Required** | **Producer** | Same list as `key-serializer` (most common: `StringSerializer`, `JsonSerializer`, `ByteArraySerializer`).<br>                                                                                                                                                                                                                                                                                                                                                                                                                                                         | Converts produced kafka message value to object (e.g., `String`). Must match value-deserializer on consumer.                                     |
- List of Spring Kafka supported fields: https://gist.github.com/geunho/77f3f9a112ea327457353aa407328771
![](assets/module2/images/ser_deser.svg)<br>

Requirements:
1. We want to connect to our local Docker Kafka server (bootstrap-servers) (HINT: localhost:9092)
2. We want the group-id to follow this format {application_name}-group-id-0 (group-id)
3. We want our consumers to start at the earliest kafka offset for that group-id (auto-offset-reset)
4. We want to control the way our consumers process and acknowledge each message (enable-auto-commit)
5. We want to write kafka messages as key/value pairs of String/ByteArray. This means the kafka message key will be stored as a String object, and the same message value will be stored as ByteArray. (key-deserializer/value-deserializer)
6. We want to read kafka messages as key/value pairs of String/ByteArray. This means the kafka message key will be read as a String object, and the same message value will be read as ByteArray (key-serializer/value-serializer)

#### Testing
`ProfileApplicationTest.java` ─ already implemented
1. Remove `@Disabled` in `ProfileApplicationTest.java` for the test method: `testDefaultProfile_kafkaConfigs()`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module2`

#### Task 2: Create the Producer Beans
`KafkaConfig.java` - we need to create two @Bean objects for ProducerFactory and KafkaTemplate, which will be used to Auto-configure Spring Kafka at runtime.
`org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration`

Spring Kafka Auto-configuration flow at runtime:
```
spring-kafka on classpath
↓
bootstrap-servers is set
↓
KafkaProperties gathers config (from application.yml)
↓
KafkaAutoConfiguration creates:
├─ ProducerFactory → DefaultKafkaProducerFactory
├─ ConsumerFactory → DefaultKafkaConsumerFactory
├─ KafkaTemplate → KafkaTemplate
└─ ListenerContainerFactory → ConcurrentKafkaListenerContainerFactory

./gradlew bootRun
   ↓
JVM Classpath
   ├── twitch-chat-hit-counter.jar
   ├── spring-boot.jar
   └── spring-kafka.jar   ← DETECTED!
        └── META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
             └── KafkaAutoConfiguration
                  ↓
            Auto-configures Kafka beans
```

Requirements:
1. Create a Spring Bean for our service's ProducerFactory
2. Create a Spring Bean for our service's KafkaTemplate

For the ProducerFactory constructor, pass in the bootstrap-servers, key-serializer, value-serializer configs from the application.yml.
For the KafkaTemplate constructor, pass in the ProducerFactory bean that we've just created.
When our application runs, Spring Boot will figure out the dependency injection (DI) based on the relationship hierarchy.
> the application.yml configs will be loaded.
> KafkaConfig will build the ProducerFactory bean based on the loaded application.yml configs
> KafkaConfig will build the KafkaTemplate bean based on the created ProducerFactory object.

#### Testing
`KafkaConfigTest.java` ─ already implemented
1. Remove `@Disabled` in `KafkaConfigTest.java` for the test method(s): `testProducerFactoryConfig()` and `testKafkaTemplateConfig()`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module2`


#### Task 3: Implement GreetingEventProducer.java
Implement `public boolean publish(String key, GreetingEvent event) {}`.

Get familiar with the KafkaTemplate class source code, here you will find the method: `kafkaTemplate.send(String topic, @Nullable V data)`.
You will need to create a constructor for this GreetingEventProducer and DI the KafkaTemplate object in `KafkaConfig.java` to successfully write the event to our kafka topic.

#### Testing
`GreetingEventProducerTest.java` ─ already implemented
1. Remove `@Disabled` in `GreetingEventProducerTest.java`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module2`

#### Task 4: Implement ApplicationRestController.java
Implement `GET /api/publishGreetingEvent` endpoint to trigger `GreetingEventProducer.publish(...);`

Requirements:
1. Generate a UUID, which will act as the kafka messageId as well as the GreetingEvent's eventId.
2. Call GreetingEventProducer.publish() to handle actual publishing of the kafka message.

#### Testing
1. Run: ./gradlew bootRun
2. Go to: `http://localhost:8080/swagger-ui/index.html`
3. Run the endpoint for `/api/publishGreetingEvent` with any inputs
4. Check Offset Explorer 3 to see that your GreetingEvent is actually published to our kafka topic. 

### Exercise 2: Implement a Kafka Message Consumer
![](assets/module2/images/exercise2.svg)<br>
`application.yml` ─ our service's configuration file</br>
`KafkaConfig.java` ─ Configuration class to store our Kafka related Beans</br>
`GreetingEvent.java` ─ the POJO class that encapsulates a simple greeting object.</br>
`GreetingEventConsumer.java` ─ the class that subscribes to our `greeting_event` kafka topics to read `GreetingEvent` objects

#### Task 1: Implement KafkaConfig.java
- We need to now create some Beans for the Kafka consumers, `ConsumerFactory` and `ConcurrentKafkaListenerContainerFactory`


### Lesson: Input/Output (IO) Operations
![](assets/module2/images/IO.svg)<br>
Quick Overview on what happens when we call read/write IOs on a server.<br>
Writes:
> -> Microservice (client) tells Data Center (server) to store persistent data.<br>
> -> Data Center stores data in server and responds to the client who made the request with a success/fail response.

Reads:
> -> Microservice (client) tells Data Center (server) to give back some data.<br>
> -> Data Center reads data in server and responds to the client who made the request with the data in store.

Let's assume all clients are running in California and that the server is running in New York.
Meaning each request/response travels across the US networks.
Say Team A is writing data to the Kafka server in NY.
Say Team B is reading data from the Kafka server in NY.
Now say we need to support high QPS traffic.
Say the Engineer on Team B only has 1 available consumer.
Instead of having the consumer read 1 kafka message at a time travelling from CA->NY->CA, we should optimize the # of IO calls we dispatch to the Kafka Server.
Meaning, we should have each consumer read a group of N kafka messages at a time (i.e.: 500 messages in a single batch).

How many read IOs would need to be issued to read 1M kafka events?
Instead of 1,000,000 read IOs, we can optimize this for 1M / 500 = 2,000 read IOs.

This simple example shows the benefit of introducing batch operations in your application, either on read/writes.
This brings us to the next exercise, creating a Batch Consumer.

### Exercise 3: Implement BATCH Kafka Message Consumer
![](assets/module2/images/exercise3.svg)<br>
