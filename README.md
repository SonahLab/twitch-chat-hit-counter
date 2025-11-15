# Practical Backend Engineer
## Twitch Chat Hit Counter

### Prerequisites
- [IntelliJ <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.jetbrains.com/idea/) (or any favorite IDE)
- Java 21+
- [Docker Desktop <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.docker.com/)
- [Offset Explorer <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.kafkatool.com/)
- [MySQLWorkbench <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.mysql.com/downloads/workbench/)
- [Twitch.TV account <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.twitch.tv/)
- [nodejs <img src="course-material/assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://nodejs.org/en/download)

#

### Useful Links
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html#/
- **Redis Insight UI**: http://localhost:8001/

#

### Repository Structure
- **course-material**: folder with all the course Modules and exercises to work through<br>
- **gui**: React FE application to integrate our backend application with<br>
- **src**: Spring Boot backend application<br>
  - **main**:
    - **java:**
      - **config**: Configuration files for any common dependency injectable @Beans to be built at runtime
      - **kafka**: Kafka related producer/consumer files
      - **model**: Data Model classes
      - **redis**: Redis related files
      - **rest**: HTTP Rest Endpoint files
      - **sql**: SQL related files
      - **twitch:** Twitch API related files
      - **utils:** Utility related files (Enums, helpers, etc)
    - **resources:** application related properties
  - **test**: folder for unit/integration tests
- **build.gradle:** Backend build file with all the relevant library imports needed<br>
- **dependencies.lock**: Locked nebula dependencies file<br>

#

### Setup project
- Build the project in the `twitch-chat-hit-counter/` directory
  ```bash
  ./gradlew build && ./gradlew idea
  ```
- Open up the project in IntelliJ `twitch-chat-hit-counter.ipr`

#

### Running the application
```bash
./gradlew bootRun
```
