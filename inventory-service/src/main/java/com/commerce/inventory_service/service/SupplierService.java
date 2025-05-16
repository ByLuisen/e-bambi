package com.commerce.inventory_service.service;

import com.commerce.inventory_service.domain.Supplier;
import com.commerce.inventory_service.dto.SupplierInputDTO;
import com.commerce.inventory_service.exception.SupplierNotFoundException;
import com.commerce.inventory_service.mapper.SupplierMapper;
import com.commerce.inventory_service.repository.SupplierCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierMapper supplierMapper;
    private final SupplierCrudRepository supplierCrudRepository;

    public Flux<Supplier> findAllSuppliers() {
        return supplierCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new SupplierNotFoundException("No supplier recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveSupplier(SupplierInputDTO supplierInputDTO) {
        Supplier supplier = supplierMapper.supplierInputDTOToSupplier(supplierInputDTO);

        return supplierCrudRepository.save(supplier)
                .map(savedSupplier -> {
                    String location = "v1/suppliers/" + savedSupplier.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateSupplier(UUID supplierId, SupplierInputDTO supplierInputDTO) {
        Supplier supplier = supplierMapper.supplierInputDTOToSupplier(supplierInputDTO);

        return supplierCrudRepository.findById(supplierId)
                .flatMap(supplierObtained -> {
                    supplier.setId(supplierId);
                    if (!supplierObtained.equals(supplier)) {
                        return supplierCrudRepository.save(supplier)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new SupplierNotFoundException("Supplier not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteSupplierById(UUID supplierId) {
        return supplierCrudRepository.existsById(supplierId)
                .flatMap(supplierExists -> {
                    if (supplierExists) {
                        return supplierCrudRepository.deleteById(supplierId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new SupplierNotFoundException("Supplier not found."));
                });
    }
}
