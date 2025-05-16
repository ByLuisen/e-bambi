package com.e.bambi.shared.infrastructure.messaging.kafka.consumer;

import org.springframework.context.SmartLifecycle;

public interface ReactiveKafkaConsumer<T> extends SmartLifecycle {
    void receive();
}
