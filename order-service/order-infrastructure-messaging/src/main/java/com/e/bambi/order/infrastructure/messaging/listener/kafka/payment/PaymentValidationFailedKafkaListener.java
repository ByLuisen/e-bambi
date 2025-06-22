package com.e.bambi.order.infrastructure.messaging.listener.kafka.payment;

import com.e.bambi.order.infrastructure.messaging.mapper.OrderMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.KafkaConsumerHelper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodValidationFailedEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.PSQLState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.sql.SQLException;

@Slf4j
@Component
public class PaymentValidationFailedKafkaListener implements ReactiveKafkaConsumer<PaymentMethodValidationFailedEventPayload> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final KafkaConsumerHelper kafkaConsumerHelper;
    private final OrderMessagingMapper orderMessagingMapper;
    private final ReactiveKafkaConsumerTemplate<String, Object> template;

    public PaymentValidationFailedKafkaListener(
            CommandBus commandBus,
            OrderMessagingMapper orderMessagingMapper,
            KafkaConsumerHelper kafkaConsumerHelper,
            ReactiveKafkaConsumerFactory factory,
            @Value("${kafka-consumer-config.order-service-consumer-group-id}") String groupId,
            @Value("${order-service.payment.validation_failed-topic-name}") String topicName
    ) {
        this.commandBus = commandBus;
        this.orderMessagingMapper = orderMessagingMapper;
        this.kafkaConsumerHelper = kafkaConsumerHelper;
        this.template = factory.create(groupId, topicName, String.class, Object.class);
    }


    @Override
    public void receive() {
        subscription = template.receive()
                .concatMap(receiverRecord -> {
                    String sagaId = receiverRecord.key();
                    PaymentMethodValidationFailedEventPayload payload =
                            kafkaConsumerHelper.getEventPayload(receiverRecord.value().toString(),
                                    PaymentMethodValidationFailedEventPayload.class);

                    log.info("Incoming message in PaymentValidationFailedKafkaListener: {}, with key: {}, " +
                                    "partition: {} and offset: {}", payload, sagaId,
                            receiverRecord.partition(),
                            receiverRecord.offset());

                    return commandBus.dispatch(orderMessagingMapper.toValidationFailedPaymentCommand(payload, sagaId))
                            .then(Mono.fromRunnable(receiverRecord.receiverOffset()::acknowledge));

                })
                .doOnError(DataAccessException.class, e -> {
                            SQLException sqlException = (SQLException) e.getRootCause();
                            if (sqlException != null && sqlException.getSQLState() != null &&
                                    PSQLState.UNIQUE_VIOLATION.getState().equals(sqlException.getSQLState())) {
                                //NO-OP for unique constraint exception
                                log.error("Caught unique constraint exception with sql state: {} in " +
                                                "PaymentValidationFailedKafkaListener",
                                        sqlException.getSQLState());
                            }
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
