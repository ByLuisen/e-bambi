package com.e.bambi.payment.application.paymentmethod.port.outbound.repository;

import com.e.bambi.payment.domain.paymentmethod.entity.PaymentMethod;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Mono;

public interface PaymentMethodRepository {

    Mono<PaymentMethod> findById(PaymentMethodId paymentMethodId);

    Mono<PaymentMethod> update(PaymentMethod paymentMethod);

    Mono<PaymentMethod> insert(PaymentMethod paymentMethod);

    Mono<Integer> deleteById(PaymentMethodId paymentMethodId);
}
