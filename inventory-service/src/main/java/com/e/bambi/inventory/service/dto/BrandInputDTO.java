package com.e.bambi.inventory.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandInputDTO {
    @NotNull
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9][A-Za-z0-9 .-]*[A-Za-z0-9]$", message = "Name must only contain letters, numbers, spaces, dots, and hyphens")
    private String name;
}
