package com.e.bambi.inventory.application.brand.mapper;

import com.e.bambi.inventory.application.brand.dto.command.CreateBrandCommand;
import com.e.bambi.inventory.application.brand.dto.command.UpdateBrandCommand;
import com.e.bambi.inventory.application.brand.dto.response.BrandResponse;
import com.e.bambi.inventory.domain.brand.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandApplicationMapper {

    public Brand createBrandCommandToBrand(CreateBrandCommand command) {
        return new Brand(command.getName());
    }

    public Brand updateBrandCommandToBrand(UpdateBrandCommand command) {
        return new Brand(
                command.getBrandId(),
                command.getName()
        );
    }

    public BrandResponse toBrandResponse(Brand brand) {
        return new BrandResponse(
                brand.getId().getValue(),
                brand.getName()
        );
    }
}
