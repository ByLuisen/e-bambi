package com.e.bambi.order.domain.order.valueobject;

import lombok.Getter;

@Getter
public class OrderAddress {
    private final String country;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;

    public OrderAddress(String country, String address, String city, String province, String postalCode, String phoneNumber) {
        this.country = country;
        this.address = address;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
    }
}