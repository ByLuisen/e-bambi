package com.e.bambi.inventory.infrastructure.rest.inventorymovement.mapper;

import com.e.bambi.inventory.application.inventorymovement.dto.command.CreateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.command.UpdateInventoryMovementCommand;
import com.e.bambi.inventory.application.inventorymovement.dto.query.InventoryMovementQuery;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.CreateInventoryMovementRequestDto;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.InventoryMovementRequestDto;
import com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request.UpdateInventoryMovementRequestDto;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class InventoryMovementRestMapper {

    public InventoryMovementQuery toInventoryMovementQuery(InventoryMovementRequestDto request) {
        return InventoryMovementQuery.builder()
                .supplierIds(convert(request.getSupplierId(), UUID.class))
                .productIds(convert(request.getProductId(), UUID.class))
                .movementTypeIds(convert(request.getMovementTypeId(), UUID.class))
                .productSkus(convert(request.getProductSku(), String.class))
                .orderBy(request.getOrderBy())
                .page(request.getPage())
                .size(request.getSize())
                .build();
    }

    public CreateInventoryMovementCommand toCreateInventoryMovementCommand(CreateInventoryMovementRequestDto request) {
        return new CreateInventoryMovementCommand(
                new SupplierId(UUID.fromString(request.getSupplierId())),
                new ProductId(UUID.fromString(request.getProductId())),
                new MovementTypeId(UUID.fromString(request.getMovementTypeId())),
                request.getProductSku(),
                request.getProductName(),
                request.getQuantity()
        );
    }

    public UpdateInventoryMovementCommand toUpdateInventoryMovementCommand(String inventoryMovementId,
                                                                           UpdateInventoryMovementRequestDto request) {
        return new UpdateInventoryMovementCommand(
                new InventoryMovementId(UUID.fromString(inventoryMovementId)),
                request.getQuantity()
        );
    }

    private <T> List<T> convert(String chain, Class<T> type) {
        return Arrays.stream(chain.split("\\|"))
                .map(item -> {
                    if (UUID.class.equals(type)) {
                        @SuppressWarnings("unchecked")
                        T value = (T) UUID.fromString(item);
                        return value;
                    }
                    @SuppressWarnings("unchecked")
                    T value = (T) item;
                    return value;
                })
                .toList();
    }
}
