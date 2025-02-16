package com.sonahlab.twitch_chat_hit_counter_course.flink.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sonahlab.twitch_chat_hit_counter_course.model.TwitchChatEvent;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TwitchChatEventDeserializationSchema implements KafkaRecordDeserializationSchema<TwitchChatEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitchChatEventDeserializationSchema.class);

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<TwitchChatEvent> out) throws IOException {
        out.collect(objectMapper.readValue(record.value(), TwitchChatEvent.class));
    }

    @Override
    public TypeInformation<TwitchChatEvent> getProducedType() {
        return TypeInformation.of(TwitchChatEvent.class);
    }
}
