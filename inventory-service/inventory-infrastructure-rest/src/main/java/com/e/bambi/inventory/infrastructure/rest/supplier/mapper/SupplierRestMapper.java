package com.e.bambi.inventory.infrastructure.rest.supplier.mapper;

import com.e.bambi.inventory.application.supplier.dto.command.UpdateSupplierCommand;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.infrastructure.rest.supplier.dto.request.UpdateSupplierRequestDto;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplierRestMapper {

    public UpdateSupplierCommand toUpdateSupplierCommand(String supplierId,
                                                         UpdateSupplierRequestDto updateSupplierRequestDto) {
        return new UpdateSupplierCommand(
                new SupplierId(UUID.fromString(supplierId)),
                updateSupplierRequestDto.getName()
        );
    }
}
