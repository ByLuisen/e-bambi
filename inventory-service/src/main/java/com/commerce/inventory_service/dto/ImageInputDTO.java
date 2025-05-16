package com.commerce.inventory_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter
public class ImageInputDTO {

    @UUID
    @NotNull
    private String productId;

    @NotNull
    @NotBlank(message = "Image is required")
    @Pattern(regexp = "^(https?://.*\\.(?:png|jpg|jpeg|gif|bmp|webp))$",
            message = "The URL must be valid and point to an image.")
    private String urlImage;
}
