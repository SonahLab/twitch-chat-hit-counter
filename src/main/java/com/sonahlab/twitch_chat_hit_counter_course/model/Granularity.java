package com.sonahlab.twitch_chat_hit_counter_course.model;

/**
 * Defines the Granularity time series type.
 *
 * This enum is mainly used when doing different aggregations in our Redis DB.
 *
 * Usually, many aggregation pipelines aggregate raw events up to the MINUTE, HOUR, DAY grain.
 * This course focuses on MINUTE level aggregation.
 * */
public enum Granularity {
    MINUTE,
    HOUR, // Unused
    DAY, // Unused
    WEEK, // Unused
    MONTH, // Unused
    YEAR // Unused
}
