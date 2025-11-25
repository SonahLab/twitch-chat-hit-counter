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
<img src="assets/common/package.svg" align="center"/> rest/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> OAuthRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchRestController.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> sql/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchChatSqlService.java<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/package.svg" align="center"/> twitch/<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="assets/common/class.svg" align="center"/> TwitchAuthService.java<br>
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
<img src="assets/common/yaml.svg" align="center"/> twitch-key.properties<br>
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

## Create Kafka Topic: `twitch-chat-events`
1. Navigate to the _**Clusters/twitch-chat-hit-counter/Topics**_ folder
2. Click '+' to add a new kafka topic
3. Input kafka topic configs:
    1. **Topic name**: twitch-chat-events<br>
    2. **Partition Count**: 3<br>
    3. **Replica Count**: 1
4. Select our kafka topic in **_Clusters/twitch-chat-hit-counter/Topics/twitch-chat-events_**
5. Change the **Content Types** for the key and value from **'Byte Array'** → **'String'**, and save by clicking **Update**.

![](assets/module2/create_topic.gif)<br>

<br>

## Create SQL Table: `dev_db.twitch_chat_events`
1. Click on **Schemas** tab
2. Navigate to **dev_db** → **Tables**
3. In the **SQL Editor**, run:
```
CREATE TABLE dev_db.twitch_chat_events (
    event_id VARCHAR(255) PRIMARY KEY,
    message TEXT,
    eventTs BIGINT,
    user_id VARCHAR(255),
    username VARCHAR(255),
    channel_id VARCHAR(255),
    channel_name VARCHAR(255),
    subscription_months INT,
    subscription_tier INT
)
```
![](assets/module3/images/mysqlworkbench_create_table.gif)<br>

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
1. In `twitchApiKey.yml`, set the following fields from setting up a new application from the **Twitch Developer Console**:
   1. **twitch-api.client-id**
   2. **twitch-api.client-secret**
   3. **twitch-api.redirect-url**<br>
2. In `TwitchConfig.java`, implement:
   1. `public String getTwitchApiClientId()`
   2. `public String getTwitchApiClientSecret()`
   3. `public String getTwitchApiRedirectUrl()`

This yaml file is already added in **.gitignore**, so your keys _**will not and should not**_ be published to Github.

### Testing
- [ ] Open `TwitchConfigTest.java` ─ already implemented and tests that the loaded TwitchConfig matches the values in `twitch-key.properties`.
- [ ] Remove `@Disabled` in `TwitchConfigTest.java` for method(s): `testTwitchClientKeys()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```

<br>

#

<br>

> [!IMPORTANT]
>
> When integrating with 3P APIs there is _always_ a bit of discovery work involved. Whether it's a publicly exposed API or a private API (direct B2B integration),
> I always spend a large amount of time understanding how to integrate with the API and what data/endpoints are available.
> Best advice is to keep learning by doing and spending time struggling through blocks.

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
    &scope=chat:read+chat:edit
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

### Task 1: Twitch4J's TwitchIdentityProvider
It's possible to handle the entire auth lifecycle using your own HTTP request logic, but the Twitch4J library already handles most of this. Let's leverage that instead of re-inventing the wheel.

In `TwitchConfig.java`, implement `public TwitchIdentityProvider twitchIdentityProvider()`. This will be used throughout our entire authentication flow.

**Requirements:**
- RTFM (Read the fucking manual) for Twitch4J's [TwitchIdentityProvider.java <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/twitch4j/twitch4j/blob/d7fec926104cd26d85f9cc1f7bcedac9a01c62c7/auth/src/main/java/com/github/twitch4j/auth/providers/TwitchIdentityProvider.java#L54)
- Create a new instance of TwitchIdentityProvider by supplying your: `client-id`, `client-secret`, and `redirect-url`

> [!TIP]
> 
> A great exercise — after fully completing this module — would be to implement this entire auth lifecycle on your own.
> This is what I did initially on my first pass through this project and learned a lot by D.I.Y-ing and struggling through the initial learning curve.

