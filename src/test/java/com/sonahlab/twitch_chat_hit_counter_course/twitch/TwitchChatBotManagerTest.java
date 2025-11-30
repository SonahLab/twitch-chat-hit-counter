package com.sonahlab.twitch_chat_hit_counter_course.twitch;

import com.github.philippheuer.events4j.core.EventManager;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.chat.TwitchChat;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.sonahlab.twitch_chat_hit_counter_course.redis.ChatBotChannelsRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;
import java.util.function.Consumer;

import static com.sonahlab.twitch_chat_hit_counter_course.utils.TwitchApiUtils.USERNAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TwitchChatBotManagerTest {

    private final String TEST_CHANNEL = "test_channel";

    @Mock
    private TwitchClient mockTwitchClient;

    @Mock
    private TwitchChat mockTwitchChat;

    @Mock
    private ChatBotChannelsRedisService mockRedisService;

    @Mock
    private EventManager mockEventManager;

    private TwitchChatBotManager twitchChatBotManager;

    @BeforeEach
    public void setup() {
        // TODO: uncomment out the line below once you're ready to test with Module 5
        //twitchChatBotManager = new TwitchChatBotManager(mockTwitchClient);
        // TODO: uncomment out the line below once you're ready to test with Module 6
        //twitchChatBotManager = new TwitchChatBotManager(mockTwitchClient, mockRedisService);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void joinChannelTest() {
        when(mockTwitchClient.getChat()).thenReturn(mockTwitchChat);
        twitchChatBotManager.joinChannel(TEST_CHANNEL);

        verify(mockTwitchChat, times(1)).joinChannel(TEST_CHANNEL);
        verifyNoMoreInteractions(mockTwitchChat);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void leaveChannelTest() {
        when(mockTwitchClient.getChat()).thenReturn(mockTwitchChat);
        when(mockTwitchChat.leaveChannel(TEST_CHANNEL)).thenReturn(true);
        boolean result = twitchChatBotManager.leaveChannel(TEST_CHANNEL);

        verify(mockTwitchChat, times(1)).leaveChannel(TEST_CHANNEL);
        verifyNoMoreInteractions(mockTwitchChat);
        assertTrue(result);
    }

    @Test
    @Tag("Module5")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 5.
    @Disabled
    public void handleChatMessageTest() {
        ChannelMessageEvent mockEvent = mock(ChannelMessageEvent.class);
        when(mockEvent.getMessage()).thenReturn("test message");

        try {
            java.lang.reflect.Method handleMessageMethod =
                    TwitchChatBotManager.class.getDeclaredMethod("handleChatMessage", ChannelMessageEvent.class);
            handleMessageMethod.setAccessible(true);
            handleMessageMethod.invoke(twitchChatBotManager, mockEvent);
        } catch (Exception e) {
            fail("handleChatMessage should not throw an exception: " + e.getMessage());
        }
    }

    @Test
    @Tag("Module6")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    public void initTest() {
        Set<String> initialChannels = Set.of(TEST_CHANNEL);
        when(mockRedisService.getJoinedChannels(USERNAME)).thenReturn(initialChannels);
        when(mockTwitchClient.getChat()).thenReturn(mockTwitchChat);
        when(mockTwitchClient.getEventManager()).thenReturn(mockEventManager);

        ArgumentCaptor<Consumer<ChannelMessageEvent>> consumerCaptor = ArgumentCaptor.forClass(Consumer.class);

        twitchChatBotManager.init();

        verify(mockEventManager, times(1)).onEvent(eq(ChannelMessageEvent.class), consumerCaptor.capture());
        verify(mockTwitchChat, times(1)).joinChannel(TEST_CHANNEL);
        verify(mockRedisService, times(1)).getJoinedChannels(USERNAME);
        verifyNoMoreInteractions(mockTwitchChat);
    }

    @Test
    @Tag("Module6")
    // TODO: remove the @Disabled annotation once you're ready to test the implementation of Module 6.
    @Disabled
    public void getJoinedChannelsTest() {
        Set<String> expectedChannels = Set.of(TEST_CHANNEL);
        when(mockRedisService.getJoinedChannels(USERNAME)).thenReturn(expectedChannels);

        Set<String> actualChannels = twitchChatBotManager.getJoinedChannels(USERNAME);

        verify(mockRedisService, times(1)).getJoinedChannels(USERNAME);
        assertEquals(expectedChannels, actualChannels);
    }
}
