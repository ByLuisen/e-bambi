package com.e.bambi.inventory.application;

import com.e.bambi.inventory.domain.product.entity.Product;
import reactor.core.publisher.Mono;

public interface InventoryApplicationService {

    Mono<Void> ensureProductIsValid(Product product);
}
