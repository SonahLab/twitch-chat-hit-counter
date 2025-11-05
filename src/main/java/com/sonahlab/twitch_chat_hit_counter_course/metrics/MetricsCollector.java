package com.sonahlab.twitch_chat_hit_counter_course.metrics;

import com.sonahlab.twitch_chat_hit_counter_course.rest.GreetingRestController;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MetricsCollector {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsCollector.class);

    private MeterRegistry meterRegistry;
    private final Counter pingCounter;

    public MetricsCollector(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        pingCounter = meterRegistry.counter("ping_counter");
    }

    @Scheduled(fixedRate = 10000) // Run every 10 seconds (10000 milliseconds)
    public void incrementPingCounter() {
        LOGGER.info("health checking metrics");
        pingCounter.increment();
    }
}
