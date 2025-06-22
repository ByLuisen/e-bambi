package com.e.bambi.payment.infrastructure.persistence.paymentmethod.adapter;

import com.e.bambi.payment.application.paymentmethod.port.outbound.repository.PaymentMethodRepository;
import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.mapper.PaymentMethodPersistenceMapper;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.repository.PaymentMethodR2dbcEntityTemplate;
import com.e.bambi.payment.infrastructure.persistence.paymentmethod.repository.PaymentMethodR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

    private final PaymentMethodR2dbcEntityTemplate paymentMethodR2dbcEntityTemplate;
    private final PaymentMethodR2dbcRepository paymentMethodR2dbcRepository;
    private final PaymentMethodPersistenceMapper paymentMethodPersistenceMapper;

    @Override
    public Mono<PaymentMethod> findById(PaymentMethodId paymentMethodId) {
        return paymentMethodR2dbcRepository
                .findById(paymentMethodId.getValue())
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Mono<PaymentMethod> update(PaymentMethod paymentMethod) {
        return paymentMethodR2dbcRepository
                .save(paymentMethodPersistenceMapper.toPaymentMethodEntity(paymentMethod))
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Mono<PaymentMethod> insert(PaymentMethod paymentMethod) {
        return paymentMethodR2dbcEntityTemplate
                .insert(paymentMethodPersistenceMapper.toPaymentMethodEntity(paymentMethod))
                .map(paymentMethodPersistenceMapper::toPaymentMethod);
    }

    @Override
    public Mono<Integer> deleteById(PaymentMethodId paymentMethodId) {
        return paymentMethodR2dbcRepository
                .deletePaymentMethodById(paymentMethodId.getValue());
    }
}
