package com.e.bambi.order.infrastructure.persistence.order.mapper;

import com.e.bambi.order.application.order.dto.response.orderwithdetails.*;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryAddress;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryItem;
import com.e.bambi.order.application.order.dto.response.ordersummary.OrderSummaryReadResponse;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.domain.order.entity.OrderItem;
import com.e.bambi.order.domain.order.entity.OrderStatusHistory;
import com.e.bambi.order.domain.order.valueobject.*;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderEntity;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderItemEntity;
import com.e.bambi.order.infrastructure.persistence.order.entity.OrderStatusHistoryEntity;
import com.e.bambi.shared.kernel.domain.valueobject.*;
import io.r2dbc.spi.Readable;
import org.jooq.Record;
import org.jooq.Result;
import org.postgresql.core.Tuple;
import org.springframework.stereotype.Component;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple3;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.e.bambi.order.domain.order.entity.Order.FAILURE_MESSAGE_DELIMITER;

@Component
public class OrderPersistenceMapper {

    public OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId().getValue())
                .userId(order.getUserId().getValue())
                .orderStatus(order.getOrderStatus().name())
                .paymentMethodId(order.getPaymentMethod().getId().getValue())
                .paymentMethod(order.getPaymentMethod().getName())
                .country(order.getAddress().getCountry())
                .address(order.getAddress().getAddress())
                .city(order.getAddress().getCity())
                .province(order.getAddress().getProvince())
                .postalCode(order.getAddress().getPostalCode())
                .phoneNumber(order.getAddress().getPhoneNumber())
                .totalPrice(order.getTotalPrice().getAmount())
                .failureMessages(order.getFailureMessages() != null ?
                        String.join(FAILURE_MESSAGE_DELIMITER, order.getFailureMessages()) : "")
                .createdAt(order.getCreatedAt())
                .build();
    }

    public List<OrderItemEntity> toOrderItemEntity(Order order) {
        return order.getItems().stream()
                .map(item ->
                        OrderItemEntity.builder()
                                .id(item.getId().getValue())
                                .orderId(item.getOrderId().getValue())
                                .imageUrl(item.getImageUrl())
                                .supplierId(item.getSupplier().getSupplierId().getValue())
                                .supplier(item.getSupplier().getName())
                                .productId(item.getProduct().getProductId().getValue())
                                .sku(item.getProduct().getSku())
                                .name(item.getProduct().getName())
                                .price(item.getPrice().getAmount())
                                .quantity(item.getQuantity())
                                .totalPrice(item.getTotalPrice().getAmount())
                                .build()
                ).toList();
    }

    public OrderStatusHistoryEntity toOrderStatusHistoryEntity(Order order) {
        OrderStatusHistory statusHistory = order.getStatusHistories().getFirst();

        return OrderStatusHistoryEntity.builder()
                .id(statusHistory.getId().getValue())
                .orderId(statusHistory.getOrderId().getValue())
                .orderStatus(statusHistory.getOrderStatus())
                .reason(statusHistory.getReason())
                .changedAt(statusHistory.getChangedAt())
                .build();
    }

    public Order toOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .orderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus()))
                .build();
    }

    public Order tupleToOrder(OrderEntity orderEntity, OrderStatusHistoryEntity orderStatusHistoryEntity,
                              @Nullable List<OrderItemEntity> orderItemEntities) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .userId(new UserId(orderEntity.getUserId()))
                .orderStatus(OrderStatus.valueOf(orderEntity.getOrderStatus()))
                .paymentMethod(new OrderPaymentMethod(
                        new PaymentMethodId(orderEntity.getPaymentMethodId()),
                        orderEntity.getPaymentMethod()))
                .address(new OrderAddress(
                        orderEntity.getCountry(),
                        orderEntity.getAddress(),
                        orderEntity.getCity(),
                        orderEntity.getProvince(),
                        orderEntity.getPostalCode(),
                        orderEntity.getPhoneNumber()
                ))
                .totalPrice(new Money(orderEntity.getTotalPrice()))
                .failureMessages(orderEntity.getFailureMessages().isEmpty() ? new ArrayList<>() :
                        new ArrayList<>(Arrays.asList(orderEntity.getFailureMessages()
                                .split(FAILURE_MESSAGE_DELIMITER))))
                .createdAt(orderEntity.getCreatedAt())
                .items(orderItemEntities != null ? orderItemEntityToOrderItem(orderItemEntities) : null)
                .statusHistories(new ArrayList<>(Arrays.asList(OrderStatusHistory.builder()
                        .id(new OrderStatusHistoryId(orderStatusHistoryEntity.getId()))
                        .orderId(new OrderId(orderStatusHistoryEntity.getOrderId()))
                        .orderStatus(orderStatusHistoryEntity.getOrderStatus())
                        .reason(orderStatusHistoryEntity.getReason())
                        .changedAt(orderStatusHistoryEntity.getChangedAt())
                        .build()))
                )
                .build();
    }

    public OrderItemEntity rowToOrderItemEntity(Readable row) {
        return OrderItemEntity.builder()
                .id(row.get("id", UUID.class))
                .orderId(row.get("order_id", UUID.class))
                .imageUrl(row.get("image_url", String.class))
                .supplierId(row.get("supplier_id", UUID.class))
                .supplier(row.get("supplier", String.class))
                .productId(row.get("product_id", UUID.class))
                .sku(row.get("sku", String.class))
                .name(row.get("name", String.class))
                .price(row.get("price", BigDecimal.class))
                .quantity(row.get("quantity", Integer.class))
                .totalPrice(row.get("total_price", BigDecimal.class))
                .build();
    }

    public OrderSummaryReadResponse toOrderSummaryReadResponse(Record r) {
        @SuppressWarnings("unchecked")
        Result<Record> items = r.get("order_items", Result.class);

        return new OrderSummaryReadResponse(
                r.get("id", UUID.class),
                r.get("order_status", String.class),
                new OrderSummaryAddress(
                        r.get("shipping_country", String.class),
                        r.get("shipping_address", String.class),
                        r.get("shipping_city", String.class),
                        r.get("shipping_province", String.class),
                        r.get("shipping_postal_code", String.class),
                        r.get("shipping_phone_number", String.class)
                ),
                items.map(item ->
                        new OrderSummaryItem(
                                item.get("image_url", String.class),
                                item.get("product_id", UUID.class),
                                item.get("name", String.class)
                        )
                ),
                r.get("total_price", BigDecimal.class),
                r.get("created_at", Instant.class)
        );
    }

    public OrderWithDetailReadResponse toOrderWithDetailsReadResponse(Record r) {
        @SuppressWarnings("unchecked")
        Result<Record> items = r.get("order_items", Result.class);

        return new OrderWithDetailReadResponse(
                r.get("order_status", String.class),
                new OrderWithDetailPaymentMethod(
                        r.get("payment_method_id", UUID.class),
                        r.get("payment_method", String.class)
                ),
                new OrderWithDetailAddress(
                        r.get("shipping_country", String.class),
                        r.get("shipping_address", String.class),
                        r.get("shipping_city", String.class),
                        r.get("shipping_province", String.class),
                        r.get("shipping_postal_code", String.class),
                        r.get("shipping_phone_number", String.class)
                ),
                items.map(item ->
                        new OrderWithDetailItem(
                                r.get("image_url", String.class),
                                new OrderWithDetailItemSupplier(
                                        r.get("supplier_id", UUID.class),
                                        r.get("supplier", String.class)
                                ),
                                new OrderWithDetailItemProduct(
                                        r.get("product_id", UUID.class),
                                        r.get("product_name", String.class)
                                ),
                                r.get("price", BigDecimal.class),
                                r.get("quantity", Integer.class),
                                r.get("item_total_price", BigDecimal.class)
                        )
                ),
                r.get("order_total_price", BigDecimal.class),
                r.get("created_at", Instant.class)
        );
    }

    private List<OrderItem> orderItemEntityToOrderItem(List<OrderItemEntity> items) {
        return items.stream()
                .map(item ->
                        OrderItem.builder()
                                .id(new OrderItemId(item.getId()))
                                .orderId(new OrderId(item.getOrderId()))
                                .imageUrl(item.getImageUrl())
                                .supplier(new OrderItemSupplier(
                                        new SupplierId(item.getSupplierId()),
                                        item.getSupplier()
                                ))
                                .product(new OrderItemProduct(
                                        new ProductId(item.getProductId()),
                                        item.getSku(),
                                        item.getName()
                                ))
                                .price(new Money(item.getPrice()))
                                .quantity(item.getQuantity())
                                .totalPrice(new Money(item.getTotalPrice()))
                                .build()
                ).toList();
    }
}
