package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.InventoryMovement;
import com.commerce.inventory_service.dto.InventoryMovementInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementMapper {

    InventoryMovement inventoryMovementInputDTOToInventoryMovement(InventoryMovementInputDTO inventoryMovementInputDTO);
}
