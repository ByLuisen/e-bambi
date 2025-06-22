package com.e.bambi.inventory.domain.shared.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class DepartmentId extends BaseId<UUID> {
    public DepartmentId(UUID value) {
        super(value);
    }
}
