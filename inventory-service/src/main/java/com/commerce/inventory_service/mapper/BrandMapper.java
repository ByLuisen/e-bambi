package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.Brand;
import com.commerce.inventory_service.dto.BrandInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    Brand brandInputDTOToBrand(BrandInputDTO brandInputDTO);
}