package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.InventoryMovement;
import com.e.bambi.inventory.service.dto.InventoryMovementInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMovementMapper {

    InventoryMovement inventoryMovementInputDTOToInventoryMovement(InventoryMovementInputDTO inventoryMovementInputDTO);
}
