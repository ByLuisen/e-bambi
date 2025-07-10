package com.e.bambi.order.application.order.dto.response.orderwithdetails;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class OrderWithDetailItemSupplier {
    private final UUID id;
    private final String name;
}
