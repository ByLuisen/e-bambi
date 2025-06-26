package com.e.bambi.order.application.order.dto.command.createorder;

import com.e.bambi.shared.kernel.domain.valueobject.PaymentMethodId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderPaymentMethodCommand {

    private final PaymentMethodId id;
    private final String name;
}
