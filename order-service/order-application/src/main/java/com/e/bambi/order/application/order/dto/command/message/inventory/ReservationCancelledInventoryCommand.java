package com.e.bambi.order.application.order.dto.command.message.inventory;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class ReservationCancelledInventoryCommand extends Command<Mono<Void>> {
    private final OrderId orderId;
}
