package com.e.bambi.inventory.service.repository;

import com.e.bambi.inventory.service.model.ProductSupplier;
import com.e.bambi.inventory.service.dto.ResponseProductSupplierOutputDTO;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ProductSupplierCrudRepository extends ReactiveCrudRepository<ProductSupplier, UUID> {
    @Query("SELECT p.id, p.brand_id, p.department_id, p.product_status_id, p.sku, p.name, p.price AS original_price, p.description, p.created_at, p.updated_at, " +
            "ps.price AS supplier_price, ps.stock AS supplier_stock " +
            "FROM products p " +
            "JOIN product_supplier ps ON p.id = ps.product_id " +
            "WHERE ps.supplier_id = :supplierId;")
    Flux<ResponseProductSupplierOutputDTO> findProductsBySupplierId(UUID supplierId);

    @Query("SELECT stock FROM product_supplier WHERE product_id = :productId AND supplier_id = :supplierId")
    Mono<Integer> getStockByProductIdAndSupplierId(UUID productId, UUID supplierId);
}
