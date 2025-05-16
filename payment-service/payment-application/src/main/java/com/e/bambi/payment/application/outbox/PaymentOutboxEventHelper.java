package com.e.bambi.payment.application.outbox;

import com.e.bambi.payment.application.outbox.model.PaymentOutboxEvent;
import com.e.bambi.payment.application.port.outbound.repository.PaymentOutboxEventRepository;
import com.e.bambi.payment.domain.exception.PaymentDomainException;
import com.e.bambi.payment.domain.exception.PaymentOutboxEventNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public void savePaymentOutboxEvent(String aggregatetype, Object payload, @Nullable String sagaId) {
        save(PaymentOutboxEvent.builder()
                .id(UUID.randomUUID())
                .aggregatetype(aggregatetype)
                .aggregateid(sagaId == null ? UUID.randomUUID().toString() : sagaId)
                .eventType(ORDER_SAGA_NAME)
                .payload(createPayload(payload))
                .build());
    }

    public void deletePaymentOutboxEvent(UUID paymentOutboxEventId) {
        paymentOutboxEventRepository.deleteById(paymentOutboxEventId)
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

    private void save(PaymentOutboxEvent paymentOutboxEvent) {
        paymentOutboxEventRepository.save(paymentOutboxEvent)
                .doOnError(error ->
                        Mono.error(
                                new PaymentDomainException("Could not save PaymentOutboxEvent with " +
                                        "aggregateid: " + paymentOutboxEvent.getAggregateid() + " and payload: " +
                                        paymentOutboxEvent.getPayload())
                        )
                )
                .doOnSuccess(saved -> {
                    log.info("PaymentOutboxEvent is saved with id: {}", paymentOutboxEvent.getId());
                });
    }

    private String createPayload(Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new PaymentDomainException("Could not create OrderEventPayload json!", e);
        }
    }
}
