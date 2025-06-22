package com.e.bambi.inventory.infrastructure.rest.supplier.dto.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequestDto {
    @Positive
    private int size = 20;

    @PositiveOrZero
    private int page = 0;
}
