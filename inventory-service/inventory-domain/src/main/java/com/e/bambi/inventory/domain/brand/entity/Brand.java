package com.e.bambi.inventory.domain.brand.entity;

import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Brand extends AggregateRoot<BrandId> {

    private final String name;

    public Brand(String name) {
        this.name = name;
    }

    public Brand(BrandId brandId, String name) {
        super.setId(brandId);
        this.name = name;
    }

    public void initializeBrand() {
        super.setId(new BrandId(UUID.randomUUID()));
    }
}
