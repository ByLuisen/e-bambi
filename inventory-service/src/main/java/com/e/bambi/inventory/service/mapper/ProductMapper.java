package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.Product;
import com.e.bambi.inventory.service.dto.ProductInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product productInputDTOToProduct(ProductInputDTO productInputDTO);
}
