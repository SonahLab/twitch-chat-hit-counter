# Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 1: HTTP/REST + Swagger
### Lesson
This section provides a brief TL;DR on how the internet works. When you open your browser and navigate to Google.com, several processes occur behind the scenes:<br>
1. **DNS Resolution**:<br>
Google.com is a user-friendly, human-readable name that is easy to remember. This domain name corresponds to an actual IP address, which your machine resolves using a DNS server.
2. **Request to Server**:<br>
Once the IP address is resolved, your machine sends a request to that IP address to retrieve the data located at this address
3. **Response from Server**:<br>
Google's servers receive the incoming request from your machine and return a response with the "data" that you see as Google.com.<br>

This process is an over simplification for all communication across machines connected by the internet.<br>
You have 2 machines: one sender and one receiver; the sender knows where it wants to speak to, and sends a request operation to the receiver. Routers/networks (aka mailman) will understand where to route the data packets in order for the request to reach its destination. Once the receiver receives the request, it will do something. Based on the API request, this will let the server know what process to kick off.

There are many communication protocols, but the two most common protocols I’ve used ubiquitously in big tech are: HTTP and gRPC. We will focus only on HTTP in this course.

Swagger
Let’s start by setting up our Spring Boot service’s API endpoints that can be easily integrated using Swagger. Another popular tool I’ve used to locally test my service’s API endpoints is Postman (at Snap), but Swagger integrates nicely with Spring Boot, which is what we ubiquitously use for most Netflix microservices.


### Additional Learning Materials

HTTP:

REST:

Swagger:

## File Structure
For `Module 1`, the below file structure are all the relevant files needed.
```
twitch-chat-hit-counter/src/
├── main/
│   └── java/com.sonahlab.twitch_chat_hit_counter/
│       ├── model/
│       │   └── GameCharacter.java
│       ├── repository/
│       │   └── GameCharacterManager.java
│       ├── rest/
│       │   ├── GameRestController.java
│       │   └── GreetingRestController.java
│       └── utils/
│           └── Potion.java
└── test/
    └── java/com.sonahlab.twitch_chat_hit_counter/
        └── rest/
            ├── GameRestControllerTest.java
            └── GreetingRestControllerTest.java
```
## Exercise 1: sayHello
`GreetingRestController.java` ─ the HTTP api endpoint(s) handler to handle our service's simple greeting methods: `/api/sayHello`.

Implement the `sayHello()` method, which takes the incoming request parameter `name` and returns a simple greeting response.<br>
If no input `name` parameter is provided, greet a `Mysterious Individual`.

#### Example 1:
> Input: `name = "Alice"`<br>
> Output: `"Hello, Alice!"`

#### Example 2:
> Input: `name = ""`<br>
> Output: `"Hello, Mysterious Individual!"`<br>
> Explanation: no name `parameter` was provided, so we default the name as if it were `Mysterious Individual`

#### Testing
`GreetingRestControllerTest.java` ─ already implemented; contains two simple tests with the above examples.
1. Remove `@Disabled` in `GreetingRestControllerTest.java`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module1`

#### Integration Testing
1. Run: `./gradlew bootRun`
2. Go to: `http://localhost:8080/swagger-ui/index.html`

## Exercise 2: takeDamage, consumePotion, characterState
`GameRestController.java` ─ the HTTP api endpoint(s) handler to handle:
1. `/api/fantasyGame/takeDamage`: deal damage to a character
2. `/api/fantasyGame/consumePotion`: consume potion to replenish character's HP/MP
3. `/api/fantasyGame/characterState`: get current snapshot information about a character 

`GameCharacter.java` ─ Java object to hold related metadata about a GameCharacter</br>
`GameCharacterManager.java` ─ the main class that will manage all data model state changes to a `GameCharacter` object
`Potion.java` ─ enum class to define all Potion objects that exist in our game.

