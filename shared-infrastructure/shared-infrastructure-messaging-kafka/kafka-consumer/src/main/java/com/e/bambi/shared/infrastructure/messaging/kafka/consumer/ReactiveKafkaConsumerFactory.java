package com.e.bambi.shared.infrastructure.messaging.kafka.consumer;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.kafka.receiver.ReceiverOptions;

import java.util.Collections;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReactiveKafkaConsumerFactory {

    private final Map<String, Object> consumerConfigs;

    public <K, V> ReactiveKafkaConsumerTemplate<K, V> create(String groupId, String topic,
                                                             Class<K> keyType, Class<V> valueType) {

        ReceiverOptions<K, V> opts = ReceiverOptions.<K, V>create(consumerConfigs)
                .consumerProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId)
                .subscription(Collections.singletonList(topic));
        return new ReactiveKafkaConsumerTemplate<>(opts);
    }
}