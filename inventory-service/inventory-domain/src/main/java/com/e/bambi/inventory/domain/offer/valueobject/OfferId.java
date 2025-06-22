package com.e.bambi.inventory.domain.offer.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class OfferId extends BaseId<UUID> {
    public OfferId(UUID value) {
        super(value);
    }
}
