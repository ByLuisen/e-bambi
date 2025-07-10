package com.e.bambi.inventory.application.inventorymovement.dto.command;

import com.e.bambi.inventory.application.inventorymovement.dto.response.InventoryMovementResponse;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.application.bus.Command;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Getter
@RequiredArgsConstructor
public class CreateInventoryMovementCommand extends Command<Mono<InventoryMovementResponse>> {
    private final SupplierId supplierId;
    private final CreateInventoryMovementProduct product;
    private final MovementTypeId movementTypeId;
    private final Integer quantity;
}
