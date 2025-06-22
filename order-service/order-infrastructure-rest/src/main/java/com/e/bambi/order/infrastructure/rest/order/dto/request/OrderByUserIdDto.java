package com.e.bambi.order.infrastructure.rest.order.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderByUserIdDto {

    @PositiveOrZero
    private int page = 0;
    @Pattern(regexp = "^(3|30|\\d{4})$")
    private Integer date = 30;
}
