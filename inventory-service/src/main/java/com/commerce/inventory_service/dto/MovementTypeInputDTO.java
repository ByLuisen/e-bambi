package com.commerce.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovementTypeInputDTO {
    @NotNull
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9 .-]*[A-Za-z0-9]$", message = "Name must only contain letters, numbers, spaces, dots, and hyphens")
    private String name;

    @NotNull
    @NotBlank(message = "Description is required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Description must only contain letters, numbers and spaces.")
    @Size(min = 5, max = 100, message = "Description must be between 5 and 100 characters.")
    private String description;
}
