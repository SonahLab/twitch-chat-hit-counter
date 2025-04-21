package com.sonahlab.twitch_chat_hit_counter_course.module;

import com.sonahlab.twitch_chat_hit_counter_course.model.Pokemon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Module1 {
    private static final Logger LOGGER = LoggerFactory.getLogger(Module1.class);

    private static Map<String, Pokemon> ASH_POKEMON = new HashMap<>() {{
        put("Pikachu", new Pokemon("Pikachu", "Lightning", 5, 100));
    }};

    /**
     * Module 1 Playground: Implement as part of Module 1, Exercise 2.
     * */
}