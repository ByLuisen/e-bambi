package com.e.bambi.payment.application.dto.command;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import reactor.core.publisher.Mono;

public record ValidatePaymentCommand(String sagaId, OrderId orderId,
                                     PaymentMethodId paymentMethodId) implements Command<Mono<Void>> {
}
