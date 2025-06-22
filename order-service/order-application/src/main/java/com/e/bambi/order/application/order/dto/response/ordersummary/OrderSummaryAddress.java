package com.e.bambi.order.application.order.dto.response.ordersummary;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderSummaryAddress {
    private final String country;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;
}
