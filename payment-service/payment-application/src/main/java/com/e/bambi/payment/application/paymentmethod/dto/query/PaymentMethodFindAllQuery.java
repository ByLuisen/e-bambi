package com.e.bambi.payment.application.paymentmethod.dto.query;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.application.bus.Query;
import reactor.core.publisher.Flux;

public class PaymentMethodFindAllQuery extends Query<Flux<PaymentMethodResponse>> {
}
