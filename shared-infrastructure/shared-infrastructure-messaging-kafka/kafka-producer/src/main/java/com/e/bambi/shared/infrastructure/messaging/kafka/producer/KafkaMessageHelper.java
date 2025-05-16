package com.e.bambi.shared.infrastructure.messaging.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.stereotype.Component;
import reactor.kafka.sender.SenderResult;

import java.util.function.Consumer;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessageHelper {

    public <T> Consumer<Throwable> getKafkaCallbackOnError(String responseTopicName, T avroModel, String avroModelName) {
        return (ex) -> {
            log.error("Error while sending {} message {} to topic {}", avroModelName, avroModel.toString(),
                    responseTopicName, ex);
        };
    }

    public Consumer<SenderResult<Void>> getKafkaCallbackOnNext(String orderId) {
        return (result) -> {
            RecordMetadata recordMetadata = result.recordMetadata();
            log.info("Received successful response from kafka for order id: {}" +
                            " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                    orderId,
                    recordMetadata.topic(),
                    recordMetadata.partition(),
                    recordMetadata.offset(),
                    recordMetadata.timestamp());
        };
    }


}
