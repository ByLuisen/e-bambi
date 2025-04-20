package com.e.bambi.order.service.mapper;

import com.e.bambi.order.service.dto.command.create.order.CreateOrderItemCommand;
import com.e.bambi.order.service.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    OrderItem createOrderItemCommandToOrderItem(CreateOrderItemCommand createOrderItemCommand, UUID orderId);
}
