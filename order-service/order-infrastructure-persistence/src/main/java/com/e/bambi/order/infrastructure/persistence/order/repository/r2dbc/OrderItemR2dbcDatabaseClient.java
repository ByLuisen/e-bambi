package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.order.infrastructure.persistence.order.entity.OrderItemEntity;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderItemR2dbcDatabaseClient {

    private final DatabaseClient databaseClient;
    private final OrderPersistenceMapper orderDataAccessMapperManual;

    public Flux<OrderItemEntity> saveAll(List<OrderItemEntity> items) {
        if (items.isEmpty()) {
            return Flux.empty();
        }

        int n = items.size();
        UUID[] ids = new UUID[n];
        UUID[] orderIds = new UUID[n];
        String[] imageUrls = new String[n];
        UUID[] supplierIds = new UUID[n];
        String[] suppliers = new String[n];
        UUID[] productIds = new UUID[n];
        String[] skus = new String[n];
        String[] names = new String[n];
        BigDecimal[] prices = new BigDecimal[n];
        Integer[] quantities = new Integer[n];
        BigDecimal[] totalPrices = new BigDecimal[n];

        for (int i = 0; i < n; i++) {
            var item = items.get(i);
            ids[i] = item.getId();
            orderIds[i] = item.getOrderId();
            imageUrls[i] = item.getImageUrl();
            supplierIds[i] = item.getSupplierId();
            suppliers[i] = item.getSupplier();
            productIds[i] = item.getProductId();
            skus[i] = item.getSku();
            names[i] = item.getName();
            prices[i] = item.getPrice();
            quantities[i] = item.getQuantity();
            totalPrices[i] = item.getTotalPrice();
        }

        String sql = """
                INSERT INTO order_items (
                    id, order_id, image_url, supplier_id, supplier,
                    product_id, sku, name, price, quantity, total_price
                )
                SELECT * FROM UNNEST(
                    $1::uuid[], $2::uuid[], $3::text[], $4::uuid[], $5::text[],
                    $6::uuid[],  $7::text[], $8::text[], $9::numeric[], $10::int[], $11::numeric[]
                )
                RETURNING *
                """;

        return databaseClient.sql(sql)
                .bind(0, ids)
                .bind(1, orderIds)
                .bind(2, imageUrls)
                .bind(3, supplierIds)
                .bind(4, suppliers)
                .bind(5, productIds)
                .bind(6, skus)
                .bind(7, names)
                .bind(8, prices)
                .bind(9, quantities)
                .bind(10, totalPrices)
                .map(orderDataAccessMapperManual::rowToOrderItemEntity)
                .all();
    }
}