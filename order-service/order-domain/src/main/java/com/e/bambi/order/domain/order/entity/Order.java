package com.e.bambi.order.domain.order.entity;

import com.e.bambi.order.domain.exception.OrderDomainException;
import com.e.bambi.order.domain.order.valueobject.*;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.OrderId;
import com.e.bambi.shared.kernel.domain.valueobject.UserId;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class Order extends AggregateRoot<OrderId> {
    private final UserId userId;
    private OrderStatus orderStatus;
    private final OrderPaymentMethod paymentMethod;
    private final OrderAddress address;
    private final Money totalPrice;
    private List<String> failureMessages;
    private OffsetDateTime createdAt;
    private final List<OrderItem> items;
    private List<OrderStatusHistory> statusHistories;

    public void initializeOrder() {
        super.setId(new OrderId(UUID.randomUUID()));
        orderStatus = OrderStatus.PENDING;
        createdAt = OffsetDateTime.now();
        initializeOrderItems();
        addStatusHistory();
    }

    public void validateOrder() {
        validateInitialOrder();
        validateTotalPrice();
        validateItemsPrice();
    }

    public void productsReserved() {
        if (orderStatus != OrderStatus.PENDING) {
            throw new OrderDomainException("Order is not in the correct state for products reserved operation!");
        }
        orderStatus = OrderStatus.PRODUCTS_RESERVED;
        addStatusHistory();
    }

    public void created() {
        if (orderStatus != OrderStatus.PRODUCTS_RESERVED) {
            throw new OrderDomainException("Order is not in the correct state for payment validated operation!");
        }
        this.orderStatus = OrderStatus.CREATED;
        addStatusHistory();
    }

    public void initCancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.PRODUCTS_RESERVED)) {
            throw new OrderDomainException("Order is not in the correct state for init cancel operation!");
        }
        orderStatus = OrderStatus.CANCELLING;
        updateFailureMessages(failureMessages);
        addStatusHistory();
    }

    public void cancel(List<String> failureMessages) {
        if (!(orderStatus == OrderStatus.PENDING || orderStatus == OrderStatus.CANCELLING)) {
            throw new OrderDomainException("Order is not in the correct state for cancel operation");
        }
        orderStatus = OrderStatus.CANCELLED;
        updateFailureMessages(failureMessages);
        addStatusHistory();
    }

    private void updateFailureMessages(List<String> failureMessages) {
        if (this.failureMessages != null && failureMessages != null) {
            this.failureMessages.addAll(failureMessages.stream().filter(messages -> !messages.isBlank()).toList());
        }

        if (this.failureMessages == null) {
            this.failureMessages = failureMessages;
        }
    }

    private void validateInitialOrder() {
        if (orderStatus != null || super.getId() != null || statusHistories != null) {
            throw new OrderDomainException("Order is not in the correct state for initialization!");
        }
    }

    private void validateTotalPrice() {
        if (totalPrice == null || !totalPrice.isGreaterThanZero()) {
            throw new OrderDomainException("Total price must be greater than zero!");
        }
    }

    private void validateItemsPrice() {
        Money orderItemsTotal = items.stream()
                .map(item -> {
                    validateItemPrice(item);
                    return item.getTotalPrice();
                }).reduce(Money.ZERO, Money::add);

        if (!totalPrice.equals(orderItemsTotal)) {
            throw new OrderDomainException("Total price: " + totalPrice.getAmount() +
                    " is not equal to Order items total: " + orderItemsTotal);
        }
    }

    private void validateItemPrice(OrderItem orderItem) {
        if (!orderItem.isPriceValid()) {
            throw new OrderDomainException("Order item price: " + orderItem.getPrice().getAmount() +
                    " is not valid for product: " + orderItem.getProduct().getProductId().getValue());
        }
    }

    private void addStatusHistory() {
        OrderStatusHistory orderStatusHistory =
                OrderStatusHistory.builder()
                        .id(new OrderStatusHistoryId(UUID.randomUUID()))
                        .orderId(super.getId())
                        .orderStatus(orderStatus)
                        .reason(orderStatus.getReason())
                        .createdAt(OffsetDateTime.now())
                        .build();

        if (statusHistories != null) {
            statusHistories.add(orderStatusHistory);
        }
        if (statusHistories == null) {
            statusHistories = new ArrayList<>(List.of(orderStatusHistory));
        }
    }

    private void initializeOrderItems() {
        for (OrderItem item : items) {
            item.initializeOrderItem(new OrderItemId(UUID.randomUUID()), super.getId());
        }
    }

    private Order(Builder builder) {
        super.setId(builder.id);
        userId = builder.userId;
        orderStatus = builder.orderStatus;
        paymentMethod = builder.paymentMethod;
        address = builder.address;
        totalPrice = builder.totalPrice;
        failureMessages = builder.failureMessages;
        createdAt = builder.createdAt;
        items = builder.items;
        statusHistories = builder.statusHistories;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private OrderId id;
        private UserId userId;
        private OrderStatus orderStatus;
        private OrderPaymentMethod paymentMethod;
        private OrderAddress address;
        private Money totalPrice;
        private List<String> failureMessages;
        private OffsetDateTime createdAt;
        private List<OrderItem> items;
        private List<OrderStatusHistory> statusHistories;

        private Builder() {
        }

        public Builder id(OrderId val) {
            id = val;
            return this;
        }

        public Builder userId(UserId val) {
            userId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder paymentMethod(OrderPaymentMethod val) {
            paymentMethod = val;
            return this;
        }

        public Builder address(OrderAddress val) {
            address = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder failureMessages(List<String> val) {
            failureMessages = val;
            return this;
        }

        public Builder createdAt(OffsetDateTime val) {
            createdAt = val;
            return this;
        }

        public Builder items(List<OrderItem> val) {
            items = val;
            return this;
        }

        public Builder statusHistories(List<OrderStatusHistory> val) {
            statusHistories = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}
