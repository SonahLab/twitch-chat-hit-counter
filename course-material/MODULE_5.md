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

## [Getting Started with the Twitch API <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/docs/api/get-started/)
1. Create a [Twitch.tv <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://www.twitch.tv/) account
2. Login to [Twitch Developers Console <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console)
3. Under **Applications**, click on [Register Your Application <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/console/apps/create).
   - **Name**: `Chat Hit Counter`
   - **OAuth Redirect URLs**: `http://localhost:8080/oauth2/callback`
   - **Category**: `Chat Bot`
   - **Client Type**: `Confidential`

![](assets/module5/images/twitch_createApplication.png)<br>

4. You should now see your application created. Click **Manage**.

5. Create a **New Secret**. Copy both of the **Client ID** and the **Client Secret**. This will be needed to access the Twitch API in our application.

![](assets/module5/images/twitch_keys.png)<br>

<br>

## Exercise 1: Add Keys to Application
In `twitchApiKey.yml`, set the **twitch.client-id** and **twitch.client-secret** properties retrieved from the **Twitch Developer Console**.<br>
In `TwitchConfig.java`, create two @Beans that will return the clientId and clientSecret.

This yaml file is already added in **.gitignore**, so your keys will not and should not be published to Github.

### Testing
- [ ] Open `TwitchConfigTest.java` ─ already implemented
- [ ] Remove `@Disabled` in `TwitchConfigTest.java` for method(s): `testTwitchClientKeys()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
```

<br>

#

<br>

> [!TIP]
>
> When integrating with 3P APIs there is always a bit of discovery work involved. Whether it's a publicly exposed API or a private API (direct B2B integration),
> I always spend a good chunk of time understanding how to integrate with the API and what data/endpoints are available. The same learning process was required to understand Twitch API.

## Exercise 2: OAuth Tokens
![](assets/module5/images/oauth_lifecycle.svg)<br>

> [!IMPORTANT]
>
> Here are some really important docs to read through on how Twitch API auth tokens work
> - https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/#authorization-code-grant-flow
> - https://dev.twitch.tv/docs/authentication/refresh-tokens
> - https://dev.twitch.tv/docs/authentication/validate-tokens/

#### Twitch API's authentication flow:<br>
**1a.** **Get the user to authorize your app**. In a browser you would go to:<br>
```
https://id.twitch.tv/oauth2/authorize
    ?response_type=code
    &client_id={YOUR_CLIENT_ID}
    &redirect_uri=http://localhost:8080/oauth2/callback
    &scope=chat:read
```
**1b.** If the user authorized your app by clicking **Authorize**, Twitch server sends the authorization code to your `redirect_uri`.<br>
Your application needs to be running and needs to be listening to an API endpoint at `GET /oauth2/callback` to handle Twitch's redirect.<br>
**2a.** use the authorization code to get an access token and refresh token.
```
POST https://id.twitch.tv/oauth2/token
{
    "client_id": {YOUR_CLIENT_ID},
    "client_secret": {YOUR_CLIENT_SECRET},
    "code": {YOUR_AUTHORIZATION_CODE},
    "grant_type": "authorization_code",
    "redirect_uri": "http://localhost:8080"
}
```
**2b.** If the request succeeds, Twitch's servers returns an access token and refresh token.
```json
{
  "Token": {
    "access_token": {access_token},
    "expires_in": 14193,
    "refresh_token": {refresh_token},
    "scope": [
      "chat:read"
    ],
    "token_type": "bearer"
  }
}
```
Think of `access_token` as a **temporary key** that Twitch asks you everytime you want access to their API endpoints.
The access_token has a specified TTL (Time to Live), meaning after that time in seconds has passed from issuance of the access_token (key), it will no longer be accepted by Twitch servers, and further requests will be rejected.<br>
**3a.** If our token expires, we need to refresh the `access_token` by issuing another token call using our `refresh_token`:
```
POST https://id.twitch.tv/oauth2/token
{
    "client_id": {YOUR_CLIENT_ID},
    "client_secret": {YOUR_CLIENT_SECRET},
    "grant_type": "refresh_token",
    "refresh_token": {YOUR_REFRESH_TOKEN}
}
```
**3b.** If the request succeeds, the response contains the new access token, refresh token, and scopes associated with the new grant.
This will give us an output similar to Twitch's response in **step 4**.

<br>

#

### Task 1: OAuth Authorize API
#### Part 1a
In `TwitchAuthService.java`, implement `public String getAuthUrl()`.

Return the URL string that will be used to ping the `https://id.twitch.tv/oauth2/authorize`.

The URL will need to fill in the fields in this template:
```
https://id.twitch.tv/oauth2/authorize
    ?response_type=code
    &client_id={client_id}
    &redirect_uri={redirect_uri}
    &scope={scope(s)}
    &state={state}
```

**Requirements:**
1. Inject the **client_id** from the `twitchApiKey.yml` loaded @Bean
2. Set the **redirect_url** to: `http://localhost:8080/oauth2/callback`
3. Set **scope** to: `chat:read`
4. Generate a generic UUID to set as our **state**

### Testing
- [ ] Open `TwitchAuthServiceTest.java` ─ already implemented to test that the requirements above are filled.
- [ ] Remove `@Disabled` in `TwitchAuthServiceTest.java` for the test method(s): `getAuthUrlTest()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
```

#

#### Part 2
In `OAuthRestController.java`, implement `public String getAuthUrl()`. This method should just directly call the `TwitchAuthManager.getLoginUrl()`.

<br>

#

### Task 2: OAuth Callback API
In `OAuthRestController.java`, implement:
```java
@GetMapping("/oauth2/callback")
public Map<String, String> handleCallback(
    @RequestParam(name = "code", required = false) String code,
    @RequestParam(name = "scope", required = false) String scope,
    @RequestParam(name = "state", required = false) String state,
    @RequestParam(name = "error", required = false) String error,
    @RequestParam(name = "error_description", required = false) String errorDescription)
```
Return a `Map<String, String>` of all the parameters that were passed into this method from Twitch when it redirects to this `GET /oauth2/callback` endpoint.

> From [Twitch Docs <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/#authorization-code-grant-flow):<br>
> _If the user authorized your app by clicking **Authorize**, the server sends the **authorization code** to your redirect URI (see the code query parameter):_
> ```
> http://localhost:3000/
>   ?code=gulfwdmys5lsm6qyz4xiz9q32l10
>   &scope=channel%3Amanage%3Apolls+channel%3Aread%3Apolls
>   &state=c3ab8aa609ea11e793ae92361f002671
> ```
> 
> _If the user didn’t authorize your app, the server sends the error code and description to your redirect URI (see the error and error_description query parameters):_
> ```
> http://localhost:3000/
>   ?error=access_denied
>   &error_description=The+user+denied+you+access
>   &state=c3ab8aa609ea11e793ae92361f002671
> ```
> 
> **TL;DR**:<br>
> If `/oauth2/authorize`:<br>
> → SUCCESS, you can expect Twitch to ping our server's `/oauth2/callback` endpoint w/: `code`, `scope`, and `state`.<br>
> → FAIL, you can expect Twitch to ping our server's `/oauth2/callback` endpoint w/: `error`, `error_description`, and `state`.

### Example 1:
> **Input:**<br>
> ```
> Twitch servers send our application a request:
> http://localhost:8080/oauth2/callback
>     ?code=access_token123
>     &scope=channel%3Aread
>     &state=stateUUID_1
>
> Map<String, String> output = handleCallBack("gulfwdmys5lsm6qyz4xiz9q32l10", scope="channel%3Amanage%3Apolls+channel%3Aread%3Apolls", state="c3ab8aa609ea11e793ae92361f002671", null, null);
> ```
> **Output:**<br>
> ```json
> {
>   "code": "access_token123",
>   "scope": "channel%3Aread",
>   "state": "stateUUID_1",
>   "error": null,
>   "error_description": null
> }
> ```

### Example 2:
> **Input:**<br>
> ```
> Twitch servers send our application a request:
> http://localhost:8080/oauth2/callback
>     ?error=access_denied
>     &error_description=The+user+denied+you+access
>     &state=stateUUID_2
>
> Map<String, String> output = handleCallBack("gulfwdmys5lsm6qyz4xiz9q32l10", scope="channel%3Amanage%3Apolls+channel%3Aread%3Apolls", state="c3ab8aa609ea11e793ae92361f002671", null, null);
> ```
> **Output:**<br>
> ```json
> {
>   "code": null,
>   "scope": null,
>   "state": "stateUUID_2",
>   "error": "access_denied",
>   "error_description": "The+user+denied+you+access"
> }
> ```

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
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play with the **Twitch API**: `GET /oauth2/authorize`
- [ ] Kick off the authorization by accessing your browser and entering the returned URL from `GET /oauth2/authorize`
- [ ] Twitch will ask you to authorize your User Account, and when granted will return the `authorization_code` to your `/oauth2/callback` endpoint
- [ ] Twitch should also have redirected your browser to `http://localhost:8080/oauth2/callback` endpoint displaying the `handleCallback()` output
- [ ] Check your application logs to verify that you were able to fetch:
  - the authorization_code (`code`)
  - the **same** `state` that was filled into the authorize URL from `GET /oauth2/authorize`
  - the **same** `scope` of "chat:read" that was filled into the authorize URL from `GET /oauth2/authorize`

<br>

#

### Task 3: OAuth Token API
#### Part 1
When Twitch sends us the **authorization code** (`code`), we can use it to create a token. Authorization codes usually have a short lifespan, so it's recommended that the server exchanges it for an **access token** quickly. 

In `TwitchAuthService.java`, implement `public String createOAuthToken(String authorizationCode)`.

Return the String representation of a Map<String, String> payload from creating the token and add a log to print to _**stdout**_ the result from Twitch.

**Requirements:**
- Send an HTTP POST request to `https://id.twitch.tv/oauth2/token` following the [Twitch API document guidelines <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://dev.twitch.tv/docs/authentication/getting-tokens-oauth/#authorization-code-grant-flow).
- Set the request header contentType to `'x-www-form-urlencoded'`.
- Set the required parameters in the body: `client_id`, `client_secret`, `code`, `grant_type`, and `redirect_url`

### Example 1:
> **Input:**<br>
> ```java
> TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> String output = twitchAuthService.createOAuthToken("access_token123");
> ```
> **Output:**<br>
> ```json
> {
>   "access_token": "rfx2uswqe8l4g1mkagrvg5tv0ks3",
>   "expires_in": 14124,
>   "refresh_token": "5b93chm6hdve3mycz05zfzatkfdenfspp1h1ar2xxdalen01",
>   "scope": [
>     "chat:read"
>   ],
>   "token_type": "bearer"
> }
> ```
> **Explanation**: `createOAuthToken()` is exchanging the **authorization code** for a temporary `access_token` with a 4 hour TTL.

#

### Example 2:
> **Input:**<br>
> ```java
> TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> String output = twitchAuthService.createOAuthToken("invalid_access_token123");
> ```
> **Output:**<br>
> ```json
> {
>   TODO with the invalid auth code (expired)
> }
> ```
> **Explanation**: `createOAuthToken()` fails to exchange the invalid **authorization code** for a valid `access_token`.


### Testing
- [ ] Open `TwitchAuthServiceTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `TwitchAuthServiceTest.java` for the test method(s): `TODO()` and `TODO()`
- [ ] Test with:
```shell
./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
```

# TODO need to actually play around with /token to see what happens

#

#### Part 2
In `OAuthRestController.java`, update `handleCallback()` to call `TwitchAuthService.createOAuthToken()` using the same `code` that Twitch passes back to our server.

#

#### Part 3
In `OAuthRestController.java`, implement `public Map<String, String> handleToken(String jsonMAP?)`. Simply return the Map of all the fields similar to handlecallback so we can see it in the UI.

### Integration Testing
- [ ] Run the application:
```shell
./gradlew bootRun
```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play with the **Twitch API**: `GET /oauth2/authorize`
- [ ] Kick off the authorization by accessing your browser and entering the returned URL from `GET /oauth2/authorize`
- [ ] Twitch will ask you to authorize your User Account, and when granted will return the `authorization_code` to your `/oauth2/callback` endpoint
- [ ] Twitch should also have redirected your browser to `http://localhost:8080/oauth2/callback` endpoint displaying the `handleCallback()` output
- [ ] Check your application logs to verify that you were able to fetch:
    - the authorization_code (`code`)
    - the **same** `state` that was filled into the authorize URL from `GET /oauth2/authorize`
    - the **same** `scope` of "chat:read" that was filled into the authorize URL from `GET /oauth2/authorize`
    - the response from issuing the `POST /oauth2/token` HTTP request
- [ ] Open up `http://localhost:8080/oauth2/token` in a new browser tab, you should see the returned value of your `createOAuthToken()`

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

### Task 3: OAuth Refresh Token API
When your Auth Token expires, we will need to refresh our auth token. Tokens have a TTL (Time To Live) of 14193 seconds, or ~4 hours.<br>
To access parts of the API we usually pass in our temporary `access_token`, but when that temporary token expires we need to refresh.
This is done by issuing a call to Twitch's servers `https://id.twitch.tv/oauth2/token` endpoint with the `refresh_token`, in order to
fetch a new temporary `access_token`.

In `TwitchAuthManager.java`, implement `public String refreshOAuthToken(String refreshToken)`.

Return the String representation of a Map<String, String> payload from refreshing the token and add a log to print to _**stdout**_ the result from Twitch.

### Example 1:
> **Input**:<br>
> ```java
> TwitchAuthManager twitchAuthManager = new TwitchAuthManager(...);
> String output = twitchAuthManager.refreshOAuthToken("refresh_token123");
> ```
> **Output**:<br>
> ```json
> {
>   TODO
> }
> ```

#

### Testing
- [ ] TODO

### Integration Testing
- [ ] TODO

<br>

#

### Task 4: OAuth Validate Token API
We need to have some way of health checking our current temporary `accessToken` to check that it's still alive.
Twitch API exposes this endpoint `GET https://id.twitch.tv/oauth2/validate` for us to do just this.

In `TwitchAuthManager.java`, implement `public boolean validateOAuthToken(String accessToken)`.
Send a GET HTTP request to the twitch endpoint using our temporary `accessToken`.

Return a boolean on whether Twitch's `/oauth2/validate` endpoint returns a 200 response, signifying that our temporary token is still alive.<br>
Also log Twitch's entire HTTP response.

### Example 1:
> **Input:**<br>
> ```java
> TwitchAuthManager twitchAuthManager = new TwitchAuthManager(...);
> boolean output1 = twitchAuthManager.validateOAuthToken("access_token123");
> 
> twitchAuthManager.createOAuthToken("access_token123");
> boolean output2 = twitchAuthManager.validateOAuthToken("access_token123");
> ```
> **Output1**: false<br>
> **Explanation**: The OAuth token isn't granted initially
> 
> **Output2**: true<br>
> **Explanation**: The OAuth token is granted, and then validated.

#

### Testing
- [ ] TODO

#

### Integration Testing
- [ ] TODO

<br>

#

### Task 5: Token Redis DB
Now that we have the ability to (1) create, (2) refresh, and (3) validate a token, we want to store the User token in Redis.<br>
We need a persistent way of caching a User's latest User Token, so that we can create the token once and then refresh as needed upon expiration.<br>
If we never store the User Token, we would need to follow the `/authorize` login flow everytime our application starts up.

In `OAuthRedisService.java`, implement:
1. `updateLatestToken(String username, String tokenResponse)` to set the token passed back by Twitch stored at a key
2. `getLatestToken(String username)` to fetch the latest token stored at a key.

**Requirements:**
1. Create a new Spring property for `twitch-chat-hit-counter.redis.oauth-token-database: 3`
2. Create the beans needed to communicate with `db3` in `RedisConfigs.java`
3. Key template: `"twitch#oauth#{userId}"`
4. Value should be a JSON String of a Map<String, String> of the token metadata response passed back from Twitch's `/oauth2/token` endpoint

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

#### Part 2
Now that we have the ability to store Tokens in Redis, we need to trigger this logic.

In `TwitchAuthManager.java`, update the methods below to update our User's latest User Token in Redis:
1. `public String createOAuthToken(String accessToken)`
2. `public String refreshOAuthToken(String refreshToken)`

**Requirements**:<br>
1. If and only if Twitch's `/oauth2/token` endpoints return a successful response of a newly minted token or a refreshed token should we update the User Token in Redis

### Example 1:
TODO example of successful overwrite

#

### Example 2:
TODO Example of non successful call to /token so we skip overwrite

#

### Testing
- [ ] TODO

### Integration Testing
- [ ] TODO

<br>

#

### Exercise 3: Twitch Chat Connection
Now that we have a working OAuth Token cache, we will be mostly focusing on the various Twitch API endpoints we want to integrate with.<br>
It's important to see what products data is even supported publicly and reverse engineer what products you can build out of free data.<br>
In small projects using public API data, you first look at the data available, see what product you can build from it.

Since we want to build a hit counter on different streamers, we will need to learn how to integrate with the Twitch Chat.<br>
https://dev.twitch.tv/docs/chat/send-receive-messages/

We will focus mainly on receiving Twitch Chat message. The API docs details on different way of connecting to Chats using Webhooks and Websockets,
but that is outside the scope of this project and, tbh, outside of Networking/OS classes I've never once used these in my daily life as a SWE.
To simplify the ease of integration we will leverage a great Java client library that abstracts away much of the lower level details for us. The library is [Twitch4J <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/).<br>

Spend time reading through the Twitch Chat section.

### Task 1: Twitch4J Client
#### Part 1
In `TwitchChatService.java`, implement the `public TwitchChatService()` post-constructor. We will need to build Twitch4J's `TwitchClient` object that acts as the underlying client to all of Twitch API's endpoints.

**Requirements:**
1. Initialize the `TwitchClient` object with our Twitch API ClientId, Client Secret, and OAuth Token.
2. Helix should be enabled
3. Chat should be enabled
4. Chat Account should be set using the OAuthCredentials

```java
TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .build();
```

<br>

#

#### Part 2
In `TwitchChatService.java`, implement `public void joinChannel(String channelName)`. This method will allow our User account to join a Twitch stream by the `channelName`.

> [!IMPORTANT]
> 
> Before using TwitchClient to join a channel we need to validate OAuth Token and refresh it if invalid.

#

#### Part 3
In `TwitchChatService.java`, implement `public boolean leaveChannel(String channelName)`. This method will allow our User account to leave a Twitch stream by the `channelName`.

Returns boolean on whether you successfully left the twitch channel (if User account was connected).

> [!IMPORTANT]
>
> Before using TwitchClient to leave a channel we need to validate OAuth Token and refresh it if invalid.

#

#### Part 4
Define the `TwitchChatEvent.java` record.

Twitch4J library has it's own POJO object, but this class has a lot of additional metadata fields that we won't need for our project.
We will define our own POJO `TwitchChatEvent.java`, which is a simplified version of Twitch4J's `ChannelMessageEvent` object.

**Requirements:**
1. `eventId`: (String) eventId for the ChannelMessageEvent
2. `message`: (String) message content that the user sent to the chat
3. `eventTs`: (long) timestamp millis of when the chat message was sent
4. `user`: (EventUser)
   - id: (String) user id on Twitch of the sender of the message
   - name: (String) username on Twitch of the sender of the message
5. `channel`: (EventChannel)
   - id: (String) channel id on Twitch
   - name: (String) channel name on Twitch
6. `subscriptionMonths`: (int) months the user who is chatting has been subscribed to the channel for
7. `subscriptionTier`: (int) tier level of the user who is chatting has subscribed to (tier1, 2, 3)

#

#### Part 5
In `TwitchChatService.java`, implement `public void handleMessage(ChannelMessageEvent channelMessageEvent)`.<br>

This method will be the main handler for the incoming real-time twitch chat messages. Twitch4J will pass in the `ChannelMessageEvent`.
We will need to create an instance of the `TwitchChatEvent.java` from the passed in event.
All the required fields we defined in the schema for `TwitchChatEvent.java` are already contained in the schema of Twitch4J's `ChannelMessageEvent`.

Your goal is to simply, for now, log the simplified `TwitchChatEvent` to **stdout**.

> [!TIP]
> 
> https://twitch4j.github.io/events/channel-message-event#write-chat-to-console
> You can set up EventHandlers in the @PostConstructor method so our application can actually receive and process message events once we join a twitch channel.

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatService service = new TwitchChatService(...);
> service.handleMessage(channelMessageEvent);
> ```
> **stdout**:<br>
> 

### Testing

### Integration Testing





### Exercise 4: Kafka
Now that we can connect to Twitch chats successfully, we need to build a Kafka producer/consumer to publish these `TwitchChatEvent` to a new separate kafka topic.

This will look very similar to the end state we had in **Module 2** with the Producer/Consumer on the `greeting-events` kafka topic.<br>
This exercise will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. In **Offset Explorer 3**, create a new kafka topic named `twitch-chat-events` 
2. Stream the incoming chat messages from a channel using Twitch4J's `TwitchClient`
3. Publish `TwitchChatEvent` to `twitch-chat-events` topic
4. Consume `TwitchChatEvent` from `twitch-chat-events` topic and log them to **stdout**

The main differences from Module 2 is the producer logic trigger. In Module 2, we manually triggered the `POST /api/kafka/publishGreetingEvent` endpoint via Swagger to publish events.
In this exercise, our `TwitchChatService.handleMessage()` method is the trigger for the `TwitchChatEventProducer.java`. Once we join a channel and attach the event handler method,
the TwitchClient will stream, in real-time, the incoming chat messages that we need to publish to `twitch-chat-events` topics.

### Testing

### Integration Testing

<br>

### Exercise 5: SQL
Now that we are able to stream Twitch chat events and pub/sub events through our new kafka topic, we need to write the `TwitchChatEvent` to a new separate SQL table.

This will look very similar to the end state we had in **Module 3**. This exercise description will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. In **MySQLWorkbench**, create a new SQL table named `twitch_chat_events`
2. Have `TwitchChatEventConsumer.java` write the `TwitchChatEvent` to this SQL table

### Testing

### Integration Testing

<br>

### Exercise 6: Redis Hit Counter
This will look very similar to the end state we had in **Module 4**. This exercise description will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. Deduplicate `TwitchChatEvent` so we don't process duplicates using the same `db0` we already have
2. Aggregate chat message count at the minutely grain in a new Redis `db4`

> [!TIP]
> 
> In Module 4, we built Redis `db1` to be the Greetings New Feed Database. Since we will be building a Twitch Chat Hit Counter our `db4` will look a little different.
> 
> In `db4`, this will be our schema:
> Key (String): `"{channelName}#{minuteBoundaryInMillis}"`
> Value (Long): # of chat messages that fall into the minute bucket (rounded down the nearest minute bucket)

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> TwitchChatRedisService service = new TwitchChatRedisService(redisDao);
> 
> long eventTs1 = 1767254439000; // Thu Jan 01 2026 08:00:39 GMT+0000
> long eventTs2 = 1767254445000; // Thu Jan 01 2026 08:00:45 GMT+0000
>
> long output1 = service.incrementMinuteHitCounter("s0mcs", eventTs1);
> long output2 = service.incrementMinuteHitCounter("s0mcs", eventTs2);
> Map<String, String> output3 = service.getHitCount("s0mcs");
> ```
> **Output1**: 1<br>
> **Explanation**: Count should be incremented to {"s0mcs#1767254400000": 1}
> 
> **Output2**: 2<br>
> **Explanation**: Count should be incremented to {"s0mcs#1767254400000": 2}
>
> **Output3**:<br>
> ```json
> {
>   "s0mcs#1767254400000": 2
> }
> ```
> **Explanation**:<br>
> After we increment the hit count for s0mcs's channel with the timestamp at 1767254439000, the timestamp bucket gets rounded down to the nearest minute boundary timestamp.<br>
> After we increment the hit count for s0mcs's channel with the timestamp at 1767254445000, the timestamp bucket gets rounded down to the nearest minute boundary timestamp.<br>
> When we fetch the hit count for s0mcs's channel, we will see we have only one key at key="s0mcs#1767254400000"

### Testing

### Integration Testing