package com.e.bambi.order.application.order.dto.command.createorder;

import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderItemProductCommand {
    private final ProductId id;
    private final String sku;
    private final String name;
}
