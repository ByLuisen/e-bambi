package com.e.bambi.inventory.domain.shared.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class BrandId extends BaseId<UUID> {
    public BrandId(UUID value) {
        super(value);
    }
}
