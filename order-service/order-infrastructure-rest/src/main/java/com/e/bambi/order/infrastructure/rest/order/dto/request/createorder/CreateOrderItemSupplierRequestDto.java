package com.e.bambi.order.infrastructure.rest.order.dto.request.createorder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class CreateOrderItemSupplierRequestDto {

    @UUID
    @NotNull
    private String id;

    @NotNull
    @NotBlank
    private String name;

}
