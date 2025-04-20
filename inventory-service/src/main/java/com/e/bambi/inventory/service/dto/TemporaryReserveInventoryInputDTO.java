package com.e.bambi.inventory.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Getter
@Setter
public class TemporaryReserveInventoryInputDTO {

    @UUID
    @NotNull
    private String userId;

    @NotNull
    @Valid
    List<ProductReservationInputDTO> products;
}
