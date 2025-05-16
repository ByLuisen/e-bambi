package com.commerce.inventory_service.mapper;

import com.commerce.inventory_service.domain.Supplier;
import com.commerce.inventory_service.dto.SupplierInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier supplierInputDTOToSupplier(SupplierInputDTO supplierInputDTO);
}