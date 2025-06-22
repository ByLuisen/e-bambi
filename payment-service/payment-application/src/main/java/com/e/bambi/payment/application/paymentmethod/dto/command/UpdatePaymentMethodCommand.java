package com.e.bambi.payment.application.paymentmethod.dto.command;

import com.e.bambi.payment.application.paymentmethod.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdatePaymentMethodCommand extends Command<Mono<PaymentMethodResponse>> {
    private final PaymentMethodId paymentMethodId;
    private final String name;
    private final String description;
}