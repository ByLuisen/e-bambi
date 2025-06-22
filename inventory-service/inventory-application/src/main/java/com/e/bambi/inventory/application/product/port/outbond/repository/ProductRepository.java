package com.e.bambi.inventory.application.product.port.outbond.repository;

import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> insert(Product product);

    Mono<Product> updated(Product product);

    Mono<Integer> deleteById(ProductId productId);

}
