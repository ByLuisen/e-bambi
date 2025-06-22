package com.e.bambi.order.infrastructure.rest.order.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {

    private final static String UUID_REGEX =
            "^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})" +
                    "(\\|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})*$";

    private final static String UUID_MESSAGE = "The format must be in the form 'uuid' or 'uuid|uuid'";

    @Pattern(regexp = UUID_REGEX, message = UUID_MESSAGE)
    private String paymentMethodId;

    @Pattern(regexp = UUID_REGEX, message = UUID_MESSAGE)
    private String userId;

    @Pattern(regexp = "^(\\d{4}-\\d{2}-\\d{2})\\|(\\d{4}-\\d{2}-\\d{2})$", message = "The format must be in the form 'YYYY-MM-DD|YYYY-MM-DD'")
    private String createdAt;

    @Pattern(regexp = "^\\d+-\\d+$", message = "The format must be in the form 'numbers-numbers', for example, '700-1700")
    private String price; // 400-1040

    @Pattern(regexp = "^[a-z_]+-(desc|asc)$", message = "The format must be in the form 'created_at-desc'")
    private String orderBy = "created_at-desc"; // total_price-desc o created_at-asc

    @PositiveOrZero
    private Integer page = 0;
}
