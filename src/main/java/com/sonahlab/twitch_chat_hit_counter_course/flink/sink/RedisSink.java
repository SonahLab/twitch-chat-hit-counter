package com.sonahlab.twitch_chat_hit_counter_course.flink.sink;

import org.apache.flink.api.connector.sink2.Sink;
import org.apache.flink.api.connector.sink2.SinkWriter;
import org.apache.flink.api.connector.sink2.WriterInitContext;
import org.apache.flink.api.java.tuple.Tuple3;

import java.io.IOException;

public class RedisSink implements Sink<Tuple3<String, Long, Long>> {
    @Override
    public SinkWriter<Tuple3<String, Long, Long>> createWriter(WriterInitContext context) throws IOException {
        return new RedisSinkWriter();
    }
}
