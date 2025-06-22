package com.e.bambi.payment.application.outbox;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import com.e.bambi.payment.application.outbox.port.outbound.repository.PaymentOutboxEventRepository;
import com.e.bambi.payment.domain.exception.PaymentDomainException;
import com.e.bambi.payment.domain.exception.PaymentOutboxEventNotFoundException;
import com.e.bambi.shared.kernel.domain.event.payload.payment.PaymentMethodEventPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.annotation.Nullable;

import java.util.UUID;

import static com.e.bambi.shared.kernel.application.saga.order.SagaConstants.ORDER_SAGA_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentOutboxEventHelper {

    private final ObjectMapper objectMapper;
    private final PaymentOutboxEventRepository paymentOutboxEventRepository;

    public Mono<Void> savePaymentOutboxEvent(String aggregatetype, PaymentMethodEventPayload payload,
                                             @Nullable String sagaId) {
        return save(PaymentOutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregatetype(aggregatetype)
                .aggregateid(sagaId == null ? UUID.randomUUID().toString() : sagaId)
                .eventType(ORDER_SAGA_NAME)
                .payload(createPayload(payload))
                .build());
    }

    public Mono<Void> deletePaymentOutboxEvent(UUID paymentOutboxEventId) {
        return paymentOutboxEventRepository.deleteById(paymentOutboxEventId)
                .handle((updatedRows, sink) -> {
                            if (updatedRows < 1) {
                                sink.error(new PaymentOutboxEventNotFoundException("PaymentOutboxEvent with id: " +
                                        paymentOutboxEventId + "could not be found"));
                            } else {
                                log.info("PaymentOutboxEvent with id: {} successfully deleted", paymentOutboxEventId);
                                sink.complete();
                            }
                        }
                );
    }

    public Mono<Boolean> existsPaymentOutboxEventByAggregateidAndAggregateType(String aggregateid,
                                                                               String... aggregatetype) {
        return paymentOutboxEventRepository
                .existsByAggregateIdAndAggregateTypeIn(aggregateid, aggregatetype);
    }

    private Mono<Void> save(PaymentOutboxEvent paymentOutboxEvent) {
        return paymentOutboxEventRepository.insert(paymentOutboxEvent)
                .doOnError(error ->
                        Mono.error(
                                new PaymentDomainException("Could not save PaymentOutboxEvent with " +
                                        "aggregateid: " + paymentOutboxEvent.getAggregateid() + " and payload: " +
                                        paymentOutboxEvent.getPayload())
                        )
                )
                .doOnSuccess(saved ->
                        log.info("PaymentOutboxEvent is saved with id: {}", paymentOutboxEvent.getId())
                )
                .then();
    }

    private String createPayload(PaymentMethodEventPayload payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new PaymentDomainException("Could not create PaymentMethodEventPayload json!", e);
        }
    }
}
