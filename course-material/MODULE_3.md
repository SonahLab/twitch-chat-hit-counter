# twitch-chat-hit-counter

### Module 3: SQL
#### Recommended Learning Materials
- https://dev.mysql.com/downloads/workbench/
#### Overview
Now we’re foraying into the land of databases.

SQL Databases, a.k.a. Relational DataBase Management System (RDBMS), a.k.a. the OG database.
SQL DBs will always be used no matter where you go, even with the rise of popularity in NoSQL databases. The foundation of SQL is so well established, many query engines are heavily incentivized to support SQL to query data.

The amount of SQL queries I run on a daily basis is very, very high.
At Snapchat, I would run SQL queries via:
- MySQLWorkbench on top of our Google Cloud SQL tables
- Google BigQuery on top of our Google Bigtables (NoSQL)

At Netflix, I run SQL queries via:
- SparkSQL on top of our Apache Iceberg tables
- CQL (looks pretty much like SQL) on top of our Apache Cassandra tables

This course won’t dive too deeply into SQL queries, we will instead be focusing on setting up and populating our own SQL db.

### File Structure
For `Module 3`, the below file structure are all the relevant files needed.
```
twitch-chat-hit-counter/src/
├── main/
│   └── java/com.sonahlab.twitch_chat_hit_counter/
│       ├── kafka/
│       │   └── consumer/
│       │       ├── GreetingEventBatchConsumer.java
│       │       └── GreetingEventConsumer.java
│       ├── model/
│       │   └── GreetingEvent.java
│       ├── rest/
│       │   └── ApplicationRestController.java
│       └── sql/
│           └── GreetingSqlService.java
├── resources/
│   └── application.yml
└── test/
    └── java/com.sonahlab.twitch_chat_hit_counter/
        ├── kafka/
        │   └── consumer/
        │       ├── GreetingEventBatchConsumerTest.java
        │       └── GreetingEventConsumerTest.java
        └── sql/
            └── GreetingSqlServiceTest.java
```
## Objective
![](course-material/assets/module3/images/Module3_Overview.svg)<br>
In Module 2, we were consuming the kafka messages and just logging them to stdout. This module goes a step further and stores these GreetingEvents in a SQL DB.

## Setup Local MySQL Server
Let's start our local MySQL Server via Docker:
1. Open and login to Docker Desktop
2. Start the MySQL Docker container:
```shell
docker run \
    --name twitch-chat-hit-counter-mysql-dev \
    -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
    -e MYSQL_DATABASE=dev_db \
    -p 3306:3306 \
    -d mysql:latest
```
In Docker, you should now see the MySQL container running locally. We now have both a Kafka server and a MySQL server.
![](course-material/assets/module3/images/Docker.jpg)<br>

Open MySQLWorkbench and connect to our MySQL instance running in Docker.
1. Click Add Connection (circle with a '+' sign)<br>
2. Input **twitch-chat-hit-counter-mysql-dev** as the connection name<br>
3. Click 'Test Connection' to verify that MySQLWorkbench is able to connect to the Docker container
4. Click 'OK' to finish setting up the connection
5. Connect to the SQL instance
![](course-material/assets/module3/images/mysqlworkbench_setup.jpg)<br>

Let's Create our first SQL table.
1. Click on 'Schemas' tab
2. Navigate to dev_db -> Tables (should be empty)
3. Now run this SQL Query in the editor:
```
CREATE TABLE dev_db.greeting_events (
    event_id VARCHAR(255) PRIMARY KEY,
    sender VARCHAR(255),
    receiver VARCHAR(255),
    message TEXT
)
```
![](course-material/assets/module3/images/mysqlworkbench_create_table.gif)<br>

### Exercise 1: Implement GreetingSqlService.insert()
![](course-material/assets/module3/images/exercise1.svg)<br>

