package com.e.bambi.order.application.order.dto.command.message.payment;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class ValidatedPaymentCommand extends Command<Mono<Void>> {
    private final OrderId orderId;
}
