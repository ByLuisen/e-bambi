package com.e.bambi.order.infrastructure.persistence.order.mapper;

import com.e.bambi.order.application.dto.response.OrderAddressResponse;
import com.e.bambi.order.application.dto.response.OrderItemResponse;
import com.e.bambi.order.application.dto.response.OrderResponse;
import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.order.domain.entity.OrderItem;
import com.e.bambi.order.domain.entity.Product;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.order.domain.valueobject.OrderItemId;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

@Component
public class OrderPersistenceMapper {

    public OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .statusId()
                .paymentMethodId()
                .userId()
                .totalPrice()
                .country()
        private UUID id;
        @Column("status_id")
        private UUID statusId;
        @Column("payment_method_id")
        private UUID paymentMethodId;
        @Column("user_id")
        private UUID userId;
        @Column("total_price")
        private BigDecimal totalPrice;
        private String country;
        private String address;
        private String city;
        private String province;
        @Column("postal_code")
        private String postalCode;
        @Column("phone_number")
        private String phoneNumber;
        @Column("created_at")
        private LocalDateTime createdAt;
        @Column("updated_at")
        private LocalDateTime updatedAt;
                .build();
    }

    public OrderItem rowToOrderItem(Map<String, Object> row) {
        return OrderItem.builder()
                .orderItemId(new OrderItemId((UUID) row.get("id")))
                .orderId(new OrderId((UUID) row.get("order_id")))
                .product(new Product(
                                new ProductId((UUID) row.get("product_id")),
                                (String) row.get("sku"),
                                (String) row.get("name"),
                                new Money((BigDecimal) row.get("price"))
                        )
                )
                .soldBy((String) row.get("sold_by"))
                .quantity((Integer) row.get("quantity"))
                .totalPrice((Money) row.get("total_price"))
                .discount((Money) row.get("discount"))
                .createdAt((ZonedDateTime) row.get("created_at"))
                .build();
    }

    private Order recordToOrder(org.jooq.Record record) {
        @SuppressWarnings("unchecked")
        Result<Record> orderItems = record.get("order_items", Result.class);

        Order.builder()
                .id(new OrderId(record.get("order_id", UUID.class)))
                .
                .userId()
                .build();

        return new OrderResponse(
                record.get("order_id", UUID.class),
                record.get("user_id", UUID.class),
                record.get("status_id", UUID.class),
                record.get("status_name", String.class),
                new OrderAddressResponse(
                        record.get("shipping_address", String.class),
                        record.get("shipping_country", String.class),
                        record.get("shipping_city", String.class),
                        record.get("shipping_province", String.class),
                        record.get("shipping_postal_code", String.class),
                        record.get("shipping_phone_number", String.class)
                ),
                orderItems.map(item -> new OrderItemResponse(
                        item.get("order_id", UUID.class),
                        item.get("product_id", UUID.class),
                        item.get("product_sku", String.class),
                        item.get("product_name", String.class),
                        item.get("sold_by", String.class),
                        item.get("product_quantity", Integer.class),
                        item.get("product_price", BigDecimal.class),
                        item.get("total_price", BigDecimal.class),
                        item.get("discount", BigDecimal.class),
                        item.get("item_created_at", ZonedDateTime.class)
                )),
                record.get("payment_method_name", String.class),
                record.get("order_total_price", BigDecimal.class),
                record.get("order_created_at", ZonedDateTime.class),
                record.get("order_updated_at", ZonedDateTime.class)
        );
    }
}
