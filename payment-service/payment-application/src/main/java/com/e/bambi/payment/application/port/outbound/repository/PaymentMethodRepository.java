package com.e.bambi.payment.application.port.outbound.repository;

import com.e.bambi.payment.domain.entity.PaymentMethod;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentMethodRepository {
    Mono<PaymentMethod> findById(PaymentMethodId paymentMethodId);
    Flux<PaymentMethod> findAll();
    Mono<PaymentMethod> save(PaymentMethod paymentMethod);
    Mono<Integer> deletePaymentMethodById(PaymentMethodId paymentMethodId);
}
