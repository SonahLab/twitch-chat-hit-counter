# The Practical Backend Engineer
## Twitch Chat Hit Counter

## Module 3: SQL

[//]: # (TODO)
#### Recommended Learning Materials
- https://dev.mysql.com/downloads/workbench/

## Overview
SQL Databases are Relational DataBase Management System (RDBMS), a.k.a. the OG database.<br>
SQL DBs will always be used no matter where you go, even with the rise of popularity in NoSQL databases. The foundation of SQL is so well established, many query engines are heavily incentivized to support SQL (Structured Query Language) to query data.

Python, Spark, and SQL skew towards _"Data Engineers"_, but as a "Backend Software Engineer" I still use SQL daily. My responsibilities range from building real-time eventing services to, lately, more offline big data processing using SparkSQL to aggregate data to Apache Iceberg + exporting impressions to different 3P Measurement vendors.

Takeaway: I use SQL (_A LOT_) on a daily basis, and it's safe to say SQL isn't going anywhere.

**Netflix**:
- SparkSQL for most of our data pipelines
- SQL to query our Apache Iceberg tables (usually when investigating issues with data)

**Snapchat:**
- **Google Cloud SQL** tables for monthly financial data to send to **Payments Team**
- **Google BigQuery** on top of our **Google Bigtables** (NoSQL)

> [!WARNING]
> 
> I'm assuming you know the basics of SQL and there are tons of resources online that I won't regurgitate for this course.

<br>

## Objective
![](assets/module3/images/Module3_Overview.svg)<br>
In **Module 2**, you should have now set up:
- a HTTP Rest Controller to send a simple Greeting to our application and convert the parameters into a `GreetingEvent` DTO.
- a Kafka producer to write/send the `GreetingEvent` to your local `greeting-events` topic.
- a Kafka consumer to read/receive the `GreetingEvent` from your local `greeting-events` topic and log the event to **stdout**.

In **Module 3**, you will go one step further and **persist/store** these `GreetingEvent` in a SQL DB.

Goals:
- Implement the ability to write a single `GreetingEvent` to a SQL table
- Implement the ability to write a batch of `GreetingEvent` to a SQL table
- Implement the ability to read all events from a SQL table + add a new Rest Controller to trigger the query

<br>


## Lab Setup
### Setup Local MySQL Server
Start your local MySQL Server via Docker:
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
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> SqlConfig.java<br>
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
<img src="assets/common/class.svg" align="center"/> AbstractSqlService.java<br>
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
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> SqlConfigTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> SqlRestControllerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingSqlServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> PropertiesApplicationTest.java<br>

> - `application.yml` — Spring Boot application properties<br>
> - `SqlConfig.java` — Configuration class to define all of our SQL related configs<br>
> - `GreetingEventBatchConsumer.java` — Kafka consumer on `greeting-events` topic reading events in batches<br>
> - `GreetingEventConsumer.java` — Kafka consumer on `greeting-events` topic reading 1 event at a time<br>
> - `GreetingEvent.java` — DTO to model a greeting event<br>
> - `SqlRestController.java` — REST controller to handle our service's SQL query endpoints:<br>
>   - `/api/sql/queryGreetingEvents`: queries all events from a SQL table<br>
> - `AbstractSqlService.java` — Abstract parent class to handle reading/writing generic `T event` from and to SQL tables<br>
> - `GreetingSqlService.java` — Class to handle reading/writing `GreetingEvent event` from and to SQL tables<br><br>
> 
> - `SqlConfigTest.java` — Test class to validate logic after implementing `SqlConfig.java`<br>
> - `SqlRestControllerTest.java` — Test class to validate logic after implementing `SqlRestController.java`<br>
> - `GreetingSqlServiceTest.java` — Test class to validate logic after implementing `GreetingSqlService.java`<br>
> - `PropertiesApplicationTest.java` — Test class to validate logic after implementing `application.yml`<br>

<br>

## Spring JDBC Autoconfiguration
![](assets/module3/images/connection.png)<br>

In `build.gradle`, I've already imported Spring Boot JDBC:
```groovy
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
implementation 'mysql:mysql-connector-java:8.0.33'
```
In `application.yml`, add the properties Spring needs to autoconfigure the `JdbcTemplate` so that your application can connect to the MySQL DB.
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/dev_db
    username: root
    password: ""
```
https://spring.io/guides/gs/accessing-data-mysql

<br>

The main @Bean that this Spring library autoconfigures for us is the:
[JdbcTemplate <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/spring-projects/spring-boot/blob/2e52c3c35e0bd44ec35dceaeaed1737905a00196/module/spring-boot-jdbc/src/main/java/org/springframework/boot/jdbc/autoconfigure/JdbcTemplateAutoConfiguration.java).<br>
- https://github.com/spring-projects/spring-boot/blob/2e52c3c35e0bd44ec35dceaeaed1737905a00196/module/spring-boot-jdbc/src/main/java/org/springframework/boot/jdbc/autoconfigure/JdbcProperties.java
- https://github.com/spring-projects/spring-boot/blob/2e52c3c35e0bd44ec35dceaeaed1737905a00196/module/spring-boot-jdbc/src/main/java/org/springframework/boot/jdbc/autoconfigure/DataSourceProperties.java

<br>


## Lesson: SQL Refresher
> [!TIP]
>
> Spend time learning about different SQL queries.
> - [W3School's MySQL Playground <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/mysql/mysql_editor.asp): play around with static, public, online datasets through an online editor.

In **MySQLWorkbench**, create a playground `employees` SQL table.
```
CREATE TABLE dev_db.employees (
    employee_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birthday VARCHAR(255),
    photo VARCHAR(255),
    notes VARCHAR(255)
)
```

#

### Task 1: SQL Writes
We've just hired **Alice** and **Bob** to our new startup, lets make sure they're employee information is stored in our SQL table, `dev_db.employees`.

Write a SQL statement that will upload these 2 employees' information into SQL:
- **Alice** employee record: `employee_id=1, first_name="Alice", last_name="Apple", birthday="1800-12-25", photo="EmpId1.pic", notes="Software Engineer"`
- **Bob** employee record: `employee_id=2, first_name="Bob", last_name="Banana", birthday="2026-01-01", photo="EmpId2.pic", notes="Very young employee"`

Verify the data is actually being written into the table as intended:
`SELECT * FROM employees;`

[//]: # (Solution:)
[//]: # (INSERT INTO employees &#40;employee_id, first_name, last_name, birthday, photo, notes&#41;)
[//]: # (VALUES)
[//]: # (  &#40;1, "Alice", "Apple", "1800-12-25", "EmpId1.pic", "Software Engineer"&#41;)
[//]: # (  &#40;2, "Bob", "Banana", "2026-01-01", "EmpId2.pic", "Very young employee"&#41;;)

#

### Task 2: SQL Overwrites
We accidentally uploaded Alice's birthday incorrectly, instead of `1800-12-25` it should be set to `2000-12-25`.

Write a SQL statement that will overwrite Alice's entire employee record with the fixed birthday. 

[//]: # (Solution:)
[//]: # (REPLACE INTO employees &#40;employee_id, first_name, last_name, birthday, photo, notes&#41;)
[//]: # (VALUES)
[//]: # (  &#40;1, "Alice", "Apple", "2000-12-25", "EmpId1.pic", "Software Engineer"&#41;;)

> [!NOTE]
>
> If we use `INSERT INTO ...` and try to overwrite an already existing row with the same **PK (Primary Key)**, SQL will throw an exception. Find another way to overwrite an existing row.

#

### Task 3: SQL Deduplication
Imagine that Alice's employee hire event was sent to us again: `employee_id=1, first_name="Alice", last_name="Apple", birthday="2000-12-25", photo="EmpId1.pic", notes="Software Engineer"`

Write a SQL statement that ignores rows from being written into the `employees` SQL table with a duplicate key (any row with that same pre-existing PK in the table).

[//]: # (Solution:)
[//]: # (INSERT IGNORE INTO employees &#40;employee_id, first_name, last_name, birthday, photo, notes&#41;)
[//]: # (VALUES)
[//]: # (  &#40;1, "Alice", "Apple", "2000-12-25", "EmpId1.pic", "Software Engineer"&#41;;)

#

### Quiz:
#### Question 1: Backfills
Situation: Your upstream team has been sending your team **corrupt** data for some time, but your team has already processed and stored all the events to a SQL table.
The upstream team notifies your team that they've just gone back and corrected the data. You go ahead and kick off a backfill to re-process all the days affected with the correct data.

For this scenario, what is the **best** (most efficient) SQL logic to use?
```
INSERT INTO table employees (...)
values (...);
```
```
-- CORRECT SOLUTION
REPLACE INTO table employees (...)
values (...);
```
```
INSERT IGNORE INTO table employees (...)
values (...);
```
```
None of the above
```
```
Explanation:
A. This is incorrect because the corrupt data and the backfilled, correct data have the same keys, so the `INSERT INTO...` will fail ungracefully.
B. This is correct because this SQL command will correctly overwrite the old corrupt data rows with the new corrected data.
C. This is incorrect because it is ignoring the newly processed, correct data since the keys already exist, this is the opposite of what we want.
```

#### Question 2: Duplicates
Situation: Your upstream team has been sending your team **correct** data for some time, and your team has already processed all the data and written the events to a SQL table.
But they just deployed a bug in their code and, for every event, they are not just sending you that single event but also a copy of the exact same event payload (duplicates).

For this scenario, what is the **best** (most efficient) SQL logic to use?
```
INSERT INTO table employees (...)
values (...);
```
```
REPLACE INTO table employees (...)
values (...);
```
```
-- CORRECT SOLUTION
INSERT IGNORE INTO table employees (...)
values (...);
```
```
None of the above
```
Explanation:
```
A. This is incorrect because the 2 duplicate events (original + copy) have the same keys, so the `INSERT INTO...` will fail ungracefully.
B. This is technically correct because this SQL command will always overwrite rows that have the same primary keys. Because the events are identical (dupes), it doesn't matter the order in which the events get written into SQL, the end state is the same. It isn't the best solution because you are writing 1 event 2 times making it less efficient than Option C. Behind the scenes, every 'REPLACE INTO ...' command — on PK collision — issues a 'DELETE' + 'INSERT' operation.
C. This is correct because it will write in the first event and ignore the second duplicate event, making it the most efficient, working command.
```

> [!IMPORTANT]
> 
> Always think about how your systems handle the **"Unhappy Path"** when things go wrong. Large systems are made up of many microservices built by many engineers/teams all working together.
> You build systems for the happy path when everything is going well, but what happens to your system when unpredictable issues come up?
> 
> In Software Engineering, it's never a matter of **IF** things go wrong, but **WHEN**. And **WHEN** things go wrong, you should have built systems to appropriately handle any failures. Software is human-engineered and we all make mistakes. As for networks and servers, those instances are unpredictable and uncontrollable when they become unavailable.
> 
> Most of my growth as a Software Engineer — technically speaking — from my New Grad days to a Senior SWE is the ability to think about all the possible ways (edge cases) my code can lead to unwanted outcomes in the future and to build in guardrails to prevent as many unexpected things happening within the system.
> By front-loading a lot of this worry upfront in the Design phase, you quickly learn the importance of trying to understand how the system works E2E and how each component fits into some system diagram. You're able to catch pitfalls earlier by collaborating and collecting feedback on all failure scenarios.
> - What do we do when our upstream team introduces a bug and sends us bad data?
> - Is there a way we can backfill data quickly?
> - How will our system scale in a thundering herd situation when event loads spike outside the normal bounds of our usual day-to-day operations?
> - If there is a system outage, when our services get back "online", is our application optimally built to catch up with the backpressured data when we scale up?

<br>

#

## Exercise 1: Single Record SQL Writer
![](assets/module3/images/exercise1.svg)<br>

<br>

### Exercise 1 Task 1: Spring Application Property
Add our SQL table name to our `application.yml` properties.
```yaml
twitch-chat-hit-counter:
  sql:
    greeting-table: greeting_events
```

#

### Unit Tests
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented to test the property above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for the test method(s): `sqlGreetingTableNameTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

#

### Exercise 1 Task 2: AbstractSqlService
![](assets/module3/images/abstract.svg)<br>


`AbstractSqlService.java` is the generic parent class to handle:
1. Writing any type of Event into SQL tables
2. Reading any type of Event from SQL tables

All child classes that `... extend AbstractSqlService` through [**Inheritance** <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.geeksforgeeks.org/java/inheritance-in-java/) can re-use the logic defined in their parent class.<br>
Core principle of good programming: **D.R.Y (Don't Repeat Yourself)**. When we get to later parts of this course you'll see the benefits of creating this `AbstractSqlService`.

**Implement:**
- constructor `public AbstractSqlService()`
  - DI the `JdbcTemplate` autoconfigured bean
- `public int insert(List<T> events)`: flexible method to handle writing a single event or multiple events into SQL. Return the number of successful event(s) written in the table (should be 0 or 1).

#### Exercise 1 Task 2 Part I: Constructor
In `AbstractSqlService.java`, implement `public AbstractSqlService()` constructor.

**Requirements:**
- Add `JdbcTemplate` object to the method signature that is expected anytime a child class implementation creates this class

#### Exercise 1 Task 2 Part II: insert()
In `AbstractSqlService.java`, implement `public int insert(List<T> events)`. This method takes in a generic `List<T>` of events and attempts to write them into a SQL table.

Return an int for the count of successfully written records into SQL.

**Requirements:**
- Define a class-level static String `SQL_WRITE_TEMPLATE`. This should be a templatized SQL command string that will perform writes into a SQL table.
Hint: [SQL Insert <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/sql/sql_insert.asp), look at the SQL template: 
```
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
```
Notice in `AbstractSqlService.java` I've provided abstract methods (which will be implemented by each child class later) for: `String sqlTableName()`, `List<String> columns()`, `Object[] values(T event)`.
[Java String `format()` <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/java/ref_string_format.asp) should help you create a templatized String that will be filled in later.
1. Make sure the SQL command will gracefully handle duplicate events (Hint: think back to the quiz from earlier). Your SQL command should:
  1. prevent duplicates from being written into the SQL table
  2. not throw exceptions when met with a duplicate.
2. Fill in the `SQL_WRITE_TEMPLATE` string in a [PreparedStatement <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.geeksforgeeks.org/java/how-to-use-preparedstatement-in-java/)-compatible format.
  - The **table_name** placeholder should be replaced by a String returned by calling `sqlTableName()`
  - The **columns** placeholder should be replaced by a comma-separated list of String returned by calling `columns()` (i.e.: `(column1, column2, ..., columnN)`)
  - The **values** placeholder should be replaced by a comma-separated list of `?` characters respecting the same length returned by calling `columns()` (i.e.: `(?₁, ?₂, ..., ?ₙ)`)

> [!TIP]
> 
> Read through this guide on [JdbcTemplate <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/spring-jdbc-jdbctemplate#the-jdbctemplate-and-running-queries) if you get stuck.

<br>

#

[//]: # (### Quiz &#40;Exercise 1 Task 2 Part II: insert&#40;&#41;&#41;)

[//]: # (#### Question 1: Based on Requirement #1 for "Exercise 1 Task 2 Part II", what method will you use to gracefully handling duplicate writes?)

[//]: # (```)

[//]: # (A. `INSERT INTO table_name &#40;columns...&#41; VALUES &#40;values...&#41;)

[//]: # (B. `INSERT IGNORE INTO table_name &#40;columns...&#41; VALUES &#40;values...&#41;)

[//]: # (C. `REPLACE INTO table_name &#40;columns...&#41; VALUES &#40;values...&#41;)

[//]: # (```)

[//]: # (#### Question 2: What is the correct output of calling `String.format&#40;"My name is %s %s. My dogs' names are: %s.", "Jane", "Doe", String.join&#40;",", List.of&#40;"Scooby-Doo", "Pluto", "Snoopy""&#41;&#41;&#41;;`)

[//]: # (```)

[//]: # (A. "My name is Jane Doe. My dogs' names are: Scooby-Doo, Pluto, Snoopy.)

[//]: # (B. "My name is Jane Doe. My dogs' names are: [Scooby-Doo, Pluto, Snoopy])

[//]: # (C. "My name is Jane Doe. My dogs' names are: Scooby-Doo, Pluto, Snoopy)

[//]: # (D. "My name is Jane Doe. My dogs' names are: [Scooby-Doo, Pluto, Snoopy].)

[//]: # (```)

[//]: # (```)

[//]: # (A. Correct answer. Correctly replaces the first and second `%s` placeholder with "Jane" and "Doe", respectively. Also correctly joins the List<String> by the `,` delimeter.)

[//]: # (B. Incorrect answer. Correctly replaces the first and second `%s` placeholder with "Jane" and "Doe", respectively. Correctly joins the List<String> by the `,` delimeter. Incorrectly adds the "[]" brackets. Incorrectly misses the period at the end that's in the original string being formatted.)

[//]: # (C. Incorrect answer. Correctly replaces the first and second `%s` placeholder with "Jane" and "Doe", respectively. Correctly joins the List<String> by the `,` delimeter. Incorrectly misses the period at the end that's in the original string being formatted.)

[//]: # (D. Incorrect answer. Correctly replaces the first and second `%s` placeholder with "Jane" and "Doe", respectively. Correctly joins the List<String> by the `,` delimeter. Incorrectly adds the "[]" brackets. Correctly has the period at the end of the original string being formatted.)

[//]: # (```)

[//]: # (#### Question 3: Based on Requirement #3, what are the BEST combination of methods to call in this code block?)

[//]: # (```java)

[//]: # (public int insert&#40;List<T> events&#41; {)

[//]: # (    // ...)

[//]: # (    if &#40;events.size&#40;&#41; == 1&#41; {)

[//]: # (        jdbcTemplate.{method1})

[//]: # (    } else {)

[//]: # (        jdbcTemplate.{method2})

[//]: # (    })

[//]: # (    // ...)

[//]: # (})

[//]: # (```)

[//]: # (```)

[//]: # (A. method1 = update&#40;&#41;; method2 = update&#40;&#41;;)

[//]: # (B. method1 = batchUpdate&#40;&#41;; method2 = update&#40;&#41;;)

[//]: # (C. method1 = batchUpdate&#40;&#41;; method2 = batchUpdate&#40;&#41;;)

[//]: # (D. method1 = update&#40;&#41;; method2 = batchUpdate&#40;&#41;;)

[//]: # (```)

[//]: # (```)

[//]: # (A. Incorrect answer. Method2 will fail because if we are trying to update 5 events, update&#40;&#41; will throw an exception. update&#40;&#41; -- Issue a single SQL update operation &#40;such as an insert, update or delete statement&#41;.)

[//]: # (B. Incorrect answer. Method1 won't fail but it's not the most optimal method we should use for updating 1 event. Method2 will fail because if we are trying to update 5 events, update&#40;&#41; will throw an exception. update&#40;&#41; -- Issue a single SQL update operation &#40;such as an insert, update or delete statement&#41;. batchUpdate&#40;&#41; -- Execute multiple batches using the supplied SQL statement with the collect of supplied arguments.)

[//]: # (C. Incorrect answer. Method1 won't fail but it's not the most optimal method we should use for updating 1 event.)

[//]: # (D. Correct answer. Method1=update&#40;&#41; will succeed and is the most optimal when updating a single insert statement. Method2=batchUpdate&#40;&#41; will succeed and is the correct method to call for a batch of insert statements.)

[//]: # (```)

[//]: # (#### Question 4: Based on Requirement #4, what is the correct `update&#40;...&#41;` and `batchUpdate&#40;...&#41;` method signature to use GIVEN that you will implement the `Object[] values&#40;T event&#41;` method -- which will return an array of raw event values.)

[//]: # (![]&#40;assets/module3/images/updateOverloaded.png&#41;<br>)

[//]: # (Example:)

[//]: # (```java)

[//]: # (public class ChildClass extends AbstractSqlService<GreetingEvent> {)

[//]: # (    // ...)

[//]: # ()
[//]: # (    @Override)

[//]: # (    protected abstract Object[] values&#40;GreetingEvent event&#41; {)

[//]: # (        return Object[] {{event.eventId&#40;&#41;, event.sender&#40;&#41;, event.receiver&#40;&#41;, event.message&#40;&#41;}};)

[//]: # (    })

[//]: # ()
[//]: # (    // ...)

[//]: # (})

[//]: # (GreetingEvent event = new GreetingEvent&#40;"id1", "Alice", "Bob", "Hi Bob, I'm Alice!"&#41;;)

[//]: # (ChildClass child = new ChildClass&#40;&#41;;)

[//]: # (Object[] values = child.values&#40;&#41;; // OUTPUT: ["id1", "Alice", "Bob", "Hi Bob, I'm Alice!"])

[//]: # (```)

[//]: # (```)

[//]: # (A. int update&#40;String sql&#41;)

[//]: # (B. int update&#40;String sql, @Nullable Object @Nullable ... args&#41;)

[//]: # (C. int update&#40;String sql, @Nullable Object @Nullable ... args, int[] argTypes&#41;)

[//]: # (D. int update&#40;String sql, @Nullable PreparedStatementSetter pss&#41;)

[//]: # (E. int update&#40;PreparedStatementCreator psc&#41;)

[//]: # (F. protected int update&#40;PreparedStatementCreator psc, @Nullable PreparedStatementSetter pss&#41;)

[//]: # (H. int update&#40;PreparedStatementCreator psc, KeyHolder generatedKeyHolder&#41;)

[//]: # (```)

[//]: # (```)

[//]: # (A. Incorrect answer. This method expects a fully formed SQL update statement, but our Requirement #2 explicitly wants our SQL statement to template the &#40;values&#41; as &#40;?₁, ?₂, ..., ?ₙ&#41;.)

[//]: # (B. Correct answer. This method expects a partially formed SQL update statement + explicit ...args which you will pass in using the abstract `Object[] values&#40;T event&#41;` method.)

[//]: # (C. Incorrect answer. This method is similar to the correct choice, but with the addition of an additional `int[] argTypes` argument. You *COULD* use this method by passing in each argument and it's type or just rely on the Java Object Types for each arg passed in, which is what we prefer.)

[//]: # (D. Incorrect answer. You could use `PreparedStatement` but the other choice is more simple and aligned with direct utilization of the abstract `Object[] values&#40;T event&#41;` method.)

[//]: # (E. Incorrect answer. You could use `PreparedStatement` but the other choice is more simple and aligned with direct utilization of the abstract `Object[] values&#40;T event&#41;` method.)

[//]: # (F. Incorrect answer. You could use `PreparedStatement` but the other choice is more simple and aligned with direct utilization of the abstract `Object[] values&#40;T event&#41;` method.)

[//]: # (```)

[//]: # (Here is the video for the actual Walkthrough of Exercise 1 Task 2 Part II: insert&#40;&#41;)

<br>

#

### Exercise 1 Task 3: GreetingEventSqlService (Child Class)
In `AbstractSqlService.java`, you should have implemented the Constructor and the `insert()` method. But there are still many `abstract` tagged methods, which are left up to any children classes to implement.

In `GreetingSqlService.java`, implement:
- `public GreetingSqlService()`
- `protected String sqlTableName()`
- `protected List<String> columns()`
- `protected Object[] values(GreetingEvent event)`
- ~~`protected GreetingEvent parseEventFromResultSet(ResultSet rs)`~~ (Ignore for now)

#

### Exercise 1 Task 3 Part I: Constructor
In `GreetingSqlService.java`, implement `public GreetingSqlService()` constructor.

**Requirements:**
1. DI the autoconfigured `JdbcTemplate` bean and instantiate the super class constructor
2. Add a new class variable `sqlTableName` that's expected to be input when calling the `GreetingSqlService` constructor

#

### Exercise 1 Task 3 Part II: `sqlTableName()`
In `GreetingSqlService.java`, implement `protected String sqlTableName()`.

Return the same `sqlTableName` variable that's passed into the constructor.

### Example 1:
> **Input**:<br>
> ```java
> GreetingSqlService service = new GreetingSqlService(jdbcTemplate, "testTableName");
> String output = service.sqlTableName();
> ```
>
> **Output**: "testTableName"

#

### Unit Tests
- [ ] TODO

### Exercise 1 Task 3 Part II: `columns()`
In `GreetingSqlService.java`, implement `protected List<String> columns()`.

Return a list of all the hard-coded fields of our `greeting_events` SQL table (schema).

**Requirements:**
- The output in which the schema fields are returned **MUST** respect the order of your `greeting_events` SQL table schema.

### Example 1:
> **Input**:<br>
> ```
> Assume this the table created in SQL:
> CREATE TABLE dev_db.employees (
>    employee_id INT PRIMARY KEY,
>    first_name VARCHAR(255),
>    last_name VARCHAR(255),
>    birthday VARCHAR(255),
>    photo VARCHAR(255),
>    notes VARCHAR(255)
> )
> ```
> ```java
> // The dedicated class that should interact with the 'dev_db.employees' SQL Table
> public class EmployeeSqlService extends AbstractSqlService<EmployeeEvent> {
>     // ...
> 
>     @Override
>     public List<String> columns() {
>         return List.of("employee_id", "first_name", "last_name", "birthday", "photo", "notes");
>     }
>
>     // ...
> }
> EmployeeSqlService service = new EmployeeSqlService(jdbcTemplate, "employees");
> List<String> output = service.columns();
> ```
>
> **Output**:
> ```
> ["employee_id", "first_name", "last_name", "birthday", "photo", "notes"]
> ```
> 
> **Explanation:**<br>
> The order of the schema from the DDL `CREATE TABLE dev_db.employees ...` matches the output order that our method call for `columns()` returns.<br>
> Since our goal is "lock" this example class `EmployeeSqlService` to **ONLY** interact with this `dev_db.employees` SQL table we can hard code the schema in the `columns()` method.<br>
>
> > [!NOTE]
> > 
> > There's a valid point in arguing for using DDL (Data Definition Language) to dynamically maintain/pull the schema for a table, but I want you to keep this simple and just hard code the schema as our `GreetingEvent` schema isn't going to be evoving.

### Unit Tests
- [ ] TODO. Tested with the actual GreetingEvent expecting it in the same schema order of the table creation DDL.

#

### Exercise 1 Task 3 Part III: `values()`
In `GreetingSqlService.java`, implement `protected Object[] values(GreetingEvent event)`. This method will be called with a `GreetingEvent` event.

Return an `Object[]` containing all the values in the `GreetingEvent`.

**Requirements:**
- The output in which the values of the event are returned **MUST** respect the order of your `greeting_events` SQL table schema.

### Example 1:
> **Input**:<br>
> ```java
> GreetingEvent input1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent input2 = new GreetingEvent("id2", "Charlie", "David", "Yo David!");
> GreetingSqlService service = new GreetingSqlService(jdbcTemplate, "mockTableName");
>
> Object[] output1 = service.values(input1);
> Object[] output2 = service.values(input2);
> ```
>
> **Output1**:<br>
> ```
> ["id1", "Alice", "Bob", "Hi Bob, I'm Alice!"]
> ```
>
> **Output2**:<br>
> ```
> ["id2", "Charlie", "David", "Yo David!"]
> ```

### Unit Tests
- [ ] TODO. Testing using examples above and expecting the same ordering that the `greeting_events` table was created with.


### Example 1:
> **Input:**<br>
> ```java
> GreetingSqlService greetingSqlService = new GreetingSqlService(...);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");
> GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");
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

#

### Exercise 1 Task 3 Part IV: Testing `GreetingSqlService`
By this point, you should have implemented:
- `AbstractSqlService.java` (parent class):
  - `public AbstractSqlService(JdbcTemplate jdbcTemplate)`
  - `public int insert(List<T> events)`
- `GreetingSqlService.java` (child class):
  - `public GreetingSqlService(JdbcTemplate jdbcTemplate, String sqlTableName)`
  - `public `public String sqlTableName()`
  - `public List<String> columns()`
  - `protected Object[] values(GreetingEvent event)`

These methods should all being working together to achieve the expected outputs in the example below.

### Example 1:
> **Input:**<br>
> ```java
> GreetingSqlService greetingSqlService = new GreetingSqlService(...);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");
> GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");
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


### Integration Tests
- [ ] Verify that **Docker Desktop** is running.
- [ ] Open `GreetingSqlServiceTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method(s): `insertTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

<br>

#

### Exercise 1 Task 4: GreetingSqlService @Bean
In `SqlConfig.java`, implement `@Bean public GreetingSqlService singleGreetingSqlService()`. This bean is dedicated to handling read/writes to your `dev_db.greeting_events` SQL table.

**Requirements:**
- DI the `greeting_event` property from `application.yml`

### Unit Tests
- [ ] TODO (fix this this is the wrong description) Open `SqlConfigTest.java` ─ already implemented to test the `tableName()` and `columns()` return expected values
- [ ] Remove `@Disabled` in `SqlConfigTest.java` for the test method(s): `singleGreetingSqlService_beanTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

<br>

#

### Exercise 1 Task 5: Hook up the Kafka Consumer to use the SQL writer
In `GreetingEventConsumer.java` (**Module 2**), integrate with the `singleGreetingSqlService` bean.<br>
Everytime an event is read from Kafka, we will need to call `GreetingSqlService.insert()` method to persist that event into the SQL table.

**Requirements:**
- DI the correct `GreetingSqlService` bean into the `GreetingEventConsumer` constructor.<br>

### Quiz: Spring Bean Dependency Resolution
>
> 
> Notice in `SqlConfig.java` there are two `@Bean`'s of the same type `GreetingSqlService`. When there are collisions for objects of the same type, Spring has trouble resolving which `@Bean` to pick, so Spring then uses the Bean **name** to best-effort resolve Bean conflicts.
> It's good practice to refer to `@Bean`'s by **name** using the Spring `@Qualifier()` annotation.
>
> Example 1: No Bean resolution
> ```
> @Configuration
> public ExampleConfiguration {
>     @Bean
>     public String string1() {
>         return "Hello";
>     }
>
>     @Bean
>     public String string2() {
>         return "World";
>     }
> }
> 
> public ExampleClass {
>     public doSomething(
>         String string3, // no @Bean named 'string3'
>         String string4  // no @Bean named 'string4'
>     ) {
>         System.out.println(string3 + " " + string4);
>     }
> }
> ```
> **Output:** Exception is thrown.<br>
> **Explanation:**
> Spring sees two `String` beans (ambiguous). Since the **parameter names** (`string3`, `string4`) do not match any available bean names (`string1`, `string2`), Spring cannot resolve the conflict and throws a NoUniqueBeanDefinitionException.
>
> Example 2: Resolving by Parameter Name (Best-Effort)
> ```
> @Configuration
> public ExampleConfiguration {
>     @Bean
>     public String string1() {
>         return "Hello";
>     }
>
>     @Bean
>     public String string2() {
>         return "World";
>     }
> }
> 
> public ExampleClass {
>     public doSomething(
>         String string1, // Matches @Bean string1()
>         String string2  // Matches @Bean string2()
>     ) {
>         System.out.println(string1 + " " + string2);
>     }
> }
> ```
> **Output:** "Hello World"<br>
> **Explanation:** Spring sees two String beans (ambiguous). Spring attempts to match the parameter names (`string1`, `string2`) to the @Bean method names (`string1()`, `string2()`). This resolves the conflict.
>
> Example 3: Resolving by `@Qualifier`
> ```
> @Configuration
> public ExampleConfiguration {
>     @Bean
>     @Qualifier("Hello")
>     public String string1() {
>         return "Hello";
>     }
>
>     @Bean
>     @Qualifier("World")
>     public String string2() {
>         return "World";
>     }
> }
> 
> public ExampleClass {
>     public doSomething(
>         @Qualifier("World") String string1,
>         @Qualifier("Hello") String string2
>     ) {
>         System.out.println(string1 + " " + string2);
>     }
> }
> ```
> **Output:** "World Hello"<br>
> **Explanation:** Spring ignores the parameter names (`string1`, `string2`) and strictly uses the @Qualifier annotations (`@Qualifier("World")` and `@Qualifier("Hello")`) to pick the exact matching bean names from the configuration. This is the safest way to resolve ambiguity.

#

### E2E Tests
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

### Exercise 2: Implement Batch Writes
![](assets/module3/images/exercise3.svg)<br>

Similar to the **'Lesson: Input/Output (IO) Operations'** section in **Module 2**, we will optimize the # of IO calls to our SQL server by reducing write IOs.<br>
If we needed to write 1M events into our SQL table, instead of separately issuing 1,000,000 calls to SQL we will group a batch of events to make less round trip calls to the SQL server.

Ultimately, the end result will be **idempotent**, meaning same 1M events will be stored regardless of if we called the writes to SQL individually as single events vs. or grouped in batches of events (assuming the order of events are always the same).

#

### Exercise 2 Task 1: Create new SQL table (DDL)
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

### Exercise 2 Task 2: Spring Application Property
Similar to **"Exercise 1 Task 1"**, you need to add the newly create SQL table name to your `application.yml` properties.

```yaml
twitch-chat-hit-counter:
  sql:
    greeting-table-batch: batch_greeting_events
```

### Unit Tests
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented to test the property above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for the test method(s): `sqlBatchGreetingTableNameTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

#

### Exercise 2 Task 3: SQL GreetingEvent Writer
In `AbstractSqlService.java`, take a look at `public int insert(List<T> events)`. Notice how in "Exercise 1 Task TODO" I made you implement a flexible method that supports writing a `List<T> events` to SQL depending on the input array's `size()`. 

This means we should be able to handle writing any number of events to SQL flexibly.

### Example 1:
> **Input**:<br>
> ```java
> GreetingSqlService greetingSqlService = new GreetingSqlService(...);
> 
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");
> GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");
> 
> int output = greetingSqlService.insert(List.of(event1, event2, event3));
> ```
>
> **Output**: 2<br>
> **Explanation**:
> This example looks very similar to the example in "Exercise 1 Task TODO". The order in which we insert the events are the same, the only difference here is that instead of 3 separate server calls, you should be issuing 1 server call.<br>
> Your SQL statement should first write event1 (success) and then event2 (success), but event3 has the same primary key as event1 ("id1") which was just written in the same batch, so SQL will drop event3 (assuming your SQL command is written correctly using `INSERT IGNORE INTO ...`.<br>
>
> Therefore, in this batch of 3 events total, 2 events were successfully stored in SQL (the 3rd being dropped).

#

### Integration Tests
- [ ] Verify that **Docker Desktop** is running.
- [ ] Open `GreetingSqlServiceTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method: `insertBatchTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

#

### Exercise 2 Task 3: GreetingSqlService @Bean
In `SqlConfig.java`, implement `@Bean public GreetingSqlService batchGreetingSqlService()`. This bean is dedicated to handling read/writes to your `dev_db.batch_greeting_events` SQL table.

**Requirements:**
- DI the `batch_greeting_events` property from `application.yml`

### Unit Tests
- [ ] TODO fix this entire block
- [ ] Open `SqlConfigTest.java` ─ already implemented to test the `tableName()` and `columns()` return expected values
- [ ] Remove `@Disabled` in `SqlConfigTest.java` for the test method(s): `batchGreetingSqlService_beanTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

<br>

#

### Exercise 2 Task 4: Hook up the Batch Kafka Consumer to use the Batch SQL writer
In `GreetingEventBatchConsumer.java` (**Module 2**), integrate with the `batchGreetingSqlService` bean.<br>
Everytime event(s) are read from Kafka, we will need to call `GreetingSqlService.insert()` method to persist that event into the SQL table (`batch_greeting_events`).

**Requirements:**
- DI the correct `GreetingSqlService` bean into the `GreetingEventConsumer` constructor.<br>

#

### E2E Tests
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with **Kafka API**: `/api/kafka/publishGreetingEvent`
- [ ] In **MySQLWorkbench**, verify that the `GreetingEvent` triggered via **Swagger** is written into SQL by querying:
    ```
    SELECT *
    FROM batch_greeting_events
    ```
- [ ] TODO Reset the batch group-id to re-process all the Kafka events from the very beginning to test that the bulk writes are happening?

#

## Exercise 3: SQL API
![](assets/module3/images/exercise2.svg)<br>

### Exercise 3 Task 1: Implement AbstractSqlService.queryAllEvents()
In `AbstractSqlService.java`, implement `public List<T> queryAllEvents()`. This method should scan **ALL** the records in a SQL table.

Return a `List<T>` of all the events in a SQL table.

**Requirements:**
- Create a variable named `SQL_QUERY_TEMPLATE`. This should be a templatized SQL command string that will perform writes into a SQL table.
  [SQL Query <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/sql/sql_syntax.asp), look at the SQL template:
```
SELECT * table_name;
```
- Rely on the abstract method `sqlTableName()` to help fill in the placeholder for `table_name`
- Take a look at [JdbcTemplate's query(String sql, RowMapper<T> rowMapper) <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html) method to see how to send a SQL Query through JDBC. 
- Rely on the abstract method `protected abstract T parseEventFromResultSet(ResultSet rs)` to convert a `ResultSet` object back to a generic event `T`. (This will be implemented in the following Task, for now just **_assume_** you've implemented it).
- Return a `List<T>` of the translated objects read back from SQL

> [!TIP]
>
> Read through [Store and Retrieve Data <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://spring.io/guides/gs/relational-data-access) as it will look very similar to what we want.<br>

[//]: # (> Example:)

[//]: # (> ```)

[//]: # (> -- DDL)

[//]: # (> CREATE TABLE customers &#40;)

[//]: # (>     id SERIAL,)

[//]: # (>     first_name VARCHAR&#40;255&#41;,)

[//]: # (>     last_name VARCHAR&#40;255&#41;)

[//]: # (> &#41;;)

[//]: # (> ```)

[//]: # (> ```java)

[//]: # (> log.info&#40;"Querying for customer records where first_name = 'Josh':"&#41;;)

[//]: # (> jdbcTemplate.query&#40;)

[//]: # (>     "SELECT id, first_name, last_name FROM customers WHERE first_name = ?",)

[//]: # (>     &#40;rs, rowNum&#41; ->)

[//]: # (>         new Customer&#40;rs.getLong&#40;"id"&#41;, rs.getString&#40;"first_name"&#41;, rs.getString&#40;"last_name"&#41;&#41;, "Josh"&#41;)

[//]: # (> .forEach&#40;customer -> log.info&#40;customer.toString&#40;&#41;&#41;&#41;;)

[//]: # (> ```)

[//]: # (> )

[//]: # (> The important piece here is that the lambda expression `&#40;rs, rowNum&#41; -> // TODO` should be calling the abstract method `parseEventFromResultSet&#40;rs&#41;`.<br>)

[//]: # (> The lambda expression `&#40;rs, rowNum&#41;` is just a fancy, succinct way of for-looping through the SQL-queried data rows one by one, processing a single `ResultSet rs` at a time.)

[//]: # (> )

[//]: # (> To re-iterate, you don't need to solve it this way, there's many ways to accomplish the same thing but this is the way I will implement my logic.)

#

### Exercise 3 Task 2: Implement GreetingSqlService.queryAllEvents()
In `GreetingSqlService.java`, implement `protected GreetingEvent parseEventFromResultSet(ResultSet rs)`. This method expects the `ResultSet` -- A table of data representing a database result set, which is usually generated by executing a statement that queries the database.

Return a `GreetingEvent` by parsing the `ResultSet` record row that gets passed back from the `JdbcTemplate` query call.

**Requirements:**
- Parse the `ResultSet` record to a `GreetingEvent` ([Guide <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/jdbc-resultset#resultset-creation))


### Example 1:
> **Input**:<br>
> ```java
> GreetingSqlService greetingSqlService = new GreetingSqlService(...);
>
> GreetingEvent event1 = new GreetingEvent("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> GreetingEvent event2 = new GreetingEvent("id2", "Charlie", "David", "Yo.");
> GreetingEvent event3 = new GreetingEvent("id1", "Echo", "Frank", "Hello there.");
>
> greetingSqlService.insert(List.of(event1));
> List<GreetingEvent> output1 = greetingSqlService.queryAllEvents();
>
> greetingSqlService.insert(List.of(event2));
> List<GreetingEvent> output2 = greetingSqlService.queryAllEvents();
> 
> greetingSqlService.insert(List.of(event3));
> List<GreetingEvent> output3 = greetingSqlService.queryAllEvents();
> ```
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
> **Explanation**: **event1** is written into SQL so 1 event(s) are read.<br><br>
>
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
> **Explanation**: **event1** and **event2** are written into SQL so 2 event(s) are read.<br><br>
>
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
> **Explanation**: **event3** is **NOT** written into SQL because it is considered a duplicate event by SQL (due to PK clash), so 2 event(s) are read.

#

### Unit Tests
- [ ] Open `GreetingSqlServiceTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `GreetingSqlServiceTest.java` for the test method(s): `queryTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```


#

### Exercise 3 Task 3: Hook up the `SqlRestController` to the `GreetingSqlService`
In `SqlRestController.java`, implement `public List<GreetingEvent> getSqlGreetingEvents(String tableName)`.

Return the `List<GreetingEvent>` return by calling the `GreetingSqlService.queryAllEvents()` method.

**Requirements:**
- DI both `GreetingSqlService` beans in `SqlConfig.java` into the constructor of the `SqlRestController.java`.
- Depending on the `tableName` input parameter, call `queryAllEvents()` on the correct Bean. (Hint: match it against the `sqlTableName()` method you implemented)
- If there is no `tableName` match, throw a `IllegalArgumentException`

### Example 1:
> **Input:**
> ```java
> SqlRestController controller = new SqlRestController(...);
> List<GreetingEvent> output1 = controller.getSqlGreetingEvents("greeting_events");
> List<GreetingEvent> output2 = controller.getSqlGreetingEvents("batch_greeting_events");
> List<GreetingEvent> output3 = controller.getSqlGreetingEvents("non_existent_table");
> ```
> 
> Output1:
> ```
> [GreetingEvent(), GreetingEvent()]
> ```
> 
> Output2:
> ```
> [GreetingEvent(), GreetingEvent()]
> ```
> 
> Output3: exception should be thrown


### Example 2:
> **Input:**
> ```java
> SqlRestController controller = new SqlRestController(...);
> List<GreetingEvent> output1 = controller.getSqlGreetingEvents("greeting_events");
> List<GreetingEvent> output2 = controller.getSqlGreetingEvents("batch_greeting_events");
> List<GreetingEvent> output3 = controller.getSqlGreetingEvents("non_existent_table");
> ```
>
> Output1:
> ```
> [GreetingEvent(), GreetingEvent()]
> ```
>
> Output2:
> ```
> [GreetingEvent(), GreetingEvent()]
> ```
>
> Output3: exception should be thrown

#### Testing
- [ ] Open `SqlRestControllerTest.java` ─ already implemented with the example(s) above.
- [ ] Remove `@Disabled` in `SqlRestControllerTest.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module3
    ```

### Integration Testing
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Execute **SQL API**: `GET /api/sql/queryAllEvents`
  ![](assets/module3/images/sqlApi.png)
