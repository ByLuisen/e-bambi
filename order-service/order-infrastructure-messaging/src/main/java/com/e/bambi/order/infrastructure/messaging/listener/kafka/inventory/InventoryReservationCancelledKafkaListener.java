package com.e.bambi.order.infrastructure.messaging.listener.kafka.inventory;

import com.e.bambi.order.domain.exception.OrderNotFoundException;
import com.e.bambi.order.infrastructure.messaging.mapper.OrderMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.KafkaConsumerHelper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.domain.event.payload.inventory.InventoryReservationCancelledEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class InventoryReservationCancelledKafkaListener implements ReactiveKafkaConsumer<InventoryReservationCancelledEventPayload> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final OrderMessagingMapper orderMessagingMapper;
    private final KafkaConsumerHelper kafkaConsumerHelper;
    private final ReactiveKafkaConsumerTemplate<String, Object> template;

    InventoryReservationCancelledKafkaListener(
            CommandBus commandBus,
            OrderMessagingMapper orderMessagingMapper,
            KafkaConsumerHelper kafkaConsumerHelper,
            ReactiveKafkaConsumerFactory factory,
            @Value("${kafka-consumer-config.order-service-consumer-group-id}") String groupId,
            @Value("${order-service.inventory.reservation_cancelled-topic-name}") String topicName
    ) {
        this.commandBus = commandBus;
        this.orderMessagingMapper = orderMessagingMapper;
        this.kafkaConsumerHelper = kafkaConsumerHelper;
        template = factory.create(groupId, topicName, String.class, Object.class);
    }

    @Override
    public void receive() {
        subscription = template.receive()
                .flatMap(receiverRecord -> {
                    InventoryReservationCancelledEventPayload payload = kafkaConsumerHelper
                            .getEventPayload(receiverRecord.value().toString(),
                                    InventoryReservationCancelledEventPayload.class);

                    log.info("Incoming message in InventoryReservationCancelledKafkaListener: {}, with key: {}, " +
                                    "topic: {}, partition: {}, offset: {} and timestamp: {}", receiverRecord.value(),
                            receiverRecord.key(), receiverRecord.topic(), receiverRecord.partition(),
                            receiverRecord.offset(), kafkaConsumerHelper.formatTimestamp(receiverRecord.timestamp()));

                    return commandBus.dispatch(orderMessagingMapper.toReservationCancelledInventoryCommand(payload))
                            .onErrorResume(DuplicateKeyException.class, e -> {
                                log.error("Caught unique constraint exception in " +
                                        "InventoryReservationCancelledKafkaListener", e);
                                return Mono.empty();
                            })
                            .onErrorResume(OrderNotFoundException.class, e -> {
                                log.error("Caught OrderNotFoundException in " +
                                        "InventoryReservationCancelledKafkaListener", e);
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
