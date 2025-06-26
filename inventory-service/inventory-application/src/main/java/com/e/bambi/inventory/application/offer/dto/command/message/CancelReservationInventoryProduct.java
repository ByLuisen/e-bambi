package com.e.bambi.inventory.application.offer.dto.command.message;

import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CancelReservationInventoryProduct {
    private final ProductId productId;
    private final SupplierId supplierId;
    private final Stock quantity;
}
