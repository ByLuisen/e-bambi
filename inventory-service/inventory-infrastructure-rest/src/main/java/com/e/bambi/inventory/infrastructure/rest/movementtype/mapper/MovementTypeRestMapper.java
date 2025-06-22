package com.e.bambi.inventory.infrastructure.rest.movementtype.mapper;

import com.e.bambi.inventory.application.movementtype.dto.command.CreateMovementTypeCommand;
import com.e.bambi.inventory.application.movementtype.dto.command.UpdateMovementTypeCommand;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.infrastructure.rest.movementtype.dto.request.CreateMovementTypeRequestDto;
import com.e.bambi.inventory.infrastructure.rest.movementtype.dto.request.UpdateMovementTypeRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovementTypeRestMapper {

    public CreateMovementTypeCommand toCreateMovementTypeCommand(CreateMovementTypeRequestDto request) {
        return new CreateMovementTypeCommand(
                request.getName(),
                request.getDescription()
        );
    }

    public UpdateMovementTypeCommand toUpdateMovementTypeCommand(String movementTypeId,
                                                                 UpdateMovementTypeRequestDto request) {
        return new UpdateMovementTypeCommand(
                new MovementTypeId(UUID.fromString(movementTypeId)),
                request.getName(),
                request.getDescription()
        );
    }
}
