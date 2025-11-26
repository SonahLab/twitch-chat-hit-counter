package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.helix.TwitchHelix;
import com.github.twitch4j.helix.domain.UserList;
import com.netflix.hystrix.HystrixCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Tag("Module6")
// TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
@Disabled
public class TwitchHelixServiceTest {

    private final String TEST_CHANNEL = "test_channel";

    @Mock
    private TwitchClient mockTwitchClient;

    @Mock
    private TwitchHelix mockTwitchHelix;

    @Mock
    private HystrixCommand<UserList> mockHystrixCommand;

    @Mock
    private UserList mockUserList;

    private TwitchHelixService twitchHelixService;

    @BeforeEach
    public void setup() {
        // TODO: uncomment out the line below once you're ready to test any of the Unit Tests below.
        //twitchHelixService = new TwitchHelixService(mockTwitchClient);
    }

    @Test
    public void getChannelsMetadataTest() {
        when(mockTwitchClient.getHelix()).thenReturn(mockTwitchHelix);
        when(mockTwitchHelix.getUsers(eq(null), eq(null), anyList())).thenReturn(mockHystrixCommand);
        when(mockHystrixCommand.execute()).thenReturn(mockUserList);
        twitchHelixService.getChannelsMetadata(List.of(TEST_CHANNEL));

        verify(mockTwitchHelix, times(1)).getUsers(null, null, List.of(TEST_CHANNEL));
        verifyNoMoreInteractions(mockTwitchHelix);
    }
}
