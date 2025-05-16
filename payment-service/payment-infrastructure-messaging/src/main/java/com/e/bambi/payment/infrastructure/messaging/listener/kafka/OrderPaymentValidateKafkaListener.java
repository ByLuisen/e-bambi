package com.e.bambi.payment.infrastructure.messaging.listener.kafka;

import com.e.bambi.payment.infrastructure.messaging.dto.listener.ValidatePaymentMessageDTO;
import com.e.bambi.payment.infrastructure.messaging.mapper.PaymentMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

@Slf4j
@Component
public class OrderPaymentValidateKafkaListener implements ReactiveKafkaConsumer<ValidatePaymentMessageDTO> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final PaymentMessagingMapper paymentMessagingMapper;
    private final ReactiveKafkaConsumerTemplate<String, ValidatePaymentMessageDTO> template;

    OrderPaymentValidateKafkaListener(
            ReactiveKafkaConsumerFactory factory,
            CommandBus commandBus,
            PaymentMessagingMapper paymentMessagingMapper,
            @Value("${kafka-consumer-config.payment-service-consumer-group-id}") String groupId,
            @Value("${payment-service.saga.order_orchestrator.process_payment}") String topicName
    ) {
        this.commandBus = commandBus;
        this.paymentMessagingMapper = paymentMessagingMapper;
        this.template = factory.create(groupId, topicName, String.class, ValidatePaymentMessageDTO.class);
    }

    @Override
    public void receive() {
        this.subscription = template.receive()
                .doOnNext(receiverRecord -> {
                            String sagaId = receiverRecord.key();
                            ValidatePaymentMessageDTO payload = receiverRecord.value();

                            log.info("Incoming message in OrderPaymentValidateKafkaListener: {} with key: {}, " +
                                            "partition: {} and offset: {}", payload, sagaId, receiverRecord.partition(),
                                    receiverRecord.offset());
                            log.info("Validating payment for order id: {}", payload.orderId());

                            commandBus.dispatch(paymentMessagingMapper.toValidatePaymentCommand(sagaId, payload));
                        }
                )
                .subscribe();
    }

    @Override
    public void start() {
        this.receive();
    }

    @Override
    public void stop() {
        if (isRunning()) {
            this.subscription.dispose();
        }

        template.doOnConsumer(consumer -> {
            consumer.close();
            log.info("The consumer is now gracefully shutdown");
            return null;
        }).subscribe();
    }

    @Override
    public boolean isRunning() {
        return this.subscription != null && !this.subscription.isDisposed();
    }
}
