# Practical Backend Engineer
## Twitch Chat Hit Counter
## Module 5: Twitch API

### Lesson


### Additional Learning Materials



<br>

## File Structure
For `Module 5`, the below file structure are all the relevant files needed.

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
<img src="assets/common/class.svg" align="center"/> TwitchConfig.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> kafka/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> consumer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEventConsumer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> producer/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEventProducer.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> model/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatEvent.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> dao/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> RedisDao.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> EventDeduperRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatRedisService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatSqlService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatBotManager.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> utils/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/enum.svg" align="center"/> EventType.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/resourcesRoot.svg" align="center"/> resources/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/yaml.svg" align="center"/> application.yml<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/yaml.svg" align="center"/> twitchApiKey.yml<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/folder.svg" align="center"/> test/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testRoot.svg" align="center"/> java/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> com.sonahlab.twitch_chat_hit_counter/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> config/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchConfigTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> redis/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatRedisServiceTest.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatSqlService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/testClass_newui.svg" align="center"/> TwitchChatBotManagerTest.java<br>

<br>

## Overview
![](assets/module5/images/Overview.svg)<br>

In **Module 5**, we will now be integrating with the public Twitch API. Instead of triggering the pipeline through HTTP requests, we will setup a pipeline to stream realtime chat messages from Twitch Chat.
We will use everything we've learned up to this point to accomplish this.

<br>

## Create Kafka Topic

<br>

## Create SQL Table

<br>

## Access Twitch API
1. Create a [Twitch.tv <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.twitch.tv/) account
2. Login to [Twitch Developers Console <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console)
3. Under **Applications**, click on [Register Your Application <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console/apps/create).
   - **Name**: `Chat Hit Counter`
   - **OAuth Redirect URLs**: `http://localhost:8080/oauth2/callback`
   - **Category**: `Chat Bot`
   - **Client Type**: `Confidential`

![](assets/module5/images/twitch_createApplication.png)<br>

4. You should now see your application created. Click **Manage**.

5. Create a **New Secret**. Copy both of the **Client ID** and the **Client Secret**. This will be the key/lock we need to access the Twitch API in our application.

![](assets/module5/images/twitch_keys.png)<br>

<br>

## Exercise 1: Add Keys to Application
In `twitchApiKey.yml`, setup the **twitch.client-id** and **twitch.client-secret** properties.
Afterwards, `TwitchConfig.java`, create two @Beans that will return the clientId and clientSecret.

This yaml file is already added in **.gitignore**, so your keys will not and should not be published to Github.

<br>

#

<br>

### Exercise 2: Auth Token
https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/

https://dev.twitch.tv/docs/authentication/refresh-tokens

When dealing with 3P APIs there is always a bit of discovery work involved. Whether it's a publicly exposed API or a private API (direct B2B integration),
I always spend a good chunk of time understanding how to integrate with the API and what data/endpoints are available.

The same learning process was required to even write this section up and understanding Twitch API's authentication flow, how to connect to twitch chat, how to get user/channel info.

Twitch API is public but requires a combination of clientId, clientSecret, or a valid OAuth token in order for Twitch to accept the incoming requests.

I will detail Twitch API's authentication flow:
1. User account needs to authorize the Twitch API in order to fetch an OAuth token
```
https://id.twitch.tv/oauth2/authorize
    ?response_type=code
    &client_id={client_id}
    &redirect_uri={redirect_uri}
    &scope={scopes}

https://id.twitch.tv/oauth2/authorize
    ?response_type=code
    &client_id={client_id}
    &redirect_uri=http://localhost:8080/oauth2/callback
    &scope=chat:read
```
2. Twitch will redirect this request over to the `{redirect_uri}` which we set to http://localhost:8080/oauth2/callback and send us the **access_token**.
This means our application needs to be running and needs an API endpoint at `GET /oauth2/callback` to handle Twitch's redirect.
3. We will need to create a valid token by hitting:
```
POST https://id.twitch.tv/oauth2/token
    Body: {
        "client_id": {client_id},
        "client_secret": {client_secret},
        "code": {access_token},
        "grant_type": "authorization_code",
        "redirect_uri": "http://localhost:8080"
    }
```
4. Twitch will accept this request and grant a new token:
```json
{
  "Token": {
    "access_token": {oauth_token},
    "expires_in": 14193,
    "refresh_token": {refresh_token},
    "scope": [
      "chat:read"
    ],
    "token_type": "bearer"
  }
}
```
Twitch has granted us a token that our application can now use to perform various API calls they expose to the public. The token has a TTL of 14193 seconds (~4 hrs).
Accessing Twitch API will work until the key expires, then Twitch will stop accepting our requests.
5. If our token expires, we need to refresh the token by issuing another token call:
```
POST https://id.twitch.tv/oauth2/token
    Headers: {
        "Content-Type": "application/x-www-form-urlencoded"
    }
    Body: {
        "client_id": {client_id},
        "client_secret": {client_secret},
        "grant_type": "refresh_token",
        "refresh_token": {refresh_token}
    }
```
This will give us an output similar to Twitch's response in **step 4**.

