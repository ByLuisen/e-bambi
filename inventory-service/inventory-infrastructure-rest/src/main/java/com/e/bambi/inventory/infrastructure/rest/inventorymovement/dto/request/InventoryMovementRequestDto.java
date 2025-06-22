package com.e.bambi.inventory.infrastructure.rest.inventorymovement.dto.request;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryMovementRequestDto {
    private static final String UUID_REGEX =
            "^([0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})" +
                    "(\\|[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12})*$";

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String supplierId;

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String productId;

    @Pattern(regexp = UUID_REGEX, message = "The format must be in the form 'uuid' or 'uuid|uuid'")
    private String movementTypeId;

    @Pattern(regexp = "^([a-zA-Z0-9-]{4,20})(\\|[a-zA-Z0-9-]{4,20})*$",
            message = "The format must be in the form 'sku' or 'sku|sku'")
    private String productSku;

    @Pattern(regexp = "^[a-z_]+-(asc|desc)$", message = "The format must be in the form 'created_at-desc'")
    private String orderBy = "created_at-desc"; // price-desc-rank o price=asc-rank

    @PositiveOrZero
    private int page = 0;

    @Positive
    private int size = 20;
}
