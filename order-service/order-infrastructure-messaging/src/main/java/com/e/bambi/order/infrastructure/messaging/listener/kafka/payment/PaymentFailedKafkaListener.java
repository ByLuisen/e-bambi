package com.e.bambi.order.infrastructure.messaging.listener.kafka.payment;

import com.e.bambi.order.application.port.inbound.message.listener.payment.PaymentFailedMessageListener;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

@Slf4j
@Component
public class PaymentFailedKafkaListener implements ReactiveKafkaConsumer<PaymentFailedEventPayload> {

    private Disposable subscription;
    private final PaymentFailedMessageListener paymentFailedMessageListener;
    private final ReactiveKafkaConsumerTemplate<String, PaymentFailedEventPayload> template;

    public PaymentFailedKafkaListener(
            Disposable subscription,
            PaymentFailedMessageListener paymentFailedMessageListener,
            ReactiveKafkaConsumerFactory factory,
            @Value("${kafka-consumer-config.payment-service-consumer-group-id}") String groupId,
            @Value("${payment-service.saga.order_orchestrator.process_payment}") String topicName
    ) {
        this.paymentFailedMessageListener = paymentFailedMessageListener;
        this.template = factory.create(groupId, topicName, String.class, PaymentFailedEventPayload.class);
    }


    @Override
    public void receive() {
        this.subscription = template.receive().subscribe();
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
