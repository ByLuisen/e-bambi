package com.commerce.inventory_service.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMovementFilterInputDTO {

    private static final String UUID_REGEX =
            "^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})(\\|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})*$";

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String id; // p_123:213735|581378

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String movementTypeId;

    @Pattern(regexp = "^([a-zA-Z0-9-]{4,20})(\\|[a-zA-Z0-9-]{4,20})*$", message = "The format must be in the form 'sku' or 'sku|sku'")
    private String productSku;

    @Pattern(regexp = "^[a-z_]+-(asc|desc)$", message = "The format must be in the form 'created_at-desc'")
    private String orderBy = "created_at-desc"; // price-desc-rank o price=asc-rank

    @PositiveOrZero
    private Integer page = 0;

    @PositiveOrZero
    private Integer size = 20;
}
