package com.e.bambi.payment.application.dto.query;

import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Mono;

public record PaymentMethodByIdQuery(PaymentMethodId paymentMethodId) implements Query<Mono<PaymentMethodResponse>> {
}