So we will kickoff the initial /authorize by manually entering logging in, and provisioning a valid oauth token.
Everytime it expires (Twitch's guidance is not to rely on the 14193 second TTL, but to refresh on a 4xx unauthorized error),
we will refresh our token.

DIAGRAM HERE

Question: Imagine our service was serving millions of users, what is the best way to statefully store the User <-> Token?

We can use another Redis DB to keep track of this information for a User's latest token. If a User has a valid token at day0,
and comes back at day365 we will attempt to access Twitch API with the token at day0, realize the call failed, refresh the token,
and update that User's token information in Redis.

### Task 1: OAuth Callback API
In `OAuthRestController.java`, implement:
```java
@GetMapping("/oauth2/callback")
public String handleCallback(
    @RequestParam(name = "code", required = false) String code,
    @RequestParam(name = "scope", required = false) String scope,
    @RequestParam(name = "state", required = false) String state,
    @RequestParam(name = "error", required = false) String error,
    @RequestParam(name = "error_description", required = false) String errorDescription)
```
Return a `Map<String, String>` of all the fields that were passed back from Twitch when it redirects to this `GET /oauth2/callback` endpoint.

In the happy path, we will get passed back a code, scope, and state parameters.<br>
In the error path, we will get passed back an error, error_description, and state parameters.

**Requirements:**
1. If error:
   1. Log the error, error_description, and state
2. If Success:
   1. Log the code, scope, and state

### Example 1:
```
Twitch servers send our application a request:
http://localhost:8080/
    ?code=gulfwdmys5lsm6qyz4xiz9q32l10
    &scope=channel%3Amanage%3Apolls+channel%3Aread%3Apolls
    &state=c3ab8aa609ea11e793ae92361f002671

Map<String, String> output = 
```

<br>

#

### Testing
- [ ] Open `OAuthRestControllerTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `OAuthRestControllerTest.java` for the test method(s): `handleCallbackSuccessTest()` and `handleCallbackErrorTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
```

#

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Kick off the authorization by accessing your browser and going to:
`https://id.twitch.tv/oauth2/authorize?response_type=code&client_id={client_id}&redirect_uri=http://localhost:8080/oauth2/callback&scope=chat:read`
- [ ] Twitch should call this endpoint logic and display the `Map<String, String>` we returned

<br>

#

### Task 2: OAuth Token API
Now that we are able to get back an `access_token` from Twitch, we want to create our OAuth token.

In `TwitchApiClient.java`, implement `public String getOAuthToken(String accessToken)`. This method needs to will expect the same `access_token` that Twitch servers send back over.

Return the Map<String, String> payload from creating the token and add a log to print to _**stdout**_ the result from Twitch.

You will need to send an HTTP POST request `https://id.twitch.tv/oauth2/token` following the Twitch API document guidelines.

In `OAuthRestController.java`, you will need to call this method within `handleCallback()`.

### Example 1:
```java

```

### Testing

#

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Kick off the authorization by accessing your browser and going to:
  `https://id.twitch.tv/oauth2/authorize?response_type=code&client_id={client_id}&redirect_uri=http://localhost:8080/oauth2/callback&scope=chat:read`
- [ ] Twitch should call this endpoint logic and display the `Map<String, String>` we returned
- [ ] You should see in your application logs the Token that Twitch created for our application to use

<br>

#

### Task 3: OAuth Token Refresh API
When your Auth Token expires, we will need to refresh our auth token.

In `TwitchApiClient.java`, implement `refreshOAuthToken(String refreshToken)`.

<br>

#

### Task 4: Token Redis DB
Now that we have the ability to (1) create or (2) refresh a token, we want to store the User token in Redis.

In `OAuthRedisService.java`, implement `updateLatestToken(String username, String tokenResponse)` to set the token passed back by Twitch stored at a key, and `getLatestToken(String username)`
to fetch the latest token stored at a key.

**Requirements:**
1. Create a new Spring property for `twitch-chat-hit-counter.redis.oauth-token-database: 3`
2. Create the beans needed to communicate with db3 in `RedisConfigs.java`
3. Key template: `"twitch#oauth#{userId}"`
4. Value should be a JSON String with the payload passed back from Twitch's `/oauth2/token`
5. Either calls to `updateOAuthToken()` or `refreshOAuthToken()` should call `updateLatestToken()`

> [!NOTE]
>
> Since this app is simply used by our application and your Twitch account will be used to grant tokens, just replace the {userId} in the key template with your own Twitch account username.<br>
> It is possible to retrieve your account metadata through the Twitch API, but for now let's keep it simple, as we're dealing with multiple Twitch API endpoints already.

### Example 1:
> ```java
> OAuthRedisService oAuthRedisService = new OAuthRedisService(...);
> String output1 = oAuthRedisService.getLatestToken("Alice");
>
> // Assume we called POST https://id.twitch.tv/oauth2/token
> String tokenResponse = """
>         {
>           "access_token": "1ssjqsqfy6bads1ws7m03gras79zfr",
>           "refresh_token": "eyJfMzUtNDU0OC4MWYwLTQ5MDY5ODY4NGNlMSJ9%asdfasdf=",
>           "scope": [
>             "channel:read:subscriptions",
>             "channel:manage:polls"
>           ],
>           "token_type": "bearer"
>         }
>         """;
> oAuthRedisService.updateLatestToken("Alice", tokenResponse);
>
> String output2 = oAuthRedisService.getLatestToken("Alice");
> ```
> **Output1**:""<br>
> **Explanation**: Alice doesn't have a token yet cached in Redis
>
> **Output2**:<br>
> ```json
> {
>   "access_token": "1ssjqsqfy6bads1ws7m03gras79zfr",
>   "refresh_token": "eyJfMzUtNDU0OC4MWYwLTQ5MDY5ODY4NGNlMSJ9%asdfasdf=",
>   "scope": [
>     "channel:read:subscriptions",
>     "channel:manage:polls"
>   ],
>   "token_type": "bearer"
> }
> ```
> **Explanation**: Alice's account retrieves the latest cached token from Redis

### Exercise 3: Twitch Chat Connection
Now that we have a working OAuth Token cache, we will be mostly focusing on the various Twitch API endpoints we want to integrate with.<br>
It's important to see what products data is even supported publicly and reverse engineer what products you can build out of free data.<br>
In small projects using public API data, you first look at the data available, see what product you can build from it.

Since we want to build a hit counter on different streamers, we will need to learn how to integrate with the Twitch Chat API endpoints.<br>
https://dev.twitch.tv/docs/chat/send-receive-messages/

We will focus mainly on receiving Twitch Chat message. The API docs details on different way of connecting to Chats using Webhooks and Websockets,
but in order to simplify the ease of integration we will leverage a great Java client library that abstracts away much of the lower level details for us. The library is [Twitch4J <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/).<br>

### Task 1: Twitch4J Client
In `TwitchApiClient.java`, implement the `public TwitchApiClient()` constructor. We will need to build Twitch4J's `TwitchClient` object that acts as the underlying client to all of Twitch API's endpoints.

**Requirements:**
1. Initialize the `TwitchClient` object with our Twitch API ClientId, Client Secret, and OAuth Token.
2. Helix should be enabled

```java
TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .build();
```

<br>

### Exercise 4: Kafka

<br>

### Exercise 5: SQL

<br>

### Exercise 6: Redis Hit Counter

