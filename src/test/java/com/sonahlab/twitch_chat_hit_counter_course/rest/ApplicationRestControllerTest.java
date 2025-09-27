package com.sonahlab.twitch_chat_hit_counter_course.rest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApplicationRestControllerTest {

    @Autowired
    private ApplicationRestController controller;

    @Test
    @Tag("Module1")
    public void testSayHello() {
        String name = "Alice";
        String expected = "Hello, Alice!";
        String result = controller.sayHello(name);
        assertEquals(expected, result);
    }

    @Test
    @Tag("Module1")
    public void testSayHelloEmptyName() {
        String name = "";
        String expected = "Hello, Mysterious Individual!";
        String result = controller.sayHello(name);
        assertEquals(expected, result);
    }
}
