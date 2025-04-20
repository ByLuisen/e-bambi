package com.e.bambi.order.service.mapper;

import com.e.bambi.order.service.model.Order;
import com.e.bambi.order.service.dto.command.create.order.CreateOrderCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "statusId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "address", source = "address.address")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "province", source = "address.province")
    @Mapping(target = "postalCode", source = "address.postalCode")
    @Mapping(target = "phoneNumber", source = "address.phoneNumber")
    Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand);
}
