package com.e.bambi.inventory.domain.movementtype.entity;

import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import lombok.Getter;

import java.util.UUID;

@Getter
public class MovementType extends AggregateRoot<MovementTypeId> {

    private final String name;
    private final String description;

    public void initializeMovementType() {
        super.setId(new MovementTypeId(UUID.randomUUID()));
    }

    private MovementType(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        description = builder.description;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private MovementTypeId id;
        private String name;
        private String description;

        private Builder() {
        }

        public Builder id(MovementTypeId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public MovementType build() {
            return new MovementType(this);
        }
    }
}
