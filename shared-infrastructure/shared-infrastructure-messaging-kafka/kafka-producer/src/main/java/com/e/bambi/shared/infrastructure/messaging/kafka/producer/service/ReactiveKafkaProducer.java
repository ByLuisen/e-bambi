package com.e.bambi.shared.infrastructure.messaging.kafka.producer.service;

import org.apache.avro.specific.SpecificRecordBase;
import reactor.kafka.sender.SenderResult;

import java.io.Serializable;
import java.util.function.Consumer;

public interface ReactiveKafkaProducer<K extends Serializable, V extends SpecificRecordBase> {
   void send(String topicName, K key, V message, Consumer<SenderResult<Void>> onNext, Consumer<Throwable> onError);
}
