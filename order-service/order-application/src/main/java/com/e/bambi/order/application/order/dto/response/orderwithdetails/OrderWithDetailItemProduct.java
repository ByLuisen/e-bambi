package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class OrderWithDetailItemProduct {
    private final UUID id;
    private final String name;
}
