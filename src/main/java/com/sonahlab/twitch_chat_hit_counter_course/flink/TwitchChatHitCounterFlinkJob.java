package com.sonahlab.twitch_chat_hit_counter_course.flink;

import com.sonahlab.twitch_chat_hit_counter_course.flink.deserializer.TwitchChatEventDeserializationSchema;
import com.sonahlab.twitch_chat_hit_counter_course.flink.sink.RedisSink;
import com.sonahlab.twitch_chat_hit_counter_course.flink.transform.CountAggregateFunction;
import com.sonahlab.twitch_chat_hit_counter_course.flink.transform.ResultProcessWindowFunction;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.apache.flink.api.common.eventtime.*;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class TwitchChatHitCounterFlinkJob implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatHitCounterFlinkJob.class);

    private final String topic;
    private final String bootstrapServers;
    private final String groupId;

    public TwitchChatHitCounterFlinkJob(
            @Value("${twitch-chat-hit-counter.kafka.consumer.twitch-chat-topic}") String topic,
            @Value("${spring.kafka.consumer.bootstrap-servers}") String bootstrapServers,
            @Value("${spring.kafka.consumer.group-id}") String groupId) {
        this.topic = topic;
        this.bootstrapServers = bootstrapServers;
        this.groupId = groupId;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
            env.setParallelism(1);

            KafkaSource<TwitchChatEvent> source = KafkaSource.<TwitchChatEvent>builder()
                    .setBootstrapServers(bootstrapServers)
                    .setTopics(topic)
                    .setGroupId(groupId)
                    .setStartingOffsets(OffsetsInitializer.earliest())
                    .setDeserializer(new TwitchChatEventDeserializationSchema())
                    .build();
            WatermarkStrategy<TwitchChatEvent> watermarkStrategy = WatermarkStrategy
                    .<TwitchChatEvent>forBoundedOutOfOrderness(Duration.ofMinutes(1))
                    .withTimestampAssigner((event, timestamp) -> event.messageContext().eventTs());
            DataStream<TwitchChatEvent> stream = env.fromSource(source, watermarkStrategy, "Kafka Source").assignTimestampsAndWatermarks(watermarkStrategy);
            SingleOutputStreamOperator result = stream
                    .keyBy(event -> event.messageContext().channelName())
                    .window(TumblingEventTimeWindows.of(Duration.ofMinutes(1)))
                    .aggregate(new CountAggregateFunction(), new ResultProcessWindowFunction());
            DataStreamSink sink = result.sinkTo(new RedisSink());
            env.execute("Twitch Chat Hit Counter Streaming Job");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
