package com.e.bambi.inventory.application.movementtype.mapper;

import com.e.bambi.inventory.application.movementtype.dto.command.CreateMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.command.UpdateMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.response.MovementTypeResponse;
import com.e.bambi.inventory.domain.movementtype.entity.MovementType;
import org.springframework.stereotype.Component;

@Component
public class MovementTypeApplicationMapper {

    public MovementType createMovementTypeCommandToMovementType(CreateMovementTypeCommand command) {
        return MovementType.builder()
                .name(command.getName())
                .description(command.getDescription())
                .build();
    }

    public MovementType updateMovementTypeCommandToMovementType(UpdateMovementTypeCommand command) {
        return MovementType.builder()
                .id(command.getMovementTypeId())
                .name(command.getName())
                .description(command.getDescription())
                .build();
    }

    public MovementTypeResponse toMovementTypeResponse(MovementType movementType) {
        return new MovementTypeResponse(
                movementType.getId().getValue(),
                movementType.getName(),
                movementType.getDescription()
        );
    }
}
