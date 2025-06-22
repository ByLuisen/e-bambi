package com.e.bambi.inventory.application.supplier.mapper;

import com.e.bambi.inventory.application.supplier.dto.command.UpdateSupplierCommand;
import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.inventory.domain.supplier.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierApplicationMapper {

    public Supplier toSupplier(UpdateSupplierCommand command) {
        return Supplier.builder()
                .id(command.getSupplierId())
                .name(command.getName())
                .build();
    }

    public SupplierResponse toSupplierResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getId().getValue(),
                supplier.getName()
        );
    }
}
