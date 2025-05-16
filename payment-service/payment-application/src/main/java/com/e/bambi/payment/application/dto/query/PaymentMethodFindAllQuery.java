package com.e.bambi.payment.application.dto.query;

import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public record PaymentMethodFindAllQuery() implements Query<Flux<PaymentMethodResponse>> {
}
