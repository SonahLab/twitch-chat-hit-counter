# The Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 0: Project Setup & Guidelines

## Course Tech Stack
- IntelliJ IDE
- Java 21+
- Spring Boot 3+
- Gradle 8.x.x+
- NodeJS (for the FE application)
- Swagger (OpenAPI)
- Apache Kafka
- MySQL
- Redis
- Twitch API

This project was bootstrapped using the default Spring Boot Initializr set up flow. This flow creates a basic, barebones Java Spring Boot repo for a generic "backend" application: [Spring Initializr <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://start.spring.io/)<br>
There's a Frontend application built using a generic React application template: [Create a React App <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://react.dev/learn/creating-a-react-app)

I'm expecting students who take this course to already be familiar with the basics of programming, coding in **Java**, and **Spring Boot** (concepts like [Spring Dependency Injection <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html))


## Local Development
1. Building the repo:
    ```
    cd ~/path/to/repo/ &&
    ./gradlew build &&
    ./gradlew idea
    ```
2. In IntelliJ, open up generated file: `twitch-chat-hit-counter.ipr`
3. Starting your Spring Application: `./gradlew bootRun`

## Testing
Test the entire repo:
```bash
./gradlew test
```
Test an entire "Module" suite (I custom-tagged all the unit tests by Module):
```bash
./gradlew test --tests "*" -Ptags=Module1
```
Test a specific class:
```bash
./gradlew test --tests com.sonahlab.twitch_chat_hit_counter_course.MainApplicationTest
```
Test a specific method:
```bash
./gradlew test --tests com.sonahlab.twitch_chat_hit_counter_course.MainApplicationTest.main
```

