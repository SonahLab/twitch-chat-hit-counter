package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.auth.domain.TwitchScopes;
import com.github.twitch4j.auth.providers.TwitchIdentityProvider;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TwitchAuthServiceMockTest {

    private final String MOCK_AUTH_CODE = "mockAuthorizationCode1";
    private final OAuth2Credential MOCK_CREDENTIAL = new OAuth2Credential(
            "twitch",
            "dummyAccessToken",
            "dummyRefreshToken",
            null,
            null,
            14124,
            null); // Setting scopes to null because Twitch4J has a bug where we need to manually override the OAuth2Credential object's scope field

    @Mock
    private TwitchIdentityProvider twitchIdentityProvider;

    @InjectMocks
    private TwitchAuthService service;

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void createOAuthTokenSuccessTest() {
        when(twitchIdentityProvider.getCredentialByCode(anyString())).thenReturn(MOCK_CREDENTIAL);

        try {
            OAuth2Credential result = service.createOAuthToken(MOCK_AUTH_CODE, List.of(TwitchScopes.CHAT_READ));

            assertNotNull(result);
            assertEquals("twitch", result.getIdentityProvider());
            assertEquals("dummyAccessToken", result.getAccessToken());
            assertEquals("dummyRefreshToken", result.getRefreshToken());
            assertNull(result.getUserId());
            assertNull(result.getUserName());
            assertEquals(14124, result.getExpiresIn().intValue());
            assertTrue(result.getScopes().contains(TwitchScopes.CHAT_READ.toString()));
        } catch (Exception e) {
            throw new RuntimeException("Should not reach this case in the happy path");
        }
    }

    @Test
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    @Tag("Module5")
    public void createOAuthTokenFailTest() {
        when(twitchIdentityProvider.getCredentialByCode(anyString()))
                .thenThrow(new RuntimeException("Dummy exception for invalid cases"));

        assertThrows(RuntimeException.class, () -> service.createOAuthToken(MOCK_AUTH_CODE, List.of(TwitchScopes.CHAT_READ)));
    }
}
