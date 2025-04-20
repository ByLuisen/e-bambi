package com.e.bambi.inventory.service.mapper;

import com.e.bambi.inventory.service.model.Supplier;
import com.e.bambi.inventory.service.dto.SupplierInputDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier supplierInputDTOToSupplier(SupplierInputDTO supplierInputDTO);
}