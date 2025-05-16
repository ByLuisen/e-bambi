package com.commerce.inventory_service.service;

import com.commerce.inventory_service.domain.Product;
import com.commerce.inventory_service.domain.ProductStatusEnum;
import com.commerce.inventory_service.dto.ProductFilterInputDTO;
import com.commerce.inventory_service.dto.ProductInputDTO;
import com.commerce.inventory_service.dto.ProductOutputDTO;
import com.commerce.inventory_service.dto.UpdateStatusDTO;
import com.commerce.inventory_service.exception.ProductBadRequestException;
import com.commerce.inventory_service.exception.ProductNotFoundException;
import com.commerce.inventory_service.exception.ProductStatusBadRequestException;
import com.commerce.inventory_service.exception.ProductStatusNotFoundException;
import com.commerce.inventory_service.mapper.ProductMapper;
import com.commerce.inventory_service.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple3;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final BrandCrudRepository brandCrudRepository;
    private final DepartmentCrudRepository departmentCrudRepository;
    private final ProductStatusCrudRepository productStatusCrudRepository;
    private final ProductCrudRepository productCrudRepository;
    private final ProductRepositoryCustom productRepositoryCustom;
    private final ReactiveRedisTemplate<String, Object> redisTemplate;

    public Mono<Object> findProductsWithPaginationAndFilters(ProductFilterInputDTO filters) {
        String redisKey = filters.redisKey();

        return redisTemplate.opsForValue().get(redisKey)
                .switchIfEmpty(
                        productRepositoryCustom.findProductsWithPaginationAndFilters(filters)
                                .flatMap(result -> redisTemplate.opsForValue().set(redisKey, result, Duration.ofMinutes(10))
                                        .thenReturn(result)
                                )
                );
    }

    public Mono<ResponseEntity<ProductOutputDTO>> findProductById(UUID productId) {
        return productRepositoryCustom.findProductById(productId)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found.")));
    }

    public Mono<ResponseEntity<Object>> saveProduct(ProductInputDTO productInputDTO) {
        Product product = productMapper.productInputDTOToProduct(productInputDTO);

        return Mono.zip(
                brandCrudRepository.existsById(product.getBrandId()),
                departmentCrudRepository.existsById(product.getDepartmentId()),
                productStatusCrudRepository.existsById(product.getProductStatusId())
        ).flatMap(results -> {
            List<String> errors = getErrors(results);

            if (errors.isEmpty()) {
                return productCrudRepository.save(product)
                        .map(savedProduct -> {
                            String location = "/v1/products/" + savedProduct.getId();

                            return ResponseEntity.created(URI.create(location)).build();
                        })
                        .onErrorResume(DuplicateKeyException.class, e -> {
                            return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique."));
                        });
            }
            return Mono.error(new ProductBadRequestException("Invalid data.", errors.toArray()));
        });
    }

    public Mono<ResponseEntity<Object>> updateProductById(UUID productId, ProductInputDTO productInputDTO) {
        Product product = productMapper.productInputDTOToProduct(productInputDTO);

        return productCrudRepository.findById(productId)
                .flatMap(productObtained -> {
                    product.setId(productId);
                    if (!productObtained.equals(product)) {
                        return validateIDs(productObtained, product)
                                .flatMap(errors -> {
                                    if (errors.isEmpty()) {
                                        System.out.println(product);
                                        return productCrudRepository.save(product)
                                                .thenReturn(ResponseEntity.noContent().build())
                                                .onErrorResume(DuplicateKeyException.class, e -> {
                                                    return Mono.error(new DuplicateKeyException("Duplicate entry detected. Please ensure the data is unique." + e.getMessage()));
                                                });
                                    }
                                    return Mono.error(new ProductBadRequestException("Invalid data.", errors.toArray()));
                                });
                    }
                    return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
                })
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found.")));
    }

    private static List<String> getErrors(Tuple3<Boolean, Boolean, Boolean> results) {
        boolean brandExists = results.getT1();
        boolean departmentExists = results.getT2();
        boolean productStatusExists = results.getT3();

        List<String> errors = new ArrayList<>();

        if (!brandExists) {
            errors.add("The selected brand is not valid.");
        }
        if (!departmentExists) {
            errors.add("The selected department is not valid.");
        }
        if (!productStatusExists) {
            errors.add("The selected product status is not valid.");
        }

        return errors;
    }

    private Mono<List<String>> validateIDs(Product productObtained, Product product) {
        List<String> errors = new ArrayList<>();

        Mono<Void> brandValidation = Mono.empty();
        if (!productObtained.getBrandId().equals(product.getBrandId())) {
            brandValidation = brandCrudRepository.existsById(product.getBrandId())
                    .map(brandExists -> {
                        if (!brandExists) {
                            errors.add("Brand with ID " + product.getBrandId() + " does not exist.");
                        }
                        return null;
                    });
        }

        Mono<Void> departmentValidation = Mono.empty();
        if (!productObtained.getDepartmentId().equals(product.getDepartmentId())) {
            departmentValidation = departmentCrudRepository.existsById(product.getDepartmentId())
                    .map(departmentExists -> {
                        if (!departmentExists) {
                            errors.add("Department with ID " + product.getDepartmentId() + " does not exist.");
                        }
                        return null;
                    });
        }

        Mono<Void> statusValidation = Mono.empty();
        if (!productObtained.getProductStatusId().equals(product.getProductStatusId())) {
            statusValidation = productStatusCrudRepository.existsById(product.getProductStatusId())
                    .map(statusExists -> {
                        if (!statusExists) {
                            errors.add("Product Status with ID " + product.getProductStatusId() + " does not exist.");
                        }
                        return null;
                    });
        }

        return Mono.zip(brandValidation, departmentValidation, statusValidation)
                .then(Mono.just(errors));
    }

    @Transactional
    public Mono<ResponseEntity<Object>> updateStatusByProductId(UUID productId, UpdateStatusDTO updateStatusDTO) {
        return productCrudRepository.findById(productId)
                .flatMap(productObtained -> {
                    UUID inactiveUUID = ProductStatusEnum.fromString("INACTIVE");
                    UUID newStatus = updateStatusDTO.getStatusId();

                    if (!productObtained.getProductStatusId().equals(newStatus)) {
                        return productStatusCrudRepository.existsById(newStatus)
                                .flatMap(statusExists -> {
                                    if (statusExists) {
                                        productObtained.setProductStatusId(newStatus);

                                        if (inactiveUUID.equals(newStatus)) {
                                            productObtained.setDeletedAt(LocalDateTime.now());
                                        }

                                        return productCrudRepository.save(productObtained)
                                                .thenReturn(ResponseEntity.noContent().build());
                                    }
                                    return Mono.error(new ProductStatusNotFoundException("Status not found for the given id."));
                                });
                    }
                    return Mono.error(new ProductStatusBadRequestException("The product already have the same status."));
                })
                .switchIfEmpty(Mono.error(new ProductNotFoundException("Product not found.")));
    }

    public Mono<ResponseEntity<Object>> deleteProductById(UUID productId) {
        return productCrudRepository.existsById(productId)
                .flatMap(productExists -> {
                    if (productExists) {
                        return productCrudRepository.deleteById(productId)
                                .thenReturn(ResponseEntity.noContent().build());
                    }
                    return Mono.error(new ProductNotFoundException("Product not found."));
                });
    }
}
