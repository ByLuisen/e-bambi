package com.e.bambi.order.application.mapper;

import com.e.bambi.order.application.dto.command.CreateOrderAddressCommand;
import com.e.bambi.order.application.dto.command.CreateOrderCommand;
import com.e.bambi.order.application.dto.command.CreateOrderItemCommand;
import com.e.bambi.order.application.dto.response.*;
import com.e.bambi.order.domain.entity.Order;
import com.e.bambi.order.domain.entity.OrderItem;
import com.e.bambi.order.domain.entity.OrderStatusHistory;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.order.domain.valueobject.OrderAddress;
import com.e.bambi.order.domain.valueobject.PaymentMethod;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderApplicationMapper {

    public Order createOrderCommandToOrderDomain(CreateOrderCommand createOrderCommand) {
        CreateOrderAddressCommand address = createOrderCommand.getAddress();

        return Order.builder()
                .paymentMethod(new PaymentMethod(createOrderCommand.getPaymentMethodId())
                )
                .address(OrderAddress.builder()
                        .country(address.getCountry())
                        .address(address.getAddress())
                        .city(address.getCity())
                        .province(address.getProvince())
                        .postalCode(address.getPostalCode())
                        .phoneNumber(address.getPhoneNumber())
                        .build()
                )
                .items(createOrderItemCommandToOrderItem(createOrderCommand.getItems()))
                .totalPrice(new Money(createOrderCommand.getTotalPrice().getAmount()))
                .build();
    }

    public OrderResponse toOrderResponse(Order order) {
        OrderAddress address = order.getAddress();

        return new OrderResponse(
                order.getId().getValue(),
                order.getUserId().getValue(),
                order.getOrderStatus().getId().getValue(),
                order.getOrderStatus().getName(),
                new OrderAddressResponse(
                        address.getAddress(),
                        address.getCountry(),
                        address.getCity(),
                        address.getProvince(),
                        address.getPostalCode(),
                        address.getPhoneNumber()
                ),
                orderItemToOrderItemResponse(order.getItems()),
                order.getPaymentMethod().getName(),
                order.getTotalPrice().getAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }

    public CreateOrderResponse toCreateOrderResponse(Order order, String message) {
        return new CreateOrderResponse(
                order.getTrackingId().getValue(),
                order.getOrderStatus().getName(),
                message
        );
    }

    public PaginatedResultResponse<OrderResponse> toPaginatedResultResponse(List<Order> orders) {
        return new PaginatedResultResponse<>(
                orders.stream()
                        .map(this::toOrderResponse)
                        .toList(),
                orders.size());
    }

    public OrderStatusHistoryResponse toOrderStatusHistoryResponse(OrderStatusHistory orderStatusHistory) {
        return new OrderStatusHistoryResponse(
                orderStatusHistory.getOrderStatus().getName(),
                orderStatusHistory.getReason(),
                orderStatusHistory.getChangedAt()
        );
    }

    public List<OrderItem> createOrderItemCommandToOrderItem(List<CreateOrderItemCommand> createOrderItemCommands) {
        return createOrderItemCommands
                .stream().map(createOrderItemCommand ->
                        OrderItem.builder()
                                .product(createOrderItemCommand.getProduct())
                                .soldBy(createOrderItemCommand.getSoldBy())
                                .quantity(createOrderItemCommand.getQuantity())
                                .totalPrice(createOrderItemCommand.getProduct().getPrice())
                                .totalPrice(createOrderItemCommand.getTotalPrice())
                                .discount(createOrderItemCommand.getDiscount())
                                .build())
                .toList();
    }

    private List<OrderItemResponse> orderItemToOrderItemResponse(List<OrderItem> items) {
        return items.stream()
                .map(item -> new OrderItemResponse(
                        item.getOrderId().getValue(),
                        item.getProduct().getId().getValue(),
                        item.getProduct().getSku(),
                        item.getProduct().getName(),
                        item.getSoldBy(),
                        item.getQuantity(),
                        item.getTotalPrice().getAmount(),
                        item.getTotalPrice().getAmount(),
                        item.getDiscount().getAmount(),
                        item.getCreatedAt()
                ))
                .toList();
    }
}
