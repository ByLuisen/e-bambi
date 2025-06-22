package com.e.bambi.inventory.application.inventorymovement.dto.command;

import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class UpdateInventoryMovementCommand extends Command<Mono<InventoryMovementResponse>> {
    private final InventoryMovementId inventoryMovementId;
    private final Integer quantity;
}
