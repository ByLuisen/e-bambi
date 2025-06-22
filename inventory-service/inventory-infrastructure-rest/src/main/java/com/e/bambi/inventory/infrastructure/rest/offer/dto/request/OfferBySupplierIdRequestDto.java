package com.e.bambi.inventory.infrastructure.rest.offer.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferBySupplierIdRequestDto {

    @NotNull
    @Positive
    private int size = 10;

    @NotNull
    @PositiveOrZero
    private int page = 0;
}
