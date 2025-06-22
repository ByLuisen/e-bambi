package com.e.bambi.order.infrastructure.rest.order.mapper;

import com.e.bambi.order.application.order.dto.command.createorder.*;
import com.e.bambi.order.application.order.dto.query.OrderQuery;
import com.e.bambi.order.infrastructure.rest.order.dto.request.OrderRequestDto;
import com.e.bambi.order.infrastructure.rest.order.dto.request.createorder.CreateOrderAddressRequestDto;
import com.e.bambi.order.infrastructure.rest.order.dto.request.createorder.CreateOrderItemRequestDto;
import com.e.bambi.order.infrastructure.rest.order.dto.request.createorder.CreateOrderRequestDto;
import com.e.bambi.shared.kernel.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class OrderRestMapper {

    public CreateOrderCommand toCreateOrderCommand(String userId, CreateOrderRequestDto request) {
        return CreateOrderCommand.builder()
                .userId(new UserId(UUID.fromString(userId)))
                .paymentMethod(new CreateOrderPaymentMethodCommand(
                        new PaymentMethodId(UUID.fromString(request.getPaymentMethod().getPaymentMethodId())),
                        request.getPaymentMethod().getName()
                ))
                .address(toCreateOrderAddressCommand(request.getAddress()))
                .items(toCreateOrderItemCommand(request.getItems()))
                .totalPrice(new Money(request.getTotalPrice()))
                .build();
    }

    public OrderQuery toOrderQuery(OrderRequestDto request) {
        return OrderQuery.builder()
                .paymentMethodId(convert(request.getPaymentMethodId(), UUID.class))
                .userId(convert(request.getUserId(), UUID.class))
                .createdAt(convert(request.getCreatedAt(), OffsetDateTime.class))
                .totalPrice(convert(request.getPrice(), BigDecimal.class))
                .orderBy(request.getOrderBy())
                .page(request.getPage())
                .build();
    }

    private CreateOrderAddressCommand toCreateOrderAddressCommand(CreateOrderAddressRequestDto address) {
        return CreateOrderAddressCommand.builder()
                .country(address.getCountry())
                .address(address.getAddress())
                .city(address.getCity())
                .province(address.getProvince())
                .postalCode(address.getPostalCode())
                .phoneNumber(address.getPhoneNumber())
                .build();
    }

    private List<CreateOrderItemCommand> toCreateOrderItemCommand(List<CreateOrderItemRequestDto> items) {
        return items.stream()
                .map(item ->
                        CreateOrderItemCommand.builder()
                                .imageUrl(item.getImageUrl())
                                .supplier(new CreateOrderItemSupplierCommand(
                                        new SupplierId(UUID.fromString(item.getSupplier().getSupplierId())),
                                        item.getSupplier().getName()
                                ))
                                .product(new CreateOrderItemProductCommand(
                                        new ProductId(UUID.fromString(item.getProduct().getProductId())),
                                        item.getProduct().getSku(),
                                        item.getProduct().getName()
                                ))
                                .price(new Money(item.getPrice()))
                                .quantity(item.getQuantity())
                                .totalPrice(new Money(item.getTotalPrice()))
                                .build())
                .toList();
    }

    private <T> List<T> convert(String chain, Class<T> type) {

        if (chain == null) {
            return null;
        }

        String[] values = BigDecimal.class.equals(type)
                ? chain.split("-")
                : chain.split("\\|");

        return Arrays.stream(values)
                .map(item -> {
                    if (UUID.class.equals(type)) {
                        @SuppressWarnings("unchecked")
                        T value = (T) UUID.fromString(item);
                        return value;
                    }
                    @SuppressWarnings("unchecked")
                    T value = (T) item;
                    return value;
                })
                .toList();
    }
}
