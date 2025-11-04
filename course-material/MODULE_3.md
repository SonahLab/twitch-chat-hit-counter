# Practical Backend Engineer
## Twitch Chat Hit Counter

## Module 3: SQL

[//]: # (TODO)
#### Recommended Learning Materials
- https://dev.mysql.com/downloads/workbench/

## Overview
[//]: # (TODO)
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

<br>

## File Structure
For `Module 3`, the below file structure are all the relevant files needed.

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
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventBatchConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingEvent.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> SqlRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingSqlService.java<br>
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
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingEventBatchConsumerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingEventConsumerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> SqlRestControllerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingSqlServiceTest.java<br>


<br>

## Objective
![](assets/module3/images/Module3_Overview.svg)<br>
In **Module 2**, we set up an E2E producer/consumer on our first `greeting-events` Kafka topic. The `GreetingEventConsumer.java` processes kafka messages but only logs the event to _stdout_.<br>

In **Module 3**, we will go one step further and **persist/store** these `GreetingEvent` in a SQL DB.

<br>

## Setup Local MySQL Server
Start our local MySQL Server via Docker:
1. Open and login to **Docker Desktop**
2. Start the MySQL Docker container:
```shell
docker run \
    --name twitch-chat-hit-counter-mysql-dev \
    -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
    -e MYSQL_DATABASE=dev_db \
    -p 3306:3306 \
    -d mysql:latest
```

<br>

In **Docker**, you should now see the MySQL container running locally. We now have both a Kafka server and a MySQL server.
![](assets/module3/images/Docker.jpg)

<br>

Open **MySQLWorkbench** and connect to the MySQL instance running in Docker.
1. Click Add Connection (circle with a '+' sign)<br>
2. Input **twitch-chat-hit-counter-mysql-dev** as the connection name<br>
3. Click '**Test Connection**' to verify that MySQLWorkbench is able to connect to the SQL server
4. Click '**OK**' to finish setting up the connection
5. Connect to the SQL instance

![](assets/module3/images/mysqlworkbench_setup.jpg)<br>

<br>

## Create your first SQL table
1. Click on **Schemas** tab
2. Navigate to **dev_db** → **Tables**
3. In the **SQL Editor**, run:
```
CREATE TABLE dev_db.greeting_events (
    event_id VARCHAR(255) PRIMARY KEY,
    sender VARCHAR(255),
    receiver VARCHAR(255),
    message TEXT
)
```
![](assets/module3/images/mysqlworkbench_create_table.gif)<br>

<br>

## Exercise 1: Single Record SQL Writer
![](assets/module3/images/exercise1.svg)<br>

<br>

### Task 1: Set SQL table name Spring property
Add our SQL table name to our `application.yml` properties
```yaml
twitch-chat-hit-counter:
  sql:
    greeting-table: greeting_events
```

#

### Testing
- [ ] Open `ProfileApplicationTest.java` ─ already implemented to test the property above.
- [ ] Remove `@Disabled` in `ProfileApplicationTest.java` for the test method(s): `testDefaultProfile_sql_greetingTableName()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
```

#

### Task 2: SQL `GreetingEvent` Writer
In `GreetingSqlService.java`, implement `public int insert(GreetingEvent event)`. This method should write a single `GreetingEvent` into the SQL table we've set up.

Return the number of successful event(s) written in the table (should be 0 or 1).

**Requirements:**
1. Events should be deduplicated.<br>
   If an event with the same **event_id (PK)** already exists in the SQL table, we should ignore them.
2. Constructor should pass in the table name define in `application.yml` from the previous task.
3. Constructor should pass in the auto-configured [JdbcTemplate <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/spring-jdbc-jdbctemplate) that is used to handle the read/write IOs to SQL<br>
> [!NOTE]
>
> If you take a look at `application.yml`, I have already implemented the MySQL configs for you that our application will use to autoconfigure or **JdbcTemplate** @Bean with.

> [!TIP]
>
> Helpful link on how to deduplicate events in the SQL Query ([Stack Overflow <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://stackoverflow.com/questions/14383503/on-duplicate-key-update-same-as-insert))
>
> ```
> INSERT INTO {TABLE_NAME} (field₁, ..., fieldₙ)
> VALUES (?, ..., ?)
> ON {SOME_FILTER}
> ```

### Example 1:
> **Input:**<br>
> ```java
> GreetingSqlService greetingSqlService = new GreetingSqlService(...);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");
> GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");
>
> int output1 = greetingSqlService.insert(event1);
> int output2 = greetingSqlService.insert(event2);
> int output3 = greetingSqlService.insert(event3);
> ```
> **Output1**: 1<br>
>
> **Output2**: 1<br>
>
> **Output3**: 0<br>
> **Explanation**: event3.eventId() == "id1" already exists in the table<br>

#

### Testing
- [ ] Open `GreetingSqlServiceTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method(s): `insertTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
```

<br>

#

### Task 3: Hook up the Kafka Consumer to use the SQL writer
In `GreetingEventConsumer.java` (from **Module 2**), integrate with `GreetingSqlService.java`.<br>
Everytime an event is read from Kafka, we will need to call `GreetingSqlService.insert()` to persist that event into the SQL DB.

You will need to inject the `GreetingSqlService` component into the `GreetingEventConsumer` constructor.

### Testing
- [ ] TODO (do i need to update the test file for the consumer?)

#

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `/api/kafka/publishGreetingEvent`
- [ ] In **MySQLWorkbench**, verify that the `GreetingEvent` triggered via **Swagger** is written into SQL by querying:
```
SELECT *
FROM greeting_events
```

<br>

## Exercise 2: SQL Api
![](assets/module3/images/exercise2.svg)<br>

### Task 1: Implement GreetingSqlService.queryAllEvents()
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
> **Output2**:<br>
> ```json
> [
>   {
>     "eventId": "id1",
>     "sender": "Alice",
>     "receiver": "Bob",
>     "message": "Hi Bob, I'm Alice!"
>     },
>   {
>     "eventId": "id2",
>     "sender": "Charlie",
>     "receiver": "David",
>     "message": "Yo."
>   }
> ]
> ```
> **Output3**:<br>
> ```json
> [
>   {
>     "eventId": "id1",
>     "sender": "Alice",
>     "receiver": "Bob",
>     "message": "Hi Bob, I'm Alice!"
>     },
>   {
>     "eventId": "id2",
>     "sender": "Charlie",
>     "receiver": "David",
>     "message": "Yo."
>   }
> ]
> ```

This task is quite trivial if you know about [SQL Query <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/sql/sql_syntax.asp).<br>
The query will be identical to the SQL query used in MySQLWorkbench that was used to read all events in our `greeting_events` table.

The only tricky part here is that you will now use the `JdbcTemplate` to execte this query and parse the returned records back to a List\<GreetingEvent>.

### Task 2: Hook up the `SqlRestController` to the `GreetingSqlService`
Now implement the `GET /api/sql/queryGreetingEventsFromSQL` API endpoint in `SqlRestController.java`.<br>
We want our HTTP Controller to call the `GreetingSqlService.queryAllEvents()` to fetch all the records in our SQL table.

You will need to inject the `GreetingSqlService` component into the `SqlRestController` constructor.

#### Testing
- [ ] Open `SqlRestControllerTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `SqlRestControllerTest.java`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
```

### Exercise 3: Implement Batch Writes
![](assets/module3/images/exercise3.svg)<br>

### Task 1: Create new SQL table
In **MySQLWorkbench**, create a new SQL table `batch_greeting_events` with the same schema as the first sql table:
```
CREATE TABLE dev_db.batch_greeting_events (
    event_id VARCHAR(255) PRIMARY KEY,
    sender VARCHAR(255),
    receiver VARCHAR(255),
    message TEXT
)
```

#

### Task 2: Configure application.yml
```yaml
twitch-chat-hit-counter:
  sql:
    greeting-table-batch: batch_greeting_events
```

### Testing
- [ ] Open `ProfileApplicationTest.java` ─ already implemented to test the property above.
- [ ] Remove `@Disabled` in `ProfileApplicationTest.java` for the test method(s): `testDefaultProfile_sql_batchGreetingTableName()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
```

#

### Task 2: Implement GreetingSqlService.insertBatch()
Implement `public int insertBatch(List<GreetingEvent> events)`.

Return the number of successful events that were written to the SQL table.

Similar to the **Lesson: Input/Output (IO) Operations** section in **Module 2**, we will optimize the # of IO calls to our SQL server by reducing write IOs. Instead of sending `.insert()` calls for (i.e: 1M events) into our SQL DB by issuing 1M write calls, we will write events in batches to make less round trips to the SQL server.


**Reminder:** pass in the `twitch-chat-hit-counter.sql.greeting-table-batch` property into the constructor.<br>

This method will look very similar to the `insert()` method, the only differences are:
1. The Batch SQL insert statement will pack more events than did the previous method.
2. The `insertBatch()` method should write to `batch_greeting_events` SQL table and not the `greeting_events` table that is used by the `insert()` method.

> [!TIP]
>
> Here's a brief overview of how to implement batch insert statements with [JdbcTemplate <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-framework/reference/data-access/jdbc/advanced.html) in Spring Boot.

### Example 1:
> **Input**:<br>
> <span style="color:#0000008c">GreetingSqlService greetingSqlService = new GreetingSqlService(...);<br></span>
> <span style="color:#0000008c">GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");<br></span>
> <span style="color:#0000008c">GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");<br></span>
> <span style="color:#0000008c">GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");<br></span>
>
> <span style="color:#0000008c">**int output = greetingSqlService.insertBatch(List.of(event1, event2, event3));** // should return 3<br></span>
>
> **Output**: <span style="color:#0000008c">3<br></span>
> **Explanation**: <span style="color:#0000008c">Our batch insert SQL statement should write event1 and event2, but event3 has the same "id1" primary key as event1 which should have already been written to in the same batch, so we won't write in event3. 2 successful writes out of 3 batched events.<br></span>

#

### Testing
- [ ] Open `GreetingSqlServiceTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method: `insertBatchTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
```

#

### Task 3: Hook up the Batch Kafka Consumer to use the Batch SQL writer
Make sure to also hook up our `GreetingEventBatchConsumer.java` (**Module 2**) to call `GreetingSqlService.insertBatch()` method.
Everytime a batch of events are read from Kafka, we will call the `GreetingSqlService.insertBatch()` method to persist this same batch of events into the SQL DB.

You will need to inject the `GreetingSqlService` component into the `GreetingEventConsumer` constructor.

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `/api/kafka/publishGreetingEvent`
- [ ] Check **Offset Explorer 3** to see that your GreetingEvent is actually published to our kafka topic
- [ ] Verify application **stdout** logs are actually receiving the newly written kafka records
- [ ] In **MySQLWorkbench**, verify that the Greeting you triggered via **Swagger** is written into the SQL table by running:
```
SELECT *
FROM batch_greeting_events
```
