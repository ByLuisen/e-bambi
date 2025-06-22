package com.e.bambi.payment.application.paymentmethod.dto.command;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class DeletePaymentMethodByIdCommand extends Command<Mono<Void>> {
    private final PaymentMethodId paymentMethodId;
}