### Task 1: Implement `GameCharacter.java`
<p>Before we can implement our API endpoints in `GameRestController.java`, we need to create a data model object for a generic fantasy game character.  
If you aren't familiar with creating POJOs (Plain Old Java Objects), I suggest you read up on various ways to create your object class:<br>
- Java Records (https://docs.oracle.com/en/java/javase/17/language/records.html)<br>
- Java Builder Patterns (https://www.baeldung.com/java-builder-pattern)<br>
- Java Constructor (https://www.w3schools.com/java/java_encapsulation.asp)
</p>

**Requirements:**
We want every GameCharacter object to have 3 fields:
1. HP (int): Character's health points (between 0 and 100)
2. MP (int): Character's mana points (between 0 and 100)
3. Inventory (Map<Potion, Integer>): Character's inventory of Potion enum to quantity count
> **Note:** Each field should have a getter/setter (to enforce encapsulation and control how variables are accessed/modified).

We also want to set default values for each creation of a GameCharacter object:
1. Set initial HP to 100
2. Set initial MP to 100
3. Populate initial inventory:
    1. 5 `Potion.HP_POTION`
    2. 5 `Potion.MP_POTION`

#### Example 1:
```java
GameCharacter character = new GameCharacter();

// Validate the default construction of the GameCharacter fields
character.getHp();        // Expected: 100
character.getMp();        // Expected: 100
character.getInventory(); // Expected: {"HP_POTION": 5, "MP_POTION": 5}

// Validate the HP/MP setting logic (between 0 and 100)
character.setHp(10000);   // Expected: null (Setters should be void, so there's no return value)
character.getHp();        // Expected: 100 (hp should be between 0 and 100, so we should ignore the previous 10000 set value)
character.setHp(-10000);  // Expected: null (Setters should be void, so there's no return value)
character.getHp();        // Expected: 100 (hp should be between 0 and 100, so we should ignore the previous -10000 set value)
character.setMp(10000);   // Expected: null (Setters should be void, so there's no return value)
character.getMp();        // Expected: 100 (hp should be between 0 and 100, so we should ignore the previous 10000 set value)
character.setMp(-10000);  // Expected: null (Setters should be void, so there's no return value)
character.getMp();        // Expected: 100 (hp should be between 0 and 100, so we should ignore the previous -10000 set value)
```


### Task 2: Implement `GameCharacterManager.java`
Now that we have our data model for a generic GameCharacter we want to handle the different state changes needed by the API endpoints.<br>
The Constructor of this class should initialize a generic GameCharacter `character` (similar to the example shown above).<br>

### Subtask 2.1:
Create and implement method `public int takeDamage(int damage) {}`, where you will reduce the `character` object's HP by the `damage` amount.</br>
Return the updated character HP.</br>

#### Example 1:
> ![](course-material/assets/module1/images/takeDamage_50.png)<br>
> Input: `damage = 50`; // Assume HP = 100</br>
> Output: `50`</br>

#### Example 2:
> ![](course-material/assets/module1/images/takeDamage_110.png)<br>
> Input: `damage = 110`; // Assume HP = 100</br>
> Output: `0`</br>
> Explanation: A character's HP should never be negative. (min capped at 0)</br>

### Subtask 2.2:
Create and implement method `public int consumePotion(Potion potion) {}`, where you will increment the `character` object's HP/MP depending on the potion.</br>
Return the updated character HP/MP, or -1 for any errors.</br>
Rules:</br>
1. Validate that `character` has the `Potion` currently in their inventory.
2. `Potion.HP_POTION` will increment the `character` HP by `50`.</br>
   `Potion.MP_POTION` will increment the `character` MP by `50`.
3. Decrement the quantity of the consumed `Potion` by 1 in the `character` inventory.
4. Remove the `Potion` from the `character` inventory if the quantity has hit `0`.
#### Example 1:
> ![](course-material/assets/module1/images/consumePotion_HP_25.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 25</br></span>
> **Output**: <span style="color:#0000008c">75<br></span>

#### Example 2:
> ![](course-material/assets/module1/images/consumePotion_HP_99.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 99</br></span>
> **Output**: <span style="color:#0000008c">100<br></span>
> **Explanation**: <span style="color:#0000008c">A character's HP should never be above 100. (max capped at 100)</br></span>

#### Example 3:
> ![](course-material/assets/module1/images/consumePotion_MP_25.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.MP_POTION // Assume MP = 25</br></span>
> **Output**: <span style="color:#0000008c">75<br></span>

#### Example 4:
> ![](course-material/assets/module1/images/consumePotion_MP_99.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.MP_POTION // Assume MP = 99<br></span>
> **Output**: <span style="color:#0000008c">100<br></span>
> **Explanation**: <span style="color:#0000008c">A character's MP should never be above 100. (max capped at 100)<br></span>

#### Example 5:
> ![](course-material/assets/module1/images/consumePotion_Remove.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 25<br></span>
> **Output**: <span style="color:#0000008c">75<br></span>
> **Explanation**: <span style="color:#0000008c">The Inventory should no longer contain the HP_POTION now that the quantity of this item is 0.<br></span>

#### Example 6:
> ![](course-material/assets/module1/images/consumePotion_Error.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 50<br></span>
> **Output**: <span style="color:#0000008c">-1<br></span>
> **Explanation**: <span style="color:#0000008c">There are no HP_POTION in the inventory, so we can't consume this potion.<br></span>

### Subtask 2.3:
Create and implement method `public void getCharacterState() {}`, where you will return a Map<String, Object> representing the character's current state (HP/MP/Inventory).
#### Example 1:
> ![](course-material/assets/module1/images/characterState.jpg)<br>
> **Input**: <span style="color:#0000008c">None // Assume the character starts with 100 HP and MP, and 5 of each HP/MP potions.<br></span>
> **Output**:
> ```json
> {
>     "HP": 100,
>     "MP": 100,
>     "INVENTORY": {
>         "HP_POTION": 5,
>         "MP_POTION": 5
>     }
> }
> ```

#### Testing
`GameCharacterManager.java` ─ already implemented
1. Remove `@Disabled` in `GameCharacterManager.java`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module1`


## Task 3: Hookup our `GameRestController.java` to our `GameCharacterManager.java` application logic
Now we should have all the application logic to handle the API endpoints for:
- PUT /api/fantasyGame/takeDamage
- PUT /api/fantasyGame/consumePotion
- GET /api/fantasyGame/characterState

#### Testing
`GameRestControllerTest.java` ─ already implemented
1. Remove `@Disabled` in `GameRestControllerTest.java`
2. Run: `./gradlew test --tests "*" -Djunit.jupiter.tags=Module1`