package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.ProductStatus;
import com.e.bambi.inventory.service.dto.ProductStatusInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductStatusMapper {
    ProductStatus productStatusInputDTOToProductStatus(ProductStatusInputDTO productStatusInputDTO);
}