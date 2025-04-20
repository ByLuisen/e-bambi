package com.e.bambi.inventory.service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductInputDTO {

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

    @NotNull
    @DecimalMin(value = "0.01", message = "The price must be greater than zero")
    @Digits(integer = 10, fraction = 2, message = "The price cannot have more than 2 decimal places")
    private BigDecimal price;

    @NotNull(message = "Description cannot be null")
    @NotBlank(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "The description must be between 10 and 500 characters.")
    private String description;

    private Integer versionNumber;
}
