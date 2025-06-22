package com.e.bambi.inventory.infrastructure.persistence.supplier.repository.r2dbc;

import com.e.bambi.inventory.infrastructure.persistence.supplier.entity.SupplierEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface SupplierR2dbcRepository extends R2dbcRepository<SupplierEntity, UUID> {

    @Modifying
    @Query("DELETE FROM suppliers WHERE id = :supplierId")
    Mono<Integer> deleteSupplierById(UUID supplierId);
}
