package com.e.bambi.inventory.infrastructure.persistence.product.adapter;

import com.e.bambi.inventory.application.product.port.outbond.repository.ProductRepository;
import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.inventory.infrastructure.persistence.product.mapper.ProductPersistenceMapper;
import com.e.bambi.inventory.infrastructure.persistence.product.repository.r2dbc.ProductR2dbcEntityTemplate;
import com.e.bambi.inventory.infrastructure.persistence.product.repository.r2dbc.ProductR2dbcRepository;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductR2dbcEntityTemplate productR2dbcEntityTemplate;
    private final ProductR2dbcRepository productR2dbcRepository;
    private final ProductPersistenceMapper productPersistenceMapper;

    @Override
    public Mono<Product> insert(Product product) {
        return productR2dbcEntityTemplate
                .insert(productPersistenceMapper.toProductEntity(product))
                .map(productPersistenceMapper::toProduct);
    }

    @Override
    public Mono<Product> updated(Product product) {
        return productR2dbcRepository
                .save(productPersistenceMapper.toProductEntity(product))
                .map(productPersistenceMapper::toProduct);
    }

    @Override
    public Mono<Integer> deleteById(ProductId productId) {
        return productR2dbcRepository
                .deleteProductById(productId.getValue());
    }
}
