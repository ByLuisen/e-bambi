package com.e.bambi.inventory.domain.productstatus.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class ProductStatusId extends BaseId<UUID> {

    public ProductStatusId(UUID value) {
        super(value);
    }
}
