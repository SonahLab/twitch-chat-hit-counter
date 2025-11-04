# Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 1: HTTP/REST + Swagger

### Lesson [TODO]
I'll keep things simple.

When thinking about how the internet works, how big tech systems work, they're all the same.

Move data from point A to point B.

Visiting a Google.com? My PC (server) sends a data request to Googles' servers, those servers accept the request and send data back to my PC. (Point A sends request to Point B, Point B sends response to Point A)
Upstream team is sending our team data? Upstream team collects the data (Point A) and dumps them off to some file storage or database, where our team reads from (Point B).
Downstream team depends on our team's data? We gather our data and write to FS/DB/send back through direct API call (Point A) and downstream team receives it (Point B).



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



<br>

## File Structure
For `Module 1`, the below file structure are all the relevant files needed.

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
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GameCharacter.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GameRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> GreetingRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> utils/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/enum.svg" align="center"/> Potion.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/enum.svg" align="center"/> Stat.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> test/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testRoot.svg" align="center"/> java/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> com.sonahlab.twitch_chat_hit_counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GameCharacterTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GameRestControllerTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> GreetingRestControllerTest.java<br>


<br>

## Exercise 1: Greeting API
![](assets/module1/images/greetingController.svg)<br>

> [!NOTE]
>
> **Relevant Files**<br>
> `GreetingRestController.java` ─ REST controller to handle our service's simple greeting methods: `/api/greeting/hello`.

In `GreetingRestController.java`, implement the `public String sayHello(@RequestParam(required = false) String name)` method.

This method is the entry point for all incoming HTTP requests routed to `GET /api/greeting/hello`.

It takes an incoming request parameter `name` and returns a simple greeting response.

If no input `name` parameter is provided, we should default the greeting message to address a `Mysterious Individual`.

### Example 1:
> **Input**: <span style="color:#0000008c">name = "Alice"<br></span>
> **Output**: <span style="color:#0000008c">"Hello, Alice!"</span>

#

### Example 2:
> **Input**: <span style="color:#0000008c">name = ""<br></span>
> **Output**: <span style="color:#0000008c">"Hello, Mysterious Individual!"<br></span>
> **Explanation**: <span style="color:#0000008c">no `name` parameter is provided, so the greeting response should address "Mysterious Individual"</span>

#

### Testing
- [ ] Open `GreetingRestControllerTest.java` ─ already implemented test cases with the example(s) above.<br>
- [ ] Remove `@Disabled` in `GreetingRestControllerTest.java`<br>
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

#

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with the **Greeting API** endpoint(s)

![](assets/module1/images/greetingSwagger.png)<br>


<br>


## Exercise 2: Game API
> [!NOTE]
>
> **Relevant Files**<br>
> `Potion.java` ─ enum defining all potions.<br>
> `Stat.java` ─ enum defining character stats.<br>
> `GameCharacter.java` ─ Data Model for a generic game character in a game with state logic.<br>
> `GameRestController.java` ─ REST controller to handle game related actions on our `GameCharacter`

### Task 1: Implement `GameCharacter.java`
Before we can implement our API endpoints in `GameRestController.java`, we need to create our Data Model object for a generic fantasy game character.

In `GameCharacter.java`, implement the following:
- `public GameCharacter()` (CONSTRUCTOR)
- `public int getStat(Stat stat)` (GETTER)
- `public Map<Potion, Integer> getInventory()` (GETTER)
- `public void setHp(int hp)` (SETTER)
- `public void setMp(int mp)` (SETTER)
- `public int takeDamage(int damage)` (STATE CHANGE)
- `public int consumePotion(Potion potion)` (STATE CHANGE)
- `public Map<String, Object> getCharacterState()` (GETTER)

