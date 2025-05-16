package com.e.bambi.payment.application.dto.command;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Mono;

public record DeletePaymentMethodByIdCommand(PaymentMethodId paymentMethodId) implements Command<Mono<Void>> {
}
