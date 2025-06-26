package com.e.bambi.inventory.application.offer.dto.command.message;

import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ReserveInventoryCommand extends Command<Mono<Void>> {
    private final String sagaId;
    private final OrderId orderId;
    private final List<ReserveInventoryProduct> products;
}
