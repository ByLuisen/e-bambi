package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.ProductStatus;
import com.commerce.inventory_service.dto.ProductStatusInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductStatusMapper {
    ProductStatus productStatusInputDTOToProductStatus(ProductStatusInputDTO productStatusInputDTO);
}