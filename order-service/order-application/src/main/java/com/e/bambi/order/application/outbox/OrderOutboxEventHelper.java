package com.e.bambi.order.application.outbox;

import com.e.bambi.order.application.outbox.model.OrderOutboxEvent;
import com.e.bambi.order.application.outbox.port.outbound.repository.OrderOutboxEventRepository;
import com.e.bambi.order.domain.exception.OrderDomainException;
import com.e.bambi.shared.kernel.application.saga.SagaStatus;
import com.e.bambi.shared.kernel.domain.event.payload.order.OrderEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderOutboxEventHelper {

    private final ObjectMapper objectMapper;
    private final OrderOutboxEventRepository orderOutboxEventRepository;

    public Mono<UUID> saveOrderOutboxEvent(String aggregatetype, OrderEventPayload payload,
                                           String sagaId, @Nullable String eventType,
                                           @Nullable SagaStatus sagaStatus) {
        UUID orderOutboxEventId = UUID.randomUUID();
        return save(OrderOutboxEvent.builder()
                .id(orderOutboxEventId)
                .aggregatetype(aggregatetype)
                .aggregateid(sagaId)
                .eventType(eventType)
                .sagaStatus(sagaStatus)
                .payload(createPayload(payload))
                .build()
        ).thenReturn(orderOutboxEventId);
    }

    public Mono<Void> deleteOrderOutboxEvent(UUID orderOutboxEventId) {
        return orderOutboxEventRepository.deleteById(orderOutboxEventId)
                .handle((updatedRows, sink) -> {
                    if (updatedRows < 1) {
                        sink.error(new OrderDomainException("OrderOutboxEvent with id: " + orderOutboxEventId +
                                " could not be found"));
                    } else {
                        log.info("OrderOutboxEvent with id: {} successfully deleted", orderOutboxEventId);
                        sink.complete();
                    }
                });
    }

    private Mono<Void> save(OrderOutboxEvent orderOutboxEvent) {
        return orderOutboxEventRepository.insert(orderOutboxEvent)
                .doOnError(error -> {
                    Mono.error(new OrderDomainException("Could not save OrderOutboxEvent with aggregateid: " +
                            orderOutboxEvent.getAggregateid() + " and payload: " + orderOutboxEvent.getPayload()));
                })
                .doOnSuccess(saved ->
                        log.info("OrderOutboxEvent is saved with id: " + orderOutboxEvent.getId()))
                .then();
    }

    private String createPayload(OrderEventPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new OrderDomainException("Could not create OrderEventPayload json!", e);
        }
    }
}