#### Example 1:
> **Input**:<br>
> <span style="color:#0000008c">GreetingSqlService greetingSqlService = new GreetingSqlService(...);<br></span>
> <span style="color:#0000008c">GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");<br></span>
> <span style="color:#0000008c">GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");<br></span>
> <span style="color:#0000008c">GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");<br></span>
> 
> <span style="color:#0000008c">**int output1 = greetingSqlService.insert(event1);** // should return 1<br></span>
> <span style="color:#0000008c">**int output2 = greetingSqlService.insert(event2);** // should return 1<br></span>
> <span style="color:#0000008c">**int output3 = greetingSqlService.insert(event3);** // should return 0 (event3.eventId == "id1" which already exists in the table)<br></span>
>
> **Output1**:<br>
> <span style="color:#0000008c">1<br></span>
> **Output2**:<br>
> <span style="color:#0000008c">1<br></span>
> **Output3**:<br>
> <span style="color:#0000008c">0<br></span>

Add our SQL table name to our application.yml properties
```yaml
twitch-chat-hit-counter:
  sql:
    greeting-table: greeting_events
```

Implement the `public int insert(GreetingEvent event) {}`. This method should write a `GreetingEvent` and insert it into the SQL table we've set up.
Return the number of successful events written in the table (should be 0 or 1).
Requirements:
1. Events should be deduplicated
   2. if an event with the same eventId already exists in the SQL table, it will be ignored.
2. Constructor should pass in the table name from application.yml
3. Constructor should pass in the auto-configured `JdbcTemplate` that is used to handle the read/write IOs to SQL

Here's a hint (without giving away the entire answer) for how the SQL insert statement should look:
```
INSERT INTO {TABLE_NAME} (field₁, ..., fieldₙ)
VALUES (?, ..., ?)
ON {SOME_FILTER}
```
Here's a very helpful link on how to ensure writes to SQL, deduplicate events: https://stackoverflow.com/questions/14383503/on-duplicate-key-update-same-as-insert

#### Testing
`GreetingSqlServiceTest.java` ─ already implemented
1. Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method: `insertTest()`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module3`

### Exercise 2: Implement GreetingSqlService.queryAllEvents()
Implement `public List<GreetingEvent> queryAllEvents() {}`. This method should read all the events in our SQL table.
Return a list of `GreetingEvent`.

#### Example 1:
> **Input**:<br>
> <span style="color:#0000008c">GreetingSqlService greetingSqlService = new GreetingSqlService(...);<br></span>
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

**Output1**:<br>
```json
[
    {
        "eventId": "id1",
        "sender": "Alice",
        "receiver": "Bob",
        "message": "Hi Bob, I'm Alice!"
    }
]
```
**Output2**:<br>
```json
[
  {
    "eventId": "id1",
    "sender": "Alice",
    "receiver": "Bob",
    "message": "Hi Bob, I'm Alice!"
    },
  {
    "eventId": "id2",
    "sender": "Charlie",
    "receiver": "David",
    "message": "Yo."
  }
]
```
**Output3**:<br>
```json
[
  {
    "eventId": "id1",
    "sender": "Alice",
    "receiver": "Bob",
    "message": "Hi Bob, I'm Alice!"
    },
  {
    "eventId": "id2",
    "sender": "Charlie",
    "receiver": "David",
    "message": "Yo."
  }
]
```

This task is quite trivial if you know about SQL Query (https://www.w3schools.com/sql/sql_syntax.asp), you will now utilize the `JdbcTemplate` to query and parse the response back to a List\<GreetingEvent>.

Task 2:<br>
Now hook up the `GreetingEventService.java` to our `GreetingEventConsumer.java`. Everytime the event is consumed, we will call the `GreetingSqlService.insert()` method to persist that event into the SQL DB.

Task 3:<br>
Now implement the `GET /api/queryGreetingEventsFromSQL` API endpoint in `ApplicationRestController.java`.
We want our HTTP Controller to call the GreetingSqlService to fetch all the records in our SQL table.

#### Testing
`GreetingSqlServiceTest.java` ─ already implemented
1. Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method: `queryTest()`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module3`

### Exercise 3: Implement GreetingSqlService.insertBatch()
Implement `public int insertBatch(List<GreetingEvent> events) {}`.

Similar to our lesson in Module 2 where we want to optimize the # of IO calls to our server, instead of writing 1M events into our SQL DB using 1M write calls, we will try to write in a batch of events to make less round trips to the SQL server.

