package com.e.bambi.inventory.domain.shared.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class MovementTypeId extends BaseId<UUID> {
    public MovementTypeId(UUID value) {
        super(value);
    }
}
