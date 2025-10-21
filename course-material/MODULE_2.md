# twitch-chat-hit-counter

> <picture>
>   <source media="(prefers-color-scheme: light)" srcset="https://raw.githubusercontent.com/Mqxx/GitHub-Markdown/main/blockquotes/badge/light-theme/info.svg">
>   <img alt="Info" src="https://raw.githubusercontent.com/Mqxx/GitHub-Markdown/main/blockquotes/badge/dark-theme/info.svg">
> </picture><br>
>
> Info

### Module 2: Kafka
#### Additional Learning Materials
- Kafka: https://kafka.apache.org/intro
- Kafka Integration in Spring Boot: https://www.geeksforgeeks.org/spring-boot-integration-with-kafka/

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
### Exercise 0: Setup Local Kafka Server
Let’s start our local Kafka instance via Docker (https://kafka.apache.org/quickstart)</br>
> **Using JVM Based Apache Kafka Docker Image**<br>
> Get the Docker image:<br>
> ```$ docker pull apache/kafka:4.1.0```<br><br>
> Start the Kafka Docker container:<br>
> ```$ docker run -p 9092:9092 apache/kafka:4.1.0```<br>

In Docker, you should now see the kafka container running locally.


### Exercise 1: Implement a Kafka Message Producer
`application.yml` ─ our service's configuration file</br> 
`KafkaConfig.java` ─ Configuration class to store our Kafka related Beans</br>
`GreetingEvent.java` ─ the POJO class that encapsulates a simple greeting object.</br> 
`GreetingEventProducer.java` ─ the class that publishes `GreetingEvent` objects to our dedicated kafka topic `greeting_topic` 

Hopefully, you've spent some time to brush up on Kafka and the role of the producer. If not, go and do that now.</br>

Implement the `public boolean publish(String messageId, GreetingEvent event) {}` method, which takes a kafka `messageId` and a `GreetingEvent`, writes a new kafka message into the kafka topic.</br>
Return the status of the kafka topic write operation.</br>

#### Example 1:
> Input: `messageId = "UUID1"`, `greetingEvent = new GreetingEvent(eventId="UUID1", sender="Alice", receiver="Bob", message="Hi Bob")`</br>
> Output: `"true"`
> Explanation
> producer = new GreetingEventProducer();
> String eventId = "UUID1";
> GreetingEvent event = new GreetingEvent(eventId, "Alice", "Bob", "Hi Bob, I'm Alice!");
> boolean result = producer.publish(eventId, event); // the producer should a boolean true if it successfullys publishes a kafka message onto the topics, false otherwise. 

### Exercise 2: Implement a Kafka Message Consumer
`application.yml` ─ our service's configuration file</br>
`KafkaConfig.java` ─ Configuration class to store our Kafka related Beans</br>
`GreetingEvent.java` ─ the POJO class that encapsulates a simple greeting object.</br>
`GreetingEventConsumer.java` ─ the class that subscribes to our `greeting_event` kafka topics to read `GreetingEvent` objects


