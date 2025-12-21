package com.sonahlab.twitch_chat_hit_counter_course.metrics;

import com.sonahlab.twitch_chat_hit_counter_course.utils.EventType;
import io.micrometer.core.instrument.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MetricsCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsCollector.class);

    private final String EVENTS_PROCESSED = "events_processed";
    private final String EVENTS_PROCESSED_TIMER = "events_processed_timer";

    private final MeterRegistry meterRegistry;

    public MetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void eventProcessedSuccess(EventType eventType, long duration) {
        meterRegistry.counter(EVENTS_PROCESSED, Tags.of("eventType", eventType.name())).increment();
        meterRegistry.timer(EVENTS_PROCESSED_TIMER, Tags.of("eventType", eventType.name())).record(duration, TimeUnit.MILLISECONDS);
        LOGGER.info("Successfully processed event: {}, time elapsed: {}", eventType.name(), duration);
    }
}
