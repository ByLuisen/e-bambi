package com.commerce.inventory_service.controller;

import com.commerce.inventory_service.dto.*;
import com.commerce.inventory_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Mono<Object> findProductsWithPaginationAndFilters(@Valid ProductFilterInputDTO filters) {
        return productService.findProductsWithPaginationAndFilters(filters);
    }

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<ProductOutputDTO>> findProductById(@PathVariable @UUID String productId) {
        return productService.findProductById(java.util.UUID.fromString(productId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveProduct(@RequestBody @Valid ProductInputDTO productInputDTO) {
        return productService.saveProduct(productInputDTO);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> updateProductById(@PathVariable @UUID String productId,
                                                          @RequestBody @Valid ProductInputDTO productInputDTO) {
        return productService.updateProductById(java.util.UUID.fromString(productId), productInputDTO);
    }

    @PatchMapping("/{productId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> softDeleteProductById(@PathVariable @UUID String productId, @RequestBody @Valid UpdateStatusDTO updateStatusDTO) {
        return productService.updateStatusByProductId(java.util.UUID.fromString(productId), updateStatusDTO);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteProductById(@PathVariable @UUID String productId) {
        return productService.deleteProductById(java.util.UUID.fromString(productId));
    }
}

