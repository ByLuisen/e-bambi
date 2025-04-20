package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.Brand;
import com.e.bambi.inventory.service.dto.BrandInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand brandInputDTOToBrand(BrandInputDTO brandInputDTO);
}