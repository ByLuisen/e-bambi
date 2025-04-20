package com.e.bambi.order.service.mapper;

import com.e.bambi.order.service.dto.command.create.CreateOrderStatusCommand;
import com.e.bambi.order.service.dto.command.update.UpdateOrderStatusCommand;
import com.e.bambi.order.service.model.OrderStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatus createOrderStatusCommandToOrderStatus(CreateOrderStatusCommand createOrderStatusCommand);

    OrderStatus udpateOrderStatusCommandToOrderStatus(UpdateOrderStatusCommand updateOrderStatusCommand);
}
