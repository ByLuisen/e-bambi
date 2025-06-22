package com.e.bambi.payment.infrastructure.persistence.paymentmethod.repository;

import com.e.bambi.payment.infrastructure.persistence.paymentmethod.entity.PaymentMethodEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PaymentMethodR2dbcEntityTemplate {

    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    public Mono<PaymentMethodEntity> insert(PaymentMethodEntity paymentMethod) {
        return r2dbcEntityTemplate.insert(paymentMethod);
    }
}
