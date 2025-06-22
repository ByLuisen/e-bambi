package com.e.bambi.order.application.order.dto.command.createorder;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateOrderAddressCommand {
    private final String country;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;
}
