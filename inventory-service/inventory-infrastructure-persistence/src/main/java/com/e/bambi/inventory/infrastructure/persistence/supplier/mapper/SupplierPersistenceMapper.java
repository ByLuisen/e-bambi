package com.e.bambi.inventory.infrastructure.persistence.supplier.mapper;

import com.e.bambi.inventory.application.supplier.dto.response.SupplierResponse;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.domain.supplier.entity.Supplier;
import com.e.bambi.inventory.infrastructure.persistence.supplier.entity.SupplierEntity;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SupplierPersistenceMapper {

    public SupplierEntity toSupplierEntity(Supplier supplier) {
        return new SupplierEntity(
                supplier.getId().getValue(),
                supplier.getName()
        );
    }

    public Supplier toSupplier(SupplierEntity entity) {
        return Supplier.builder()
                .id(new SupplierId(entity.getId()))
                .name(entity.getName())
                .build();
    }

    public SupplierResponse toSupplierResponse(Record r) {
        return new SupplierResponse(
                r.get("supplier_id", UUID.class),
                r.get("supplier_name", String.class)
        );
    }
}
