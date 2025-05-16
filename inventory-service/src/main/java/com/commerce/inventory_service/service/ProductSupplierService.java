package com.commerce.inventory_service.service;

import com.commerce.inventory_service.dto.ResponseProductSupplierOutputDTO;
import com.commerce.inventory_service.exception.SupplierProductNotFoundException;
import com.commerce.inventory_service.repository.ProductSupplierCrudRepository;
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
