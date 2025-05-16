package com.e.bambi.order.infrastructure.persistence.order.repository.r2dbc;

import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.infrastructure.persistence.order.mapper.OrderPersistenceMapper;
import com.e.bambi.order.domain.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderItemR2dbcClientRepository {

    private final DatabaseClient databaseClient;
    private final OrderPersistenceMapper orderDataAccessMapperManual;

    public Flux<OrderItem> saveAll(List<OrderItem> items, OrderId orderId) {
        if (items.isEmpty()) {
            return Flux.empty();
        }

        int n = items.size();
        UUID[] orderIds = new UUID[n];
        UUID[] productIds = new UUID[n];
        String[] skus = new String[n];
        String[] names = new String[n];
        String[] soldBys = new String[n];
        Integer[] quantities = new Integer[n];
        BigDecimal[] prices = new BigDecimal[n];
        BigDecimal[] totalPrices = new BigDecimal[n];
        BigDecimal[] discounts = new BigDecimal[n];

        for (int i = 0; i < n; i++) {
            var item = items.get(i);
            orderIds[i] = orderId.getValue();
            productIds[i] = item.getProduct().getId().getValue();
            skus[i] = item.getProduct().getSku();
            names[i] = item.getProduct().getName();
            soldBys[i] = item.getSoldBy();
            quantities[i] = item.getQuantity();
            prices[i] = item.getProduct().getPrice().getAmount();
            totalPrices[i] = item.getTotalPrice().getAmount();
            discounts[i] = item.getDiscount().getAmount();
        }

        String sql = """
                INSERT INTO order_items (
                    order_id, product_id, sku, name, sold_by,
                    quantity, price, total_price, discount
                )
                SELECT * FROM UNNEST(
                    $1::uuid[], $2::uuid[], $3::text[], $4::text[], $5::text[],
                    $6::int[],  $7::numeric[], $8::numeric[], $9::numeric[]
                )
                RETURNING *
                """;

        return databaseClient.sql(sql)
                .bind(0, orderIds)
                .bind(1, productIds)
                .bind(2, skus)
                .bind(3, names)
                .bind(4, soldBys)
                .bind(5, quantities)
                .bind(6, prices)
                .bind(7, totalPrices)
                .bind(8, discounts)
                .fetch()
                .all()
                .map(orderDataAccessMapperManual::rowToOrderItem);
    }
}