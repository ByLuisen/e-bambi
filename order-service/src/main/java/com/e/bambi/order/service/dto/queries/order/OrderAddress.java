package com.e.bambi.order.service.dto.queries.order;


public record OrderAddress (String shippingAddress, String shippingCountry, String shippingCity,
                               String shippingProvince, String shippingPostalCode, String shippingPhoneNumber) {
}