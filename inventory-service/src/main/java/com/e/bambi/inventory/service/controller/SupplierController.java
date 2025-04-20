package com.e.bambi.inventory.service.controller;

import com.e.bambi.inventory.service.model.Supplier;
import com.e.bambi.inventory.service.dto.ResponseProductSupplierOutputDTO;
import com.e.bambi.inventory.service.dto.SupplierInputDTO;
import com.e.bambi.inventory.service.service.ProductSupplierService;
import com.e.bambi.inventory.service.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/suppliers")
public class SupplierController {

    private final SupplierService supplierService;
    private final ProductSupplierService productSupplierService;

    @GetMapping
    public Flux<Supplier> findAllSuppliers() {
        return supplierService.findAllSuppliers();
    }

    @GetMapping("/{supplierId}/products")
    public Flux<ResponseProductSupplierOutputDTO> findProductsBySupplierId(@PathVariable @UUID String supplierId) {
        return productSupplierService.findProductsBySupplierId(java.util.UUID.fromString(supplierId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveSupplier(@RequestBody @Valid SupplierInputDTO supplierInputDTO) {
        return supplierService.saveSupplier(supplierInputDTO);
    }

    @PutMapping("/{supplierId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> updateSupplier(@PathVariable @UUID String supplierId,
                                                       @RequestBody @Valid SupplierInputDTO supplierInputDTO) {
        return supplierService.updateSupplier(java.util.UUID.fromString(supplierId), supplierInputDTO);
    }

    @DeleteMapping("/{supplierId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteSupplierById(@PathVariable @UUID String supplierId) {
        return supplierService.deleteSupplierById(java.util.UUID.fromString(supplierId));
    }
}



