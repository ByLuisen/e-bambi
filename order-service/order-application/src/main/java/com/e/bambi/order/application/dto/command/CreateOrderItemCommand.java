package com.e.bambi.order.application.dto.command;

import com.e.bambi.order.domain.entity.Product;
import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.application.bus.Command;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderItemCommand implements Command<Void> {
    private Product product;
    private String soldBy;
    private Integer quantity;
    private Money totalPrice;
    private Money discount;
}
