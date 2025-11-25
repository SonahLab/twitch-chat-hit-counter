package com.sonahlab.twitch_chat_hit_counter_course.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Service responsible for interacting with the Twitch API Client.
 */
@RestController
@Tag(name = "Twitch Auth API", description = "Backend API endpoints that act as proxy to various Twitch Auth API endpoints")
public class OAuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuthRestController.class);

    // Constructor
    public OAuthRestController() {
        /**
         * TODO: Implement as part of Module 5
         * */
    }

    /**
     * HTTP GET request handler for endpoint /oauth2/authorize. The returned URL of this endpoint needs to be input in
     * a browser to kick off the OAuth Token generation process.
     *
     * @return String user token authorize URL
     * */
    @GetMapping("/oauth2/authorize")
    public String authorize() {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    /**
     * HTTP GET request handler for endpoint /oauth2/callback. This endpoint will be called directly by Twitch's servers
     * once you input the result of /oauth2/authorize into a browser. Twitch will redirect the authorization to this endpoint.
     *
     * If Twitch is able to call this endpoint with a valid code parameter, you should create the OAuth Token by
     * sending a POST request to Twitch at https://id.twitch.tv/oauth2/token.
     *
     * @param code String access_token that will be used to issue the new OAuth Token
     * @param scope String of list of scopes that you are trying to attach to the OAuth Token
     * @param state String UUID that should be previously generated in /oauth2/authorize
     * @param error String error while trying to authorize User
     * @param errorDescription String description of the error while trying to authorize User
     * @return Map of the parameters that were passed in from Twitch's servers.
     */
    @GetMapping("/oauth2/callback")
    public Map<String, Object> handleCallback(
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "scope", required = false) String scope,
            @RequestParam(name = "state", required = false) String state,
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "error_description", required = false) String errorDescription) throws JsonProcessingException {
        /**
         * TODO: Implement as part of Module 5
         * */
        Map<String, Object> response = new HashMap<>();
        Map<String, String> authParams = new HashMap<>() {{
            put("code", code);
            put("scope", scope);
            put("state", state);
            put("error", error);
            put("error_description", errorDescription);
        }};
        response.put("authorization", authParams);
        return response;
    }

    @GetMapping("/oauth2/refreshToken")
    public OAuth2Credential refresh(@RequestParam(name = "code", required = false) String refreshToken) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return null;
    }

    @GetMapping("/oauth2/validateToken")
    public boolean validate(@RequestParam(name = "code", required = false) String accessToken) {
        /**
         * TODO: Implement as part of Module 5
         * */
        return false;
    }
}
