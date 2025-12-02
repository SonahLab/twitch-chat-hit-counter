package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * Defines the Granularity time series type.
 *
 * This enum is mainly used when doing different aggregations in our Redis DB.
 * */
public enum Granularity {
    MINUTE,
    HOUR,
    DAY
}
