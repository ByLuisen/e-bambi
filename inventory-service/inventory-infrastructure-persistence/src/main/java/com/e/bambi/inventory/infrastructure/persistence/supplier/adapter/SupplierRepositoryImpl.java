package com.e.bambi.inventory.infrastructure.persistence.supplier.adapter;

import com.e.bambi.inventory.application.supplier.port.outbound.repository.SupplierRepository;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.domain.supplier.entity.Supplier;
import com.e.bambi.inventory.infrastructure.persistence.supplier.mapper.SupplierPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.supplier.repository.r2dbc.SupplierR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierR2dbcRepository supplierR2dbcRepository;
    private final SupplierPersistenceMapper supplierPersistenceMapper;

    @Override
    public Mono<Supplier> update(Supplier supplier) {
        return supplierR2dbcRepository.save(
                supplierPersistenceMapper.toSupplierEntity(supplier)
        ).map(supplierPersistenceMapper::toSupplier);
    }

    @Override
    public Mono<Integer> deleteById(SupplierId supplierId) {
        return supplierR2dbcRepository
                .deleteSupplierById(supplierId.getValue());
    }
}
