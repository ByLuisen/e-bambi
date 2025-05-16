package com.e.bambi.order.rest.mapper;

import com.e.bambi.order.application.dto.command.CreateOrderAddressCommand;
import com.e.bambi.order.application.dto.command.CreateOrderCommand;
import com.e.bambi.order.application.dto.command.CreateOrderItemCommand;
import com.e.bambi.order.application.dto.query.OrderQuery;
import com.e.bambi.order.domain.entity.Product;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.order.rest.dto.request.CreateOrderAddressRequestDTO;
import com.e.bambi.order.rest.dto.request.CreateOrderItemRequestDTO;
import com.e.bambi.order.rest.dto.request.CreateOrderRequestDTO;
import com.e.bambi.order.rest.dto.request.OrderQueryRequestDTO;
import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderRestMapper {

    public CreateOrderCommand toCreateOrderCommand(CreateOrderRequestDTO createOrderRequestDTO) {
        return CreateOrderCommand.builder()
                .paymentMethodId(new PaymentMethodId(UUID.fromString(createOrderRequestDTO.getPaymentMethodId())))
                .address(toCreateOrderAddressCommand(createOrderRequestDTO.getAddress()))
                .items(toCreateOrderItemCommand(createOrderRequestDTO.getItems()))
                .totalPrice(new Money(createOrderRequestDTO.getTotalPrice()))
                .build();
    }

    public OrderQuery toOrderQuery(OrderQueryRequestDTO orderQueryRequestDTO) {
        return OrderQuery.builder()
                .statusId(orderQueryRequestDTO.getStatusId())
                .paymentMethodId(orderQueryRequestDTO.getPaymentMethodId())
                .userId(orderQueryRequestDTO.getUserId())
                .createdAt(orderQueryRequestDTO.getCreatedAt())
                .totalPrice(orderQueryRequestDTO.getTotalPrice())
                .orderBy(orderQueryRequestDTO.getOrderBy())
                .page(orderQueryRequestDTO.getPage())
                .size(orderQueryRequestDTO.getSize())
                .build();
    }

    private CreateOrderAddressCommand toCreateOrderAddressCommand(CreateOrderAddressRequestDTO
                                                                          createOrderAddressRequestDTO) {
        return CreateOrderAddressCommand.builder()
                .country(createOrderAddressRequestDTO.getCountry())
                .address(createOrderAddressRequestDTO.getAddress())
                .city(createOrderAddressRequestDTO.getCity())
                .province(createOrderAddressRequestDTO.getProvince())
                .postalCode(createOrderAddressRequestDTO.getPostalCode())
                .phoneNumber(createOrderAddressRequestDTO.getPhoneNumber())
                .build();
    }

    private List<CreateOrderItemCommand> toCreateOrderItemCommand(List<CreateOrderItemRequestDTO> items) {
        return items.stream()
                .map(item ->
                        CreateOrderItemCommand.builder()
                                .product(new Product(new ProductId(
                                        UUID.fromString(item.getProductId())),
                                        item.getName(),
                                        item.getSku(),
                                        new Money(item.getPrice()))
                                )
                                .soldBy(item.getSoldBy())
                                .quantity(item.getQuantity())
                                .totalPrice(new Money(item.getTotalPrice()))
                                .discount(new Money(item.getDiscount()))
                                .build())
                .toList();
    }
}
