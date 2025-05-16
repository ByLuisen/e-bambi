package com.e.bambi.payment.application.dto.command;

import com.e.bambi.payment.application.dto.response.PaymentMethodResponse;
import com.e.bambi.shared.kernel.application.bus.Command;
import reactor.core.publisher.Mono;

public record CreatePaymentMethodCommand(String name,
                                         String description) implements Command<Mono<PaymentMethodResponse>> {
}
