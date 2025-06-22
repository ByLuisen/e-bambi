package com.e.bambi.payment.application.paymentmethod.dto.command.message;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class ValidatePaymentCommand extends Command<Mono<Void>> {
    private final String sagaId;
    private final OrderId orderId;
    private final PaymentMethodId paymentMethodId;
}
