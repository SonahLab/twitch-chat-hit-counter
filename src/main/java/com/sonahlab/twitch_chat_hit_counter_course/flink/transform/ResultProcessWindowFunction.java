package com.sonahlab.twitch_chat_hit_counter_course.flink.transform;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultProcessWindowFunction extends ProcessWindowFunction<Long, Tuple3<String, Long, Long>, String, TimeWindow> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResultProcessWindowFunction.class);

    @Override
    public void process(String key, ProcessWindowFunction<Long, Tuple3<String, Long, Long>, String, TimeWindow>.Context context, Iterable<Long> elements, Collector<Tuple3<String, Long, Long>> out) throws Exception {
        Long count = elements.iterator().next();
        out.collect(new Tuple3<>(key, context.window().getStart(), count));
    }
}
