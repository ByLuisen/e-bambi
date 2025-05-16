package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.MovementType;
import com.commerce.inventory_service.dto.MovementTypeInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementTypeMapper {
    MovementType movementTypeInputDTOToMovementType(MovementTypeInputDTO movementTypeInputDTO);
}
