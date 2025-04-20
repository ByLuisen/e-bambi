package com.e.bambi.order.service.model;

import lombok.Getter;

import java.util.UUID;

@Getter
public enum OrderStatusEnum {
    PENDING("5622ab4f-66fe-4b4a-89ca-9f685799dd4e"),
    PROCESSING("59110ea8-19ea-47f6-a3af-29beeade550c"),
    SHIPPED("43880647-fecb-4992-ae69-3275952c1e3f"),
    DELIVERED("62b09039-e6c3-4394-8b72-217dea7f99df"),
    CANCELLED("c0bb1115-b9ec-48ea-8fdb-201f0a002011"),
    RETURNED("335ec6e0-0a98-4f1f-8a30-f666a906b20d"),
    REFUNDED("b22eb1a8-55ed-40e1-a89d-3bcbe97cb527"),
    PAYMENT_FAILED("92a7c35d-3483-40e2-802b-6cb3761ccd9d"),
    ON_HOLD("adc87ef1-3384-4df3-9ad3-dfa935d5eb64"),
    AWAITING_PAYMENT("98ec5b76-3336-495a-af84-58816489523a");

    private final UUID value;

    OrderStatusEnum(String value) {
        this.value = UUID.fromString(value);
    }
}
