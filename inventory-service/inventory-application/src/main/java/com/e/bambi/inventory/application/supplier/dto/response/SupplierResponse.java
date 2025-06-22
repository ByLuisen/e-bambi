package com.e.bambi.inventory.application.supplier.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class SupplierResponse {
    private final UUID id;
    private final String name;
}
