package com.commerce.inventory_service.controller;

import com.commerce.inventory_service.domain.ProductStatus;
import com.commerce.inventory_service.dto.ProductStatusInputDTO;
import com.commerce.inventory_service.service.ProductStatusService;
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
@RequestMapping("/v1/product-statuses")
public class ProductStatusController {

    private final ProductStatusService productStatusService;

    @GetMapping
    public Flux<ProductStatus> findAllProductStatuses() {
        return productStatusService.findAllProductStatuses();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveProductStatus(@RequestBody @Valid ProductStatusInputDTO productStatusInputDTO) {
        return productStatusService.saveProductStatus(productStatusInputDTO);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> updateProductStatus(@PathVariable @UUID String productStatusId,
                                                            @RequestBody @Valid ProductStatusInputDTO productStatusInputDTO) {
        return productStatusService.updateProductStatus(java.util.UUID.fromString(productStatusId), productStatusInputDTO);
    }

    @DeleteMapping("/{productStatusId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteProductStatusById(@PathVariable @UUID String productStatusId) {
        return productStatusService.deleteProductStatusById(java.util.UUID.fromString(productStatusId));
    }
}
