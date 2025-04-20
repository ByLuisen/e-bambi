package com.e.bambi.inventory.service.controller;

import com.e.bambi.inventory.service.model.Brand;
import com.e.bambi.inventory.service.dto.BrandInputDTO;
import com.e.bambi.inventory.service.service.BrandService;
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
@RequestMapping("/v1/brands")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public Flux<Brand> findAllBrands() {
        return brandService.findAllBrands();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> saveBrand(@RequestBody @Valid BrandInputDTO brandInputDTO) {
        return brandService.saveBrand(brandInputDTO);
    }

    @PutMapping("/{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> updateBrand(@PathVariable @UUID String brandId, @RequestBody @Valid BrandInputDTO brandInputDTO) {
        return brandService.updateBrand(java.util.UUID.fromString(brandId), brandInputDTO);
    }

    @DeleteMapping("/{brandId}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<Object>> deleteBrandById(@PathVariable @UUID String brandId) {
        return brandService.deleteBrandById(java.util.UUID.fromString(brandId));
    }
}
