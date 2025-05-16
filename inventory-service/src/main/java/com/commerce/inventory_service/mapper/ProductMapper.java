package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.Product;
import com.commerce.inventory_service.dto.ProductInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productInputDTOToProduct(ProductInputDTO productInputDTO);
}
