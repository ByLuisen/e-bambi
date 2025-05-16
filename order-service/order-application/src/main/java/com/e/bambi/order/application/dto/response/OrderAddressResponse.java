package com.e.bambi.order.application.dto.response;


public record OrderAddressResponse(String address, String country, String city,
                                   String province, String postalCode, String phoneNumber) {
}