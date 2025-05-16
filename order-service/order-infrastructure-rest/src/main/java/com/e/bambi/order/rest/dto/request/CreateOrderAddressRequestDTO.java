package com.e.bambi.order.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateOrderAddressRequestDTO {
    @NotNull
    @NotBlank
    @Pattern(regexp = "^(Canada|Spain)$", message = "Invalid shipping country")
    private String country;

    @NotNull
    @NotBlank
    @Size(max = 255, message = "Shipping address cannot be longer than 255 characters")
    private String address;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String province;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^[0-9]{5}(?:-[0-9]{4})?$", message = "Invalid postal code format")
    private String postalCode;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^\\+?[0-9]{1,4}[\\s-]?[0-9]+$", message = "Invalid phone number format")
    private String phoneNumber;
}