> [!TIP]
>
> If you aren't familiar with creating POJOs (Plain Old Java Objects), I suggest you read up on various ways to create Java Object Oriented Programming (OOP) objects:<br>
> - [_Java Constructor Pattern_ <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.w3schools.com/java/java_encapsulation.asp)<br>
> - [_Java Builder Pattern_ <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.baeldung.com/java-builder-pattern)<br>
> - [_Java Record Pattern_ <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://docs.oracle.com/en/java/javase/17/language/records.html)<br>

> [!NOTE]
>
> The very basics of **OOP (Object Oriented Programming)** is that you have well-defined classes that encapsulates some stateful variables/methods that are common to the type of object you are attempting to model.
>
> **Example**:
> ```java
> class Car {
>     private String make;
>     private String model;
>     private int speed;
>
>     public Car(String make, String model) {
>         this.make = make;
>         this.model = model;
>         this.speed = 0;
>     }
>
>     public String getMake() {
>         return make;
>     }
>
>     public String getModel() {
>         return model;
>     }
>
>     public int getSpeed() {
>         return speed;
>     }
>
>     // Increases car speed by 10 MPH w/ a maximum speed of 100 MPH
>     public void accelerate() {
>         speed = Math.min(100, speed + 10);
>     }
>
>     // Decreases car speed by 10 MPH w/ a minimum speed of 0 MPH
>     public void decelerate() {
>         speed = Math.max(0, speed - 10);
>     }
> }
>
> public class Main {
>     public static void main(String[] args) {
>         Car car1 = new Car("Toyota", "Prius");
>         Car car2 = new Car("Honda", "Civic");
>
>         car1.getMake(); // "Toyota"
>         car1.getModel(); // "Prius"
>         car1.getSpeed(); // 0
>         car2.getMake(); // "Honda"
>         car2.getModel(); // "Civic"
>         car2.getSpeed(); // 0
>
>         car1.accelerate(); // speed → 10
>         car1.accelerate(); // speed → 20
>         car1.accelerate(); // speed → 30
>         car1.getSpeed(); // 30
>
>         car2.accelerate(); // speed → 10
>         car2.getSpeed(); // 10
>         car2.decelerate(); // speed → 0
>         car2.getSpeed(); // 0
>     }
> }
> ```
>
> `car1` and `car2` are both `Car` class objects, but they each have their own separate memory space in the program and each object maintains its own independent state:<br>
> `car1`: make = "Toyota", model = "Prius", and its own speed<br>
> `car2`: make = "Honda", model = "Civic", and its own speed
>
> This is the core idea of objects in OOP: 1 class → N objects (each with its own data).
>
> Think of the Car class like a car factory blueprint.<br>
> You can use the same blueprint to build many cars, but each car has their own unique set of variables that're
> independent of each other and have state altering methods within the Class (Encapsulation).

### Constructor
In `GameCharacter.java` our constructor is empty, so anytime we create a new instance of this class, it holds 0 state.

Implement `public GameCharacter()` based on the requirements below.

**Requirements:**<br>
1. `GameCharacter` object should have 3 fields:
    1. **Stat.HP (int):** Character's health points (between `0` and `100`)
    2. **Stat.MP (int):** Character's mana points (between `0` and `100`)
    3. **Inventory (Map<Potion, Integer>):** Character's bag of `Potion` enum to quantity count
2. Default values on `GameCharacter` init:
    1. Initial **HP** of 100
    2. Initial **MP** of 100
    3. Initial **inventory**:
        1. 5 `Potion.HP_POTION`
        2. 5 `Potion.MP_POTION`

### Getters
Implement `public int getStat(Stat stat)` and return the current value for the input `stat`.

**Requirements:**
1. stat=Stat.HP: return the `GameCharacter` current HP value
2. stat=Stat.MP: return the `GameCharacter` current MP value

#

Implement `public Map<Potion, Integer> getInventory()`, and return the current `inventory` map.

#

### Setters
Implement `public void setHp(int hp)` and `public void setMp(int mp)` by overriding the HP/MP value.

**Requirements:**
1. HP/MP is bounded by [0, 100]
2. Any values outside of this range should be ignored (no overwrite should happen)

**Let's test what we have up to this point.**

#

### Example 1:
> **Input**:<br>
> ```java
> // Constructor should initialize: hp=100, mp=100, inventory={"HP_POTION": 5, "MP_POTION": 5}
> GameCharacter character = new GameCharacter();
>
> // Validate the default construction of the GameCharacter fields
> int output1 = character.getStat(Stat.HP);
> int output2 = character.getStat(Stat.MP);
> Map<Potion, Integer> output3 = character.getInventory();
> 
> // Validate the HP/MP setting logic (between 0 and 100)
> character.setHp(10000);
> int output4 = character.getStat(Stat.HP); // Expected: 100 (hp should be between 0 and 100
>                                           // we should ignore the previous 10000 set value)
> character.setHp(-10000);
> int output5 = character.getStat(Stat.HP); // Expected: 100 (hp should be between 0 and 100
>                                           // we should ignore the previous -10000 set value)
> 
> character.setMp(10000);
> int output6 = character.getStat(Stat.MP); // Expected: 100 (hp should be between 0 and 100
>                                           // we should ignore the previous 10000 set value)
> 
> character.setMp(-10000);
> int output7 = character.getStat(Stat.MP); // Expected: 100 (hp should be between 0 and 100
>                                           // so we should ignore the previous -10000 set value)
> ```
>
> **Output1**: <span style="color:#0000008c">100<br></span>
> **Output2**: <span style="color:#0000008c">100<br></span>
> **Output3**: <span style="color:#0000008c">{ "HP_POTION": 5, "MP_POTION": 5 }<br></span>
> **Output4**: <span style="color:#0000008c">100<br></span>
> **Output5**: <span style="color:#0000008c">100<br></span>
> **Output6**: <span style="color:#0000008c">100<br></span>
> **Output7**: <span style="color:#0000008c">100</span>

#

### Testing
- [ ] Open `GameCharacterTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GameCharacterTest.java` for the test method: `initTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

#

### takeDamage
Implement `public int takeDamage(int damage)`, where you will reduce the `character` object's HP by the `damage` amount.

Return the updated character `HP` int.

> [!TIP]
>
> Remember: a `GameCharacter` HP has a minimum value of `0`

### Example 1:
> ![](assets/module1/images/takeDamage_50.png)<br>
> **Input**: <span style="color:#0000008c">damage = 50 // Assume HP = 100<br></span>
> **Output**: <span style="color:#0000008c">50</span>

#

### Example 2:
> ![](assets/module1/images/takeDamage_110.png)<br>
> **Input**: <span style="color:#0000008c">damage = 110 // Assume HP = 100<br></span>
> **Output**: <span style="color:#0000008c">0<br></span>
> **Explanation**: <span style="color:#0000008c">A character's HP should never be negative. (min capped at 0)</span>

#

### Testing
- [ ] Open `GameCharacterTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GameCharacterTest.java` for the test method: `takeDamageTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

#

### consumePotion
Implement `public int consumePotion(Potion potion)`, where you will increment the `character` object's HP/MP depending on the potion.

Return the updated character `HP`/`MP` int, or -1 for any errors.

> [!TIP]
>
> Remember: a `GameCharacter` HP has a maximum value of `100`

> [!IMPORTANT]
> 
> Look at how I defined the `Potion.java` enum. Each potion has some helpful methods to tell you what `Stat` should be affected and by how much.

**Rules:**</br>
1. The `GameCharacter` object must have the `Potion` currently in their inventory.
2. `Potion.HP_POTION` should increment the `character` HP by `50`.<br>
   `Potion.MP_POTION` should increment the `character` MP by `50`.
3. Decrement the quantity of the consumed `Potion` by 1 in the `GameCharacter.inventory`
4. Remove the `Potion` from the `character` inventory if the quantity hits `0`.

### Example 1:
> ![](assets/module1/images/consumePotion_HP_25.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 25<br></span>
> **Output**: <span style="color:#0000008c">75</span>

#

### Example 2:
> ![](assets/module1/images/consumePotion_HP_99.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 99<br></span>
> **Output**: <span style="color:#0000008c">100<br></span>
> **Explanation**: <span style="color:#0000008c">A character's HP should never be above 100. (max capped at 100)</span>

#

### Example 3:
> ![](assets/module1/images/consumePotion_MP_25.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.MP_POTION // Assume MP = 25<br></span>
> **Output**: <span style="color:#0000008c">75</span>

#

### Example 4:
> ![](assets/module1/images/consumePotion_MP_99.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.MP_POTION // Assume MP = 99<br></span>
> **Output**: <span style="color:#0000008c">100<br></span>
> **Explanation**: <span style="color:#0000008c">A character's MP should never be above 100. (max capped at 100)</span>

#

### Example 5:
> ![](assets/module1/images/consumePotion_Remove.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 25<br></span>
> **Output**: <span style="color:#0000008c">75<br></span>
> **Explanation**: <span style="color:#0000008c">The Inventory should no longer contain the HP_POTION now that the quantity of this item is 0.</span>

#

### Example 6:
> ![](assets/module1/images/consumePotion_Error.jpg)<br>
> **Input**: <span style="color:#0000008c">potion = Potion.HP_POTION // Assume HP = 50<br></span>
> **Output**: <span style="color:#0000008c">-1<br></span>
> **Explanation**: <span style="color:#0000008c">There are no HP_POTION in the inventory, so we can't consume this potion.</span>

#

### Testing
- [ ] Open `GameCharacterTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GameCharacterTest.java` for the test method: `consumePotionTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

### getCharacterState
Implement `public void getCharacterState() {}`.

Return a `Map<String, Object>` representing the character's current state (**HP**, **MP**, and **INVENTORY**).

### Example 1:
> ![](assets/module1/images/characterState.jpg)<br>
> **Input**
> ```java
> // Assume the character starts with 100 HP and MP, and 5 of each HP/MP potions
> GameCharacter character = new GameCharacter();
> Map<String, Object> output = character.getCharacterState();
>```
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

#

### Testing
- [ ] Open `GameCharacterTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GameCharacterTest.java` for the test method: `getCharacterStateTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

#

### Task 3: Hookup our `GameRestController.java` to our `GameCharacter.java` application logic
Now we should have all the application logic in `GameCharacter.java` to handle the Game API endpoints, we simply need to call them.
![](assets/module1/images/gameController.svg)<br>

In the constructor for `GameRestController.java`, initialize a default `GameCharacter` object.<br>

> [!IMPORTANT]
> 
> When our application runs, the character state changes in that specific run will not be persisted on subsequent application runs. The `GameRestController.java` will always create a "new" GameCharacter object.
> 
> Example:<br>
> → Run the application: `./gradlew bootRun`<br>
> → `GameRestController.java` should init a new `GameCharacter` object<br>
> → Call the `PUT /takeDamage?damage=50` endpoint (the character object's HP should be 50)<br>
> → Quit the application run (Control+C)<br>
> → Run the application (again): `./gradlew bootRun`<br>
> → Call the `GET /characterState` endpoint (the character object's HP will be 100 because `GameRestController` will init a new `GameCharacter` object)

For each of these API endpoints simply call the respective methods we previously implemented in `GameRestController.java`.<br>
Return the result output from each of these `GameRestController.java` function calls back through the HTTP Response.
```java
@PutMapping("/takeDamage")
public int takeDamage(@RequestParam int damage) {
    // Hook up to GameCharacter.takeDamage(...)
}

@PutMapping("/consumePotion")
public int consumePotion(@RequestParam String potionName) {
    // Hook up to GameCharacter.consumePotion(...)
}

@GetMapping("/characterState")
public Map<String, Object> getCharacterState() {
    // Hook up to GameCharacter.getCharacterState()
}
```

### Example 1:
> ```java
> WebClient webClient = WebClient.builder()
>         .baseUrl("http://localhost:8080/api")
>         .build();
> 
> // 1. Get default character state: {HP=100, MP=100, inventory={HP_POTION=5, MP_POTION=5}}
> Map<String, Object> output1 = webClient.get()
>         .uri("/fantasyGame/characterState")
>         .retrieve()
>         .bodyToMono(Map.class)
>         .block();
> 
> // 2. Character takes 70 damage: 100 (current HP) - 70 (damage) = 30 HP
> int output2 = webClient.put()
>         .uri(uriBuilder -> uriBuilder
>                 .path("/fantasyGame/takeDamage")
>                 .queryParam("damage", 70)
>                 .build())
>         .retrieve()
>         .bodyToMono(Integer.class)
>         .block();
> 
> // 3. Character drinks an HP potion: 30 (current HP) + 50 (HP potion) = 80 HP
> int output3 = webClient.put()
>         .uri(uriBuilder -> uriBuilder
>                 .path("/fantasyGame/consumePotion")
>                 .queryParam("potionName", "HP_POTION)
>                 .build())
>         .retrieve()
>         .bodyToMono(Integer.class)
>         .block();
> 
> // 4. Get updated character state: {HP=80, MP=100, inventory={HP_POTION=4, MP_POTION=5}}
> int output4 = webClient.get()
>         .uri("/fantasyGame/characterState")
>         .retrieve()
>         .bodyToMono(Map.class)
>         .block();
> ```
> **Output1**: {"HP": 100, "MP": 100, "INVENTORY": {"HP_POTION": 5, "MP_POTION": 5}<br>
> **Output2**: 30<br>
> **Output3**: 80<br>
> **Output4**: {"HP": 80, "MP": 100, "INVENTORY": {"HP_POTION": 4, "MP_POTION": 5}

#

### Testing
- [ ] Open `GameRestControllerTest.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `GameRestControllerTest.java`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module1
```

#

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play around with the **Character API** endpoint(s)

![](assets/module1/images/gameSwagger.png)<br>
