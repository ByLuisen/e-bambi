package com.e.bambi.order.application.order.dto.command.createorder;

import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderItemSupplierCommand {
    private final SupplierId supplierId;
    private final String name;

}
