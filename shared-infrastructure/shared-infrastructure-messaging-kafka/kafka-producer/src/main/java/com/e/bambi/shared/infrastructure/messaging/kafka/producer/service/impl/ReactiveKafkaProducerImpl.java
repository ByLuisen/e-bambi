package com.e.bambi.shared.infrastructure.messaging.kafka.producer.service.impl;

import com.e.bambi.shared.infrastructure.messaging.kafka.producer.service.ReactiveKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.core.KafkaProducerException;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.SenderResult;

import java.io.Serializable;
import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveKafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements ReactiveKafkaProducer<K, V> {

    private final ReactiveKafkaProducerTemplate<K, V> reactiveKafkaProducerTemplate;

    @Override
    public void send(String topicName, K key, V message, Consumer<SenderResult<Void>> onNext,
                     Consumer<Throwable> onError) {
        log.info("Sending message = {} to topic = {}", message, topicName);
        try {
            reactiveKafkaProducerTemplate.send(topicName, key, message)
                    .subscribe(onNext, onError);
        } catch (KafkaProducerException e) {
            log.error("Error on Kafka producer with key: {}, message: {} and exception: {}", key, message,
                    e.getMessage());
            throw new KafkaProducerException(e.getFailedProducerRecord(), "Error on Kafka producer with key: "
                    + key + " and message: " + message, e);
        }
    }
}