package com.e.bambi.order.service.mapper;

import com.e.bambi.order.service.dto.command.create.CreatePaymentMethodCommand;
import com.e.bambi.order.service.dto.command.update.UpdatePaymentMethodCommand;
import com.e.bambi.order.service.model.PaymentMethod;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    @Mapping(target = "id", ignore = true)
    PaymentMethod createPaymentMethodCommandToPaymentMethod(CreatePaymentMethodCommand createPaymentMethodCommand);

    PaymentMethod updatePaymentMethodCommandToPaymentMethod(UpdatePaymentMethodCommand updatePaymentMethodCommand);
}
