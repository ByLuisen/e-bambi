package com.e.bambi.inventory.domain.image.valueobject;

import com.e.bambi.shared.kernel.domain.valueobject.BaseId;

import java.util.UUID;

public class ImageId extends BaseId<UUID> {
    public ImageId(UUID value) {
        super(value);
    }
}
