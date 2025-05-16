package com.e.bambi.payment.infrastructure.persistence.adapter;

import com.e.bambi.payment.application.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.entity.PaymentMethod;
import com.e.bambi.payment.infrastructure.persistence.mapper.PaymentMethodPersistenceMapper;
import com.e.bambi.payment.infrastructure.persistence.repository.PaymentMethodR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

    private final PaymentMethodR2dbcRepository paymentMethodR2dbcRepository;
    private final PaymentMethodPersistenceMapper paymentMethodPersistenceMapper;

    @Override
    public Mono<PaymentMethod> findById(PaymentMethodId paymentMethodId) {
        return paymentMethodR2dbcRepository
                .findById(paymentMethodId.getValue())
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Flux<PaymentMethod> findAll() {
        return paymentMethodR2dbcRepository.findAll()
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Mono<PaymentMethod> save(PaymentMethod paymentMethod) {
        return paymentMethodR2dbcRepository
                .save(paymentMethodPersistenceMapper.toPaymentMethodEntity(paymentMethod))
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Mono<Integer> deletePaymentMethodById(PaymentMethodId paymentMethodId) {
        return paymentMethodR2dbcRepository
                .deletePaymentMethodById(paymentMethodId.getValue());
    }
}
