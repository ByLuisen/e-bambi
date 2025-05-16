package com.commerce.inventory_service.service;

import com.commerce.inventory_service.domain.ProductStatus;
import com.commerce.inventory_service.dto.ProductStatusInputDTO;
import com.commerce.inventory_service.exception.ProductStatusNotFoundException;
import com.commerce.inventory_service.mapper.ProductStatusMapper;
import com.commerce.inventory_service.repository.ProductStatusCrudRepository;
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
public class ProductStatusService {

    private final ProductStatusMapper productStatusMapper;
    private final ProductStatusCrudRepository productStatusCrudRepository;

    public Flux<ProductStatus> findAllProductStatuses() {
        return productStatusCrudRepository.findAll().
                switchIfEmpty(Flux.error(new ProductStatusNotFoundException("No product status recorded.")));
    }

    public Mono<ResponseEntity<Object>> saveProductStatus(ProductStatusInputDTO productStatusInputDTO) {
        ProductStatus productStatus = productStatusMapper.productStatusInputDTOToProductStatus(productStatusInputDTO);

        return productStatusCrudRepository.save(productStatus)
                .map(savedProductStatus -> {
                    String location = "/v1/product-statuses/" + savedProductStatus.getId();

                    return ResponseEntity.created(URI.create(location)).build();
                })
                .onErrorResume(DuplicateKeyException.class, e -> {
                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                });
    }

    public Mono<ResponseEntity<Object>> updateProductStatus(UUID productStatusId, ProductStatusInputDTO productStatusInputDTO) {
        ProductStatus productStatus = productStatusMapper.productStatusInputDTOToProductStatus(productStatusInputDTO);

        return productStatusCrudRepository.findById(productStatusId)
                .flatMap(productStatusObtained -> {
                    productStatus.setId(productStatusId);
                    if (!productStatusObtained.equals(productStatus)) {
                        return productStatusCrudRepository.save(productStatus)
                                .thenReturn(ResponseEntity.noContent().build())
                                .onErrorResume(DuplicateKeyException.class, ex -> {
                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                                });
                    }
                    return Mono.just(ResponseEntity.noContent().build());
                })
                .switchIfEmpty(Mono.error(new ProductStatusNotFoundException("Product not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteProductStatusById(UUID productStatusId) {
        return productStatusCrudRepository.existsById(productStatusId)
                .flatMap(productStatusExists -> {
                    if (productStatusExists) {
                        return productStatusCrudRepository.deleteById(productStatusId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new ProductStatusNotFoundException("Product not found."));
                });
    }
}
