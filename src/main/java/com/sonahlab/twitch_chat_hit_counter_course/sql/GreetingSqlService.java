package com.sonahlab.twitch_chat_hit_counter_course.sql;

import com.sonahlab.twitch_chat_hit_counter_course.model.GreetingEvent;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for interacting with SQL databases to handle GreetingEvent data.
 *
 * Recommended Learning materials to learn Spring Boot + SQL integration:
 * - https://docs.spring.io/spring-boot/reference/data/sql.html
 */
@Service
public class GreetingSqlService {
    /**
     * TODO: Implement as part of Module 3
     * */
    public int insert(GreetingEvent event) {
        return 0;
    }

    public int insertBatch(List<GreetingEvent> events) {
        return 0;
    }

    public List<GreetingEvent> queryAllEvents() {
        return null;
    }
}
