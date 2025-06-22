package com.e.bambi.inventory.application.supplier.port.outbound.repository;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.inventory.domain.supplier.entity.Supplier;
import reactor.core.publisher.Mono;

public interface SupplierRepository {
    Mono<Supplier> update(Supplier supplier);

    Mono<Integer> deleteById(SupplierId supplierId);
}