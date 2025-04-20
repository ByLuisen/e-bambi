package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.MovementType;
import com.e.bambi.inventory.service.dto.MovementTypeInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovementTypeMapper {
    MovementType movementTypeInputDTOToMovementType(MovementTypeInputDTO movementTypeInputDTO);
}
