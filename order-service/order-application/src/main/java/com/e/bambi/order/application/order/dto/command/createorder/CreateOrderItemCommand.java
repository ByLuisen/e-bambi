package com.e.bambi.order.application.order.dto.command.createorder;

import com.e.bambi.shared.kernel.domain.valueobject.Money;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderItemCommand {
    private final String imageUrl;
    private final CreateOrderItemSupplierCommand supplier;
    private final CreateOrderItemProductCommand product;
    private final Money price;
    private final Integer quantity;
    private final Money totalPrice;
}
