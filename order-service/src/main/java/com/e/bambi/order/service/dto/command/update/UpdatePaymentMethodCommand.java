package com.e.bambi.order.service.dto.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePaymentMethodCommand {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name can only contain letters and spaces.")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Description can only contain letters and spaces.")
    private String description;
}
