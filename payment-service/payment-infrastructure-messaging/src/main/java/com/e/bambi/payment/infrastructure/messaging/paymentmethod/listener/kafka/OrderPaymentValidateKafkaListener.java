package com.e.bambi.payment.infrastructure.messaging.paymentmethod.listener.kafka;

import com.e.bambi.payment.infrastructure.messaging.paymentmethod.mapper.PaymentMethodMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.KafkaConsumerHelper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderPaymentValidateEventPayload;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class OrderPaymentValidateKafkaListener implements ReactiveKafkaConsumer<OrderPaymentValidateEventPayload> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final KafkaConsumerHelper kafkaConsumerHelper;
    private final PaymentMethodMessagingMapper paymentMethodMessagingMapper;
    private final ReactiveKafkaConsumerTemplate<String, Object> template;

    OrderPaymentValidateKafkaListener(
            ReactiveKafkaConsumerFactory factory,
            CommandBus commandBus,
            KafkaConsumerHelper kafkaConsumerHelper,
            PaymentMethodMessagingMapper paymentMessagingMapper,
            @Value("${kafka-consumer-config.payment-service-consumer-group-id}") String groupId,
            @Value("${payment-service.order.payment.validate-topic-name}") String topicName
    ) {
        this.commandBus = commandBus;
        this.kafkaConsumerHelper = kafkaConsumerHelper;
        this.paymentMethodMessagingMapper = paymentMessagingMapper;
        this.template = factory.create(groupId, topicName, String.class, Object.class);
    }

    @Override
    public void receive() {
        subscription = template.receive()
                .flatMap(receiverRecord -> {
                            String sagaId = receiverRecord.key();
                            OrderPaymentValidateEventPayload payload =
                                    kafkaConsumerHelper.getEventPayload(receiverRecord.value().toString(),
                                            OrderPaymentValidateEventPayload.class);

                            log.info("Incoming message in OrderPaymentValidateKafkaListener: {} with key: {}, " +
                                            "topic: {}, partition: {}, offset: {} and timestamp: {}",
                                    receiverRecord.value(), sagaId, receiverRecord.topic(), receiverRecord.partition(),
                                    receiverRecord.offset(),
                                    kafkaConsumerHelper.formatTimestamp(receiverRecord.timestamp()));

                            return commandBus
                                    .dispatch(paymentMethodMessagingMapper.toValidatePaymentCommand(sagaId, payload))
                                    .onErrorResume(DuplicateKeyException.class, e -> {
                                        log.error("Caught unique constraint exception in " +
                                                "OrderPaymentValidateKafkaListener", e);
                                        return Mono.empty();
                                    })
                                    .then(Mono.fromRunnable(receiverRecord.receiverOffset()::acknowledge));
                        }
                )
                .subscribe();
    }

    @Override
    public void start() {
        receive();
    }

    @Override
    public void stop() {
        if (isRunning()) {
            subscription.dispose();
        }

        template.doOnConsumer(consumer -> {
            consumer.close();
            return null;
        });
    }

    @Override
    public boolean isRunning() {
        return subscription != null && !subscription.isDisposed();
    }
}
