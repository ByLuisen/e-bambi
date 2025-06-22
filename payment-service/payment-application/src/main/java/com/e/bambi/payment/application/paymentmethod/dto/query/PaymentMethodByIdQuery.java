package com.e.bambi.payment.application.paymentmethod.dto.query;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodReadResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class PaymentMethodByIdQuery extends Query<Mono<PaymentMethodReadResponse>> {
    private final PaymentMethodId paymentMethodId;
}
