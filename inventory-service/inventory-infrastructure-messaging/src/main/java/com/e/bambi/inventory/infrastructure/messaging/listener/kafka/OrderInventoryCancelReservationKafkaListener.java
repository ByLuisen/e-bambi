package com.e.bambi.inventory.infrastructure.messaging.listener.kafka;

import com.e.bambi.inventory.infrastructure.messaging.mapper.InventoryMessagingMapper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.KafkaConsumerHelper;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumer;
import com.e.bambi.shared.infrastructure.messaging.kafka.consumer.ReactiveKafkaConsumerFactory;
import com.e.bambi.shared.kernel.application.port.inbound.bus.CommandBus;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderInventoryCancelReservationEventPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

@Slf4j
@Component
public class OrderInventoryCancelReservationKafkaListener
        implements ReactiveKafkaConsumer<OrderInventoryCancelReservationEventPayload> {

    private Disposable subscription;
    private final CommandBus commandBus;
    private final KafkaConsumerHelper kafkaConsumerHelper;
    private final InventoryMessagingMapper inventoryMessagingMapper;
    private final ReactiveKafkaConsumerTemplate<String, Object> template;

    OrderInventoryCancelReservationKafkaListener(
            CommandBus commandBus,
            KafkaConsumerHelper kafkaConsumerHelper,
            ReactiveKafkaConsumerFactory factory,
            InventoryMessagingMapper inventoryMessagingMapper,
            @Value("${kafka-consumer-config.inventory-service-consumer-group-id}") String groupId,
            @Value("${inventory-service.order.inventory.cancel_reservation-topic-name}") String topicName
    ) {
        this.commandBus = commandBus;
        this.kafkaConsumerHelper = kafkaConsumerHelper;
        this.inventoryMessagingMapper = inventoryMessagingMapper;
        this.template = factory.create(groupId, topicName, String.class, Object.class);
    }

    @Override
    public void receive() {
        subscription = template.receive()
                .concatMap(receiverRecord -> {
                    OrderInventoryCancelReservationEventPayload payload =
                            kafkaConsumerHelper.getEventPayload(
                                    receiverRecord.value().toString(),
                                    OrderInventoryCancelReservationEventPayload.class);
                    String sagaId = receiverRecord.key();

                    log.info("Incoming message in OrderInventoryCancelReservationKafkaListener: {} with key: {}, " +
                            "partition: {} and offset: {}", payload, sagaId, receiverRecord.partition(),
                            receiverRecord.offset());

//                    commandBus.dispatch();
                    return null;
                }).subscribe();
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
