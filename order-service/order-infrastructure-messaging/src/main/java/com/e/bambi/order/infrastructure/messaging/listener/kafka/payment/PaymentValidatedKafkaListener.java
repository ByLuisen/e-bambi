package com.e.bambi.order.infrastructure.messaging.listener.kafka.payment;

import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.order.infrastructure.messaging.mapper.OrderMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.KafkaConsumerHelper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidatedEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PaymentValidatedKafkaListener implements ReactiveKafkaConsumer<PaymentMethodValidatedEventPayload> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final KafkaConsumerHelper kafkaConsumerHelper;
    private final OrderMessagingMapper orderMessagingMapper;
    private final ReactiveKafkaConsumerTemplate<String, Object> template;

    PaymentValidatedKafkaListener(
            CommandBus commandBus,
            ReactiveKafkaConsumerFactory factory,
            KafkaConsumerHelper kafkaConsumerHelper,
            OrderMessagingMapper orderMessagingMapper,
            @Value("${kafka-consumer-config.order-service-consumer-group-id}") String groupId,
            @Value("${order-service.payment.validated-topic-name}") String topicName
    ) {
        this.commandBus = commandBus;
        this.kafkaConsumerHelper = kafkaConsumerHelper;
        this.orderMessagingMapper = orderMessagingMapper;
        this.template = factory.create(groupId, topicName, String.class, Object.class);
    }

    @Override
    public void receive() {
        subscription = template.receive()
                .concatMap(receiverRecord -> {
                    String sagaId = receiverRecord.key();
                    PaymentMethodValidatedEventPayload payload = kafkaConsumerHelper
                            .getEventPayload(receiverRecord.value().toString(), PaymentMethodValidatedEventPayload.class);

                    log.info("Incoming message in PaymentValidatedKafkaListener: {} with key: {}, partition: {} and " +
                                    "offset: {}", receiverRecord.value(), sagaId, receiverRecord.partition(),
                            receiverRecord.offset());

                    return commandBus.dispatch(orderMessagingMapper.toValidatedPaymentCommand(payload))
                            .onErrorResume(DuplicateKeyException.class, e -> {
                                log.error("Caught unique constraint exception in PaymentValidatedKafkaListener",
                                        e);
                                return Mono.empty();
                            })
                            .onErrorResume(OrderNotFoundException.class, e -> {
                                log.error("Caught OrderNotFoundException in PaymentValidatedKafkaListener", e);
                                return Mono.empty();
                            })
                            .then(Mono.fromRunnable(receiverRecord.receiverOffset()::acknowledge));
                })
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