### Testing
- [ ] Open `TwitchConfigTest.java` ─ already implemented and tests that the TwitchIdentityProvider bean is not null.
- [ ] Remove `@Disabled` in `TwitchConfigTest.java` for method(s): `testTwitchIdentityProvider()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```
- [ ] Run `testTwitchIdentityProvider()` through IntelliJ's debugger by:
  - Set a breakpoint at `assertNotNull(twitchConfig.twitchIdentityProvider());`
  - Click the Play button and click: `Debug testTwitchIdentity...()`
  - Manually validate that the `TwitchIdentityProvider` **clientId**, **clientSecret**, and **redirectUrl** matches the values in `twitch-key.properties`.



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
1. Inject `TwitchConfig` into the constructor.
2. Set **scope(s)** to: `chat:read` and `chat:edit` (Look at [TwitchScopes <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/twitch4j/twitch4j/blob/d7fec926104cd26d85f9cc1f7bcedac9a01c62c7/auth/src/main/java/com/github/twitch4j/auth/domain/TwitchScopes.java#L3))
3. Generate a generic UUID to set as the **state** (Unique identifier across a single auth lifecycle to verify sender)
4. Utilize a helper method from `TwitchIdentityProvider` to do the heavy lifting of creating this auth URL for us.

<br>

### Testing
- [ ] Open `OAuthRestControllerTest.java` ─ already implemented, almost identical test case as the previous part but with the added layer of calling the HTTP endpoint.
- [ ] Remove `@Disabled` in `OAuthRestControllerTest.java` for the test method(s): `getAuthUrlTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```

#

#### Part 2
![](assets/module5/images/OAuth_controller_authorize.svg)<br>
In `OAuthRestController.java`, implement `public String getAuthUrl()`. This method should just directly call the `TwitchAuthService.getLoginUrl()`.

Don't forget to inject the Bean of the `TwitchAuthService` into `OAuthRestController`.

### Integration Testing
- [ ] Run the application:
    ```shell
    ./gradlew bootRun
    ```
- [ ] Go to: [Swagger UI <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](http://localhost:8080/swagger-ui/index.html)<br>
- [ ] Play with the **Twitch Auth API**: `GET /oauth2/authorize`
- [ ] Validate that:
    - `client_id={client_id}` matches your Twitch Application's clientId
    - `redirect_uri=http://localhost:8080/oauth2/callback`
    - `scope=chat:read+chat:edit`
    - `state={UUID}` (any unique UUID string)

<br>

#

### Task 2: OAuth Callback API
![](assets/module5/images/twitch_callback.svg)<br>

In `OAuthRestController.java`, implement:
```java
@GetMapping("/oauth2/callback")
public Map<String, Object> handleCallback(
    @RequestParam(name = "code", required = false) String code,
    @RequestParam(name = "scope", required = false) String scope,
    @RequestParam(name = "state", required = false) String state,
    @RequestParam(name = "error", required = false) String error,
    @RequestParam(name = "error_description", required = false) String errorDescription)
```
Return a `Map<String, Object>` of all the parameters that were passed into this method from Twitch when it redirects to this `GET /oauth2/callback` endpoint.

**Requirements:**
- Make sure to store the initial parameters under the key `"authorization"`.
  - This task should return a `Map<String, Map<String, String>>`:
  ```json
    {
      "authorization": {__Parameters_go_here__} 
    }
  ```
  - The future tasks will explain why I've defined the return type this way. Don't worry about it for now.

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
> Map<String, String> output = handleCallBack("access_token123", scope="channel%3Aread", state="stateUUID_1", null, null);
> ```
> **Output:**<br>
> ```json
> {
>   "authorization": {
>     "code": "access_token123",
>     "scope": "channel%3Aread",
>     "state": "stateUUID_1",
>     "error": null,
>     "error_description": null
>   }
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
> Map<String, String> output = handleCallBack(code=null, scope=null, state="stateUUID_2", "access_denied", "The+user+denied+you+access");
> ```
> **Output:**<br>
> ```json
> {
>   "authorization": {
>     "code": null,
>     "scope": null,
>     "state": "stateUUID_2",
>     "error": "access_denied",
>     "error_description": "The+user+denied+you+access"
>   }
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
#### Part 1a
When Twitch sends us the **authorization code** (`code`), we can use it to create a token. Authorization codes usually have a short lifespan, so it's recommended that the server exchanges it for an **access token** quickly. 

In `TwitchAuthService.java`, implement `public OAuth2Credential createOAuthToken(String authorizationCode, List<TwitchScopes> scopes)`.

Return the OAuth2Credential object from utilizing Twitch4J's TwitchIdentityProvider to exchange our authorization `code` for a temporary access token and add a log to print to _**stdout**_ the result from Twitch.

**Requirements:**
- RTFM for Twitch4J's [TwitchIdentityProvider.java <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/twitch4j/twitch4j/blob/d7fec926104cd26d85f9cc1f7bcedac9a01c62c7/auth/src/main/java/com/github/twitch4j/auth/providers/TwitchIdentityProvider.java#L54). See if you can figure out the correct method to call.

### Example 1:
> **Input:**<br>
> ```java
import com.github.twitch4j.auth.domain.TwitchScopes; > TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> OAuth2Credential output = twitchAuthService.createOAuthToken("access_token123", List.of(TwitchScopes.CHAT_READ));
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
> OAuth2Credential output = twitchAuthService.createOAuthToken("invalid_access_token123", List.of(TwitchScopes.CHAT_READ));
> ```
> **Output:** Exception is thrown<br>
> **Explanation**: `createOAuthToken()` fails to exchange the invalid **authorization code** for a valid `access_token`.

### Testing
- [ ] Open `TwitchAuthServiceMockTest.java` ─ already implemented to test the example(s) above.
- [ ] Remove `@Disabled` in `TwitchAuthServiceMockTest.java` for the test method(s): `createOAuthTokenSuccessTest()` and `createOAuthTokenFailTest()`
- [ ] Test with (These tests should fail):
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```

#

#### Part 1b
I've intentionally set the unit tests up in `TwitchAuthServiceMockTest.java` to fail when asserting the OAuth2Credential scopes field.

Why've I done this?<br>
Mainly because — at the time of writing this — I found a bug in the latest open source library code that TwitchIdentityProvider uses when fetching back the `OAuth2Credential` object.

[OAuth2IdentityProvider.java <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://github.com/PhilippHeuer/credential-manager/blob/main/src/main/java/com/github/philippheuer/credentialmanager/identityprovider/OAuth2IdentityProvider.java#L315)
```java
return new OAuth2Credential(
        this.providerName, // identityProvider
        (String) resultMap.get("access_token"), // accessToken
        (String) resultMap.get("refresh_token"), // refreshToken
        null, // userId
        null, // userName
        TokenResponseUtil.parseExpiresIn(resultMap.get("expires_in")), // expiresIn
        null); // scopes - THE ISSUE IS HERE
```
When we issue authorize a token with scope=["chat:read"], we'd like for the `OAuth2Credential` object that Twitch4J's library returns
in `TwitchIdentityProvider.getCredentialByCode(...)` to ideally contain all the same information when we get a valid token.

> [!WARNING]
> 
> It's arguable that even the `userId`, `userName` fields should also be set if they exist, but these fields don't really matter for us. But we do care about the scopes metadata.
> 
> When I was first integrating with Twitch4J, I initially thought I did something wrong because
> I was able to successfully fetch a valid token, but saw that the `scopes` were empty. It wasn't until I dove deeper into
> the source code that I was able to root cause **_why_** I was noticing this behavior.
> 
> Main Takeaway: read the fucking manual.

In `TwitchAuthService.java`, fix `public OAuth2Credential createOAuthToken(String authorizationCode, List<TwitchScopes> scopes)`. Notice the `scopes` argument that we never used in `Part 1a`.

After receiving a valid `OAuth2Credential` from Twitch4J, let's create a new `OAuth2Credential` to fill in the missing scopes field.<br>
Return the new `OAuth2Credential` that you manually create.

**Requirements:**
1. Create a new `OAuth2Credential` object
2. Copy over all the fields from the original `OAuth2Credential` object that Twitch4J returns
3. For `scopes`, just use the input `List<TwitchScopes> scopes` parameter

### Testing
- [ ] Open `TwitchAuthServiceMockTest.java` ─ already implemented.
- [ ] Run the same methods that were previously failing: `createOAuthTokenSuccessTest()` and `createOAuthTokenFailTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5
    ```

#

#### Part 2
As I've said earlier, authorization codes usually have a short lifespan, so it's recommended that the server exchanges it for an **access token** quickly.
When our application gets called by Twitch's servers at our `http://localhost:8080/oauth2/callback` endpoint, we will immediately kick off the authorization_code → access_token exchange process.

In `OAuthRestController.java`, update `handleCallback()` to call `TwitchAuthService.createOAuthToken()` using the same `code` that Twitch passes back to our server.

**Requirements:**
1. Add a new entry in the return `Map<String, Object>` for `"token"`
2. Set the `OAuth2Credential` at that new `"token"` key

### Example 1:
> **Input:**<br>
> ```
> Twitch servers send our application a request:
> http://localhost:8080/oauth2/callback
>     ?code=access_token123
>     &scope=channel%3Aread
>     &state=stateUUID_1
>
> Map<String, String> output = handleCallBack("access_token123", scope="channel%3Aread", state="stateUUID_1", null, null);
> ```
> **Output:**<br>
> ```json
> {
>   "authorization": {
>     "code": "access_token123",
>     "scope": "channel%3Aread",
>     "state": "stateUUID_1",
>     "error": null,
>     "error_description": null
>   },
>   "token": {
>     "identityProvider": "twitch",
>     "accessToken": "access_token123",
>     "refreshToken": "refresh_token123",
>     "userId": null,
>     "userName": null,
>     "expiresIn": 14124,
>     "scopes": ["chat:read"]
>   }
> }
> ```
> **Explanation**:
> 1. "authorization" is just the bundled up input parameters that was sent from Twitch servers when the user's `/authorization` endpoint is redirected to our service.
> 2. "token" is the `OAuth2Credential` object we put into the `Map<String, Object>` to visually see it in the redirected URL. This `OAuth2Credential` is the object that is:<br>
> First, exchanged by `TwitchIdentityProvider` and then<br>
> Second, re-created by you just now with the additional `scopes` field properly populated.

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

<br>

#

### Task 3: OAuth Refresh Token API
When your Auth Token expires, we will need to refresh our auth token. Tokens have a TTL (Time To Live) specified when a token is minted.<br>
To access Twitch API we usually pass in our temporary `access_token`, but when that temporary token expires we need to ask Twitch to give us a new "key". 
What a Key is to a treasure chest, is what a token is to the Twitch API. Except we need a new token everytime it expires.
This is done by issuing a request to `https://id.twitch.tv/oauth2/token` with the `refresh_token`.

In `TwitchAuthService.java`, implement `public String refreshOAuthToken(String refreshToken)`.

Return the String representation of a Map<String, String> payload from refreshing the token and add a log to print to _**stdout**_ the result from Twitch.

### Example 1:
> **Input**:<br>
> ```java
> TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> String output = twitchAuthService.refreshOAuthToken("refresh_token123");
> ```
> **Output**:<br>
> ```json
> {
>   "access_token": "newAccessToken123",
>   "expires_in": 14124,
>   "refresh_token": "5b93chm6hdve3mycz05zfzatkfdenfspp1h1ar2xxdalen01",
>   "scope": [
>     "chat:read"
>   ],
>   "token_type": "bearer"
> }
> ```
> **Explanation**: When we refresh a token using the `refresh_token` you will notice the `refresh_token`, `scope`, and `token_type` don't change on refresh. The only fields that do change are the new "key", a.k.a the `access_token`, and the `expires_in` field which
> is the updated TTL for this new key.

### Example 2:
> **Input**:<br>
> ```java
> TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> String output = twitchAuthService.refreshOAuthToken("invalid_refresh_token123");
> ```
> **Output**:Exception is thrown.<br>

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

In `TwitchAuthService.java`, implement `public boolean validateOAuthToken(String accessToken)`.
Send a GET HTTP request to the twitch endpoint using our temporary `accessToken`.

Return a boolean on whether Twitch's `/oauth2/validate` endpoint returns a 200 response, signifying that our temporary token is still alive.<br>
Also log Twitch's entire HTTP response.

### Example 1:
> **Input:**<br>
> ```java
> TwitchAuthService twitchAuthService = new TwitchAuthService(...);
> boolean output1 = twitchAuthService.validateOAuthToken("access_token123");
> 
> twitchAuthService.createOAuthToken("access_token123");
> boolean output2 = twitchAuthService.validateOAuthToken("access_token123");
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
We need a persistent way of caching a User's latest token, so that we can create the token once and then refresh as needed upon expiration.<br>
If we never store the User Token, we would need to follow the `/authorize` login flow everytime our application starts up.

In `OAuthRedisService.java`, implement:
1. `updateLatestToken(String username, String tokenResponse)` to set the token passed back by Twitch as a `OAuth2Credential` object represented as a String stored at a key
2. `getLatestToken(String username)` to fetch the latest OAuth2Credential token stored at a key.

**Requirements:**
1. Create a new Spring property for `twitch-chat-hit-counter.redis.oauth-token-database: 3`
2. Create the beans needed to communicate with `db3` in `RedisConfigs.java`
3. Key template: `"twitch#oauth#{userId}"`
4. Value should be a JSON String of a `OAuth2Credential.java` object w/ the token metadata response passed back from Twitch's `/oauth2/token` endpoint

> [!NOTE]
>
> Since this app is simply used by our application and your Twitch account will be used to grant tokens, just replace the {userId} in the key template with your own Twitch account username.<br>
> It is possible to retrieve your account metadata through the Twitch API, but for now let's keep it simple, as we're dealing with multiple Twitch API endpoints already.

### Example 1:
> ```java
> OAuthRedisService oAuthRedisService = new OAuthRedisService(...);
> OAuth2Credential output1 = oAuthRedisService.getLatestToken("Alice");
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
> OAuth2Credential output2 = oAuthRedisService.getLatestToken("Alice");
> ```
> **Output1**:null<br>
> **Explanation**: Alice doesn't have a token yet cached in Redis
>
> **Output2**:TODO<br>
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
Now that we have the ability to store Tokens in Redis, we need to call this method.

In `TwitchAuthService.java`, update the methods below to update our User's latest User Token in Redis:
1. `public String createOAuthToken(String accessToken)`
2. `public String refreshOAuthToken(String refreshToken)`

**Requirements**:<br>
1. If and only if Twitch's `/oauth2/token` endpoints return a successful response of a newly minted token or a refreshed token should we update the User Token in Redis

### Example 1:
TODO example of successful overwrite

#

### Example 2:
TODO Example of non-successful call to /token so we skip overwrite

#

### Testing
- [ ] TODO

### Integration Testing
- [ ] TODO















<br>

#

### Exercise 3: Twitch Chat Connection
![](assets/module5/images/ChatOverview.svg)<br>
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
In `TwitchChatBotManager.java`, implement the `public void init()` post-constructor. We will need to build Twitch4J's `TwitchClient` object that acts as the proxy (client middleman) to all of Twitch API's endpoints.

**Requirements:**
1. Initialize the `TwitchClient` object with our Twitch API ClientId, Client Secret, and OAuth Token.
2. Helix should be enabled
3. Chat should be enabled
4. Chat Account should be set using the OAuthCredentials
   1. We should check Redis `db3` to see if our User Account has a token
   2. If yes, validate this token to see that it hasn't expired.
      1. If expired, refresh token and cache the refreshed token into Redis `db3`.
   3. If no, create a token and cache it into Redis `db3`

```java
TwitchClient twitchClient = TwitchClientBuilder.builder()
            .withEnableHelix(true)
            .build();
```

<br>

#

#### Part 2
In `TwitchChatBotManager.java`, implement `public void joinChannel(String channelName)`. This method will allow our User account to join a Twitch stream by the `channelName`.

> [!IMPORTANT]
> 
> Before using TwitchClient to join a channel we need to validate OAuth Token and refresh it if invalid.

#

#### Part 3
In `TwitchChatBotManager.java`, implement `public boolean leaveChannel(String channelName)`. This method will allow our User account to leave a Twitch stream by the `channelName`.

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
In `TwitchChatBotManager.java`, implement `public void handleMessage(ChannelMessageEvent channelMessageEvent)`.<br>

This method will be the main handler for the incoming real-time twitch chat messages. Twitch4J will pass in the `ChannelMessageEvent`.
We will need to create an instance of the `TwitchChatEvent.java` from the passed in event.
All the required fields we defined in the schema for `TwitchChatEvent.java` are already contained in the schema of Twitch4J's `ChannelMessageEvent`.

Your goal is to simply, for now, log the simplified `TwitchChatEvent` to **stdout**.

**Requirements:**
1. In the `@PostConstructor init()` method, make sure to attach the `handleMessage()` method as the [Event Handler <img src="assets/common/export.svg" width="16" height="16" style="vertical-align: top;" alt="export" />](https://twitch4j.github.io/events/channel-message-event#write-chat-to-console) for all incoming chat messages
2. Convert the incoming `ChannelMessageEvent` → `TwitchChatEvent`
3. Log `TwitchChatEvent` to _**stdout**_

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatBotManager service = new TwitchChatBotManager(...);
> service.handleMessage(channelMessageEvent);
> ```
> **stdout**:<br>
> 

### Testing

### Integration Testing












<br>

### Exercise 4: Kafka
![](assets/module5/images/KafkaOverview.svg)<br>

> **Relevant Files:**
> 
> `application.yml`
> `KafkaConfigs.java`
> 

Now that we can connect to Twitch chats successfully, we need to build a Kafka producer/consumer to publish these `TwitchChatEvent` to a new separate kafka topic.

This will look very similar to the end state we had in **Module 2** with the Producer/Consumer on the `greeting-events` kafka topic.<br>
This exercise will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:** 
1. Stream the incoming chat messages from channel(s) using Twitch4J's `TwitchClient`
2. Publish `TwitchChatEvent` to `twitch-chat-events` topic
3. Consume `TwitchChatEvent` from `twitch-chat-events` topic and log them to **stdout**

The main differences from **Module 2** is the producer trigger logic. In **Module 2**, we needed to manually trigger the `POST /api/kafka/publishGreetingEvent` endpoint via Swagger to invoke our Producer to publish event(s).
In this exercise, our `TwitchChatBotManager.handleMessage()` event handler method is the trigger for the `TwitchChatEventProducer.java`. Once we join a channel and attach the event listener,
the TwitchClient will stream, in real-time, the incoming chat messages that we need to publish to `twitch-chat-events` topics. No more manual trigger, fully automated.

### Task 1: add new kafka topic name to `application.yml`
```yaml
twitch-chat-hit-counter:
  kafka:
    consumer:
      twitch-chat-topic:
        twitch-chat-events
    producer:
      twitch-chat-topic:
        twitch-chat-events
```

### Testing
- [ ] Open `PropertiesApplicationTest.java` ─ already implemented to test the properties above.
- [ ] Remove `@Disabled` in `PropertiesApplicationTest.java` for the test method(s): `kafkaTwitchChatTopicNameTest()`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module4
    ```

### Task 2: Producer
In `TwitchChatEventProducer.java`, fix the constructor and inject the topic name from `application.yml`.
You'll notice that our initial implementation of `AbstractEventProducer.publish()` from **Module 2** takes care of writing events to a generic topic.
This is where our abstract class pays dividends. We don't need to repeat code and can leverage implementing the main logic in one parent class, and all it's children will benefit.

### Example 1:
> **Input**:<br>
> ```java
> TwitchChatEventProducer producer = new TwitchChatEventProducer(...);
> String eventId = "UUID1";
> TwitchChatEvent event = new TwitchChatEvent(eventId, "Alice", "Bob", "Hi Bob, I'm Alice!");
> boolean output1 = producer.publish(eventId, event);
> 
> String eventId2 = "UUID2";
> TwitchChatEvent event2 = new TwitchChatEvent(eventId2, "Charlie", "David", "Yo.");
> boolean output2 = producer.publish(eventId2, event2);
> ```
> **Output1**: <span style="color:#0000008c">true<br></span>
> **Output2**: <span style="color:#0000008c">true<br></span>

#

### Testing
- [ ] Open `TwitchChatEventProducer.java` ─ already implemented test cases with the example(s) above.
- [ ] Remove `@Disabled` in `TwitchChatEventProducer.java`
- [ ] Test with:
    ```shell
    ./gradlew test --tests "*" -Djunit.jupiter.tags=Module5`
    ```

<br>

#

### Task 3: Consumer

<br>














<br>

### Exercise 5: SQL
![](assets/module5/images/SqlOverview.svg)<br>
Now that we are able to stream Twitch chat events and pub/sub events through our new kafka topic, we need to write the `TwitchChatEvent` to a new separate SQL table.

This will look very similar to the end state we had in **Module 3**. This exercise description will be kept short and it's up to you to make your application achieve the end state in the diagram above.

**Goals:**
1. In **MySQLWorkbench**, create a new SQL table named `twitch_chat_events`
2. Have `TwitchChatEventConsumer.java` write the `TwitchChatEvent` to the new SQL table
3. Implement `TwitchChatSqlService.java` - should look very similar in terms of layout to `GreetingSqlService.java`

### Example 1:
> ```java
> TwitchChatSqlService service = new TwitchChatSqlService(...);
> 
> TwitchChat event1 = new TwitchChat("id1", "Alice", "Bob", "Hi Bob, I'm Alice!");
> TwitchChat event2 = new TwitchChat("id2", "Charlie", "David", "Yo.");
> TwitchChat event3 = new TwitchChat("id1", "Echo", "Frank", "Hello there.");
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

### Testing

### Integration Testing















<br>

### Exercise 6: Redis Hit Counter
![](assets/module5/images/RedisOverview.svg)<br>
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





### Task 1: Hook up `TwitchChatEventConsumer` to the `EventDeduperRedisService`
![](assets/module5/images/RedisDeduperOverview.svg)<br>
In `TwitchChatEventConsumer.java`, update `TODO()` to now increment the hit count for the channel.

**Consumer Process Flow:**
1. Check Redis to see if the kafka message key is a duplicate
2. If **isDupeEvent == True**:
    1. Do nothing (skip processing the event)
3. If **isDupeEvent == False**:
    1. Write the event to SQL
    2. Update the Redis DB to add the event's key, so that we can skip this event from being processed if we ever see an event with the same key again.

### Testing
TODO?

<br>

### Task 2: TwitchChatRedisService
![](assets/module5/images/RedisAggOverview.svg)<br>
In `TwitchChatRedisService.java`, implement `public Long incrementMinuteHitCounter(String channelName, long eventTimestampMs)`.

Return the updated count after we increment the key.

If you notice the key template: `"{channelName}#{minuteBoundaryInMillis}"`, you will need to figure out how to mathematically
round the raw event ts to the nearest minute timestamp. Look at the example below for an explanation.

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
> **Explanation**: After we increment the hit count for s0mcs's channel with the timestamp at 1767254439000, the minutely "bucket" it gets rounded down to is 1767254400000. Then it increments that key value.<br>
> ```json
> {
>   "s0mcs#1767254400000": 1
> }
> ```
> 
> **Output2**: 2<br>
> **Explanation**: After we increment the hit count for s0mcs's channel with the timestamp at 1767254445000, the minutely "bucket" it gets rounded down to is 1767254400000. Then it increments that key value.<br>
> ```json
> {
>   "s0mcs#1767254400000": 2
> }
> ```

### Testing

#

In `TwitchChatRedisService.java`, implement `public Map<String, String> getHitCounts(String channelName)`.

Return a Map<String, String> of **ALL** minutely bucket hit counts for a specified channelName by utilizing the `RedisDao.scanKeys()`.

### Example 1:
> **Input**:<br>
> ```java
> RedisDao redisDao = new RedisDao(...);
> TwitchChatRedisService service = new TwitchChatRedisService(redisDao);
> 
> long eventTs1 = 1767254439000; // Thu Jan 01 2026 08:00:39 GMT+0000
> long eventTs2 = 1767254445000; // Thu Jan 01 2026 08:00:45 GMT+0000
> long eventTs3 = 1767254545000; // Thu Jan 01 2026 08:02:25 GMT+0000
>
> service.incrementMinuteHitCounter("s0mcs", eventTs1);
> service.incrementMinuteHitCounter("s0mcs", eventTs2);
> service.incrementMinuteHitCounter("s0mcs", eventTs3);
> Map<String, String> output3 = service.getHitCount("s0mcs");
> ```
> **Output1**: 1<br>
> ```json
> {
>   "s0mcs#1767254400000": 2,
>   "s0mcs#1767254520000": 1
> }
> ```
> **Explanation**:<br>
> eventTs1 = `1767254439000` (2026-01-01 08:**00:39** UTC) → `1767254400000` (2026-01-01 08:**00:00** UTC)<br>
> eventTs2 = `1767254445000` (2026-01-01 08:**00:45** UTC) → `1767254400000` (2026-01-01 08:**00:00** UTC)<br>
> eventTs3 = `1767254545000` (2026-01-01 08:**02:25** UTC) → `1767254520000` (2026-01-01 08:**02:00** UTC)<br>

### Testing

<br>

#

### Task 3: Hook up `TwitchChatEventConsumer` to the `TwitchChatRedisService`
In `TwitchChatEventConsumer.java`, update `TODO()` to now increment the hit count for the channel.

**Consumer Process Flow:**
1. Check Redis to see if the kafka message key is a duplicate
2. If **isDupeEvent == True**:
    1. Do nothing (skip processing the event)
3. If **isDupeEvent == False**:
    1. Write the event to SQL
    2. **(NEW)** Increment the channel's hit count by 1
    3. Update the Redis DB to add the event's key, so that we can skip this event from being processed if we ever see an event with the same key again.

### Testing
TODO?

<br>

#
