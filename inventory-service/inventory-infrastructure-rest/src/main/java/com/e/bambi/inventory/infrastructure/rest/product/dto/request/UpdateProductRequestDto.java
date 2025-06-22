package com.e.bambi.inventory.infrastructure.rest.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class UpdateProductRequestDto {

    @UUID
    @NotNull
    private String brandId;

    @UUID
    @NotNull
    private String departmentId;

    @UUID
    @NotNull
    private String productStatusId;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9_-]+$", message = "The SKU can only contain letters, numbers, hyphens, and underscores.")
    private String sku;

    @NotNull
    @NotBlank(message = "Product name cannot be empty")
    @Size(min = 3, max = 100, message = "The name must be between 3 and 100 characters long.")
    @Pattern(regexp = "^[A-Za-z0-9 ]+$", message = "The name can only contain letters, numbers and spaces")
    private String name;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "The description must be between 10 and 500 characters.")
    private String description;
}
