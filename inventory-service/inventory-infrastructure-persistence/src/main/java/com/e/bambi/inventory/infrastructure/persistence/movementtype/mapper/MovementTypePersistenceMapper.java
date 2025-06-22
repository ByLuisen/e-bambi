package com.e.bambi.inventory.infrastructure.persistence.movementtype.mapper;

import com.e.bambi.inventory.domain.movementtype.entity.MovementType;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.infrastructure.persistence.movementtype.entity.MovementTypeEntity;
import org.springframework.stereotype.Component;

@Component
public class MovementTypePersistenceMapper {

    public MovementType toMovementType(MovementTypeEntity entity) {
        return MovementType.builder()
                .id(new MovementTypeId(entity.getId()))
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }

    public MovementTypeEntity toMovementTypeEntity(MovementType movementType) {
        return new MovementTypeEntity(
                movementType.getId().getValue(),
                movementType.getName(),
                movementType.getDescription()
        );
    }
}
