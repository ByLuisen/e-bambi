package com.e.bambi.order.application.order.mapper;

import com.e.bambi.order.application.order.dto.command.createorder.CreateOrderAddressCommand;
import com.e.bambi.order.application.order.dto.command.createorder.CreateOrderCommand;
import com.e.bambi.order.application.order.dto.command.createorder.CreateOrderItemCommand;
import com.e.bambi.order.application.order.dto.response.CreateOrderResponse;
import com.e.bambi.order.domain.order.entity.Order;
import com.e.bambi.order.domain.order.entity.OrderItem;
import com.e.bambi.order.domain.order.valueobject.OrderAddress;
import com.e.bambi.order.domain.order.valueobject.OrderItemProduct;
import com.e.bambi.order.domain.order.valueobject.OrderItemSupplier;
import com.e.bambi.order.domain.order.valueobject.OrderPaymentMethod;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderApplicationMapper {

    public Order createOrderCommandToOrderDomain(CreateOrderCommand command) {
        CreateOrderAddressCommand address = command.getAddress();

        return Order.builder()
                .userId(command.getUserId())
                .paymentMethod(new OrderPaymentMethod(
                        command.getPaymentMethod().getId(),
                        command.getPaymentMethod().getName()
                ))
                .address(new OrderAddress(
                        address.getCountry(),
                        address.getAddress(),
                        address.getCity(),
                        address.getProvince(),
                        address.getPostalCode(),
                        address.getPhoneNumber()
                ))
                .items(createOrderItemCommandToOrderItem(command.getItems()))
                .totalPrice(new Money(command.getTotalPrice().getAmount()))
                .build();
    }

    public CreateOrderResponse toCreateOrderResponse(Order order, String message) {
        return new CreateOrderResponse(
                order.getId().getValue(),
                order.getOrderStatus().name(),
                message
        );
    }

    public List<OrderItem> createOrderItemCommandToOrderItem(List<CreateOrderItemCommand> commands) {
        return commands
                .stream().map(command ->
                        OrderItem.builder()
                                .imageUrl(command.getImageUrl())
                                .supplier(OrderItemSupplier.builder()
                                        .supplierId(command.getSupplier().getId())
                                        .name(command.getSupplier().getName())
                                        .build()
                                )
                                .product(OrderItemProduct.builder()
                                        .productId(command.getProduct().getId())
                                        .sku(command.getProduct().getSku())
                                        .name(command.getProduct().getName())
                                        .build()
                                )
                                .price(command.getPrice())
                                .quantity(command.getQuantity())
                                .totalPrice(command.getTotalPrice())
                                .build())
                .toList();
    }
}
