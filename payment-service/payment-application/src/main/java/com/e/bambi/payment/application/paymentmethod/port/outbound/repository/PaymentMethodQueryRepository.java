package com.e.bambi.payment.application.paymentmethod.port.outbound.repository;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodReadResponse;
import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentMethodQueryRepository {

    Mono<PaymentMethodReadResponse> findById(PaymentMethodId paymentMethodId);

    Flux<PaymentMethodResponse> findAll();
}
