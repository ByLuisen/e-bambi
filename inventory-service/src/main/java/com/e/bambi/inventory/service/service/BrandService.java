package com.e.bambi.inventory.service.service;

import com.e.bambi.inventory.service.model.Brand;
import com.e.bambi.inventory.service.dto.BrandInputDTO;
import com.e.bambi.inventory.service.exception.BrandNotFoundException;
import com.e.bambi.inventory.service.mapper.BrandMapper;
import com.e.bambi.inventory.service.repository.BrandCrudRepository;
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
public class BrandService {

    private final BrandMapper brandMapper;
    private final BrandCrudRepository brandCrudRepository;

    public Flux<Brand> findAllBrands() {
        return brandCrudRepository.findAll()
                .switchIfEmpty(Flux.error(new BrandNotFoundException("No brands recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveBrand(BrandInputDTO brandInputDTO) {
        Brand brand = brandMapper.brandInputDTOToBrand(brandInputDTO);

        return brandCrudRepository.save(brand)
                .map(savedBrand -> {
                    String location = "/v1/brands/" + savedBrand.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateBrand(UUID brandId, BrandInputDTO brandInputDTO) {
        Brand brand = brandMapper.brandInputDTOToBrand(brandInputDTO);

        return brandCrudRepository.findById(brandId)
                .flatMap(brandObtained -> {
                    brand.setId(brandId);
                    if (!brandObtained.equals(brand)) {
                        return brandCrudRepository.save(brand)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, e -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new BrandNotFoundException("Brand not found")));
    }

    public Mono<ResponseEntity<Object>> deleteBrandById(UUID brandId) {
        return brandCrudRepository.existsById(brandId)
                .flatMap(brandExists -> {
                    if (brandExists) {
                        return brandCrudRepository.deleteById(brandId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new BrandNotFoundException("Brand not found"));
                });
    }
}
