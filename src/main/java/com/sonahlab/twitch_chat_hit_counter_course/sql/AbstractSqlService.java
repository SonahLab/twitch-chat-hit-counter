package com.sonahlab.twitch_chat_hit_counter_course.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.util.List;

public abstract class AbstractSqlService<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSqlService.class);

    // Constructor
    public AbstractSqlService() {
        /**
         * TODO: Implement as part of Module 3
         * */
    }

    public abstract String sqlTableName();

    public abstract List<String> columns();

    protected abstract void bind(PreparedStatement ps, T event);

    public int insert(List<T> events) {
        /**
         * TODO: Implement as part of Module 3
         * */
        return -1;
    }

    public List<T> queryAllEvents() {
        /**
         * TODO: Implement as part of Module 3
         * */
        return null;
    }
}
