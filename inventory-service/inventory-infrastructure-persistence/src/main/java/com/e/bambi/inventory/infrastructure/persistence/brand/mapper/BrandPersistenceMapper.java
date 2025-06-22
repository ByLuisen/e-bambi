package com.e.bambi.inventory.infrastructure.persistence.brand.mapper;

import com.e.bambi.inventory.domain.brand.entity.Brand;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.infrastructure.persistence.brand.entity.BrandEntity;
import org.springframework.stereotype.Component;

@Component
public class BrandPersistenceMapper {

    public Brand toBrand(BrandEntity brandEntity) {
        return new Brand(
                new BrandId(brandEntity.getId()),
                brandEntity.getName()
        );
    }

    public BrandEntity toBrandEntity(Brand brand) {
        return new BrandEntity(
                brand.getId().getValue(),
                brand.getName()
        );
    }
}
