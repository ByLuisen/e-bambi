package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.dto.ResponseProductSupplierOutputDTO;
import com.e.bambi.inventory.service.exception.SupplierProductNotFoundException;
import com.e.bambi.inventory.service.repository.ProductSupplierCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSupplierService {

    private final ProductSupplierCrudRepository productSupplierCrudRepository;

    public Flux<ResponseProductSupplierOutputDTO> findProductsBySupplierId(UUID supplierId) {
        return productSupplierCrudRepository.findProductsBySupplierId(supplierId)
                .switchIfEmpty(Flux.error(new SupplierProductNotFoundException("Products not found for the given supplier id.")));
    }
}
