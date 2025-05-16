package com.e.bambi.order.domain.valueobject;

import lombok.Getter;

@Getter
public class OrderAddress {
    private final String country;
    private final String address;
    private final String city;
    private final String province;
    private final String postalCode;
    private final String phoneNumber;

    private OrderAddress(Builder builder) {
        country = builder.country;
        address = builder.address;
        city = builder.city;
        province = builder.province;
        postalCode = builder.postalCode;
        phoneNumber = builder.phoneNumber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String country;
        private String address;
        private String city;
        private String province;
        private String postalCode;
        private String phoneNumber;

        private Builder() {
        }

        public Builder country(String val) {
            country = val;
            return this;
        }

        public Builder address(String val) {
            address = val;
            return this;
        }

        public Builder city(String val) {
            city = val;
            return this;
        }

        public Builder province(String val) {
            province = val;
            return this;
        }

        public Builder postalCode(String val) {
            postalCode = val;
            return this;
        }

        public Builder phoneNumber(String val) {
            phoneNumber = val;
            return this;
        }

        public OrderAddress build() {
            return new OrderAddress(this);
        }
    }
}