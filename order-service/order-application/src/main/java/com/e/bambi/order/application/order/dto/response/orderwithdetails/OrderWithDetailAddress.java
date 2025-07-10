package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderWithDetailAddress {
    private final String country;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;
}
