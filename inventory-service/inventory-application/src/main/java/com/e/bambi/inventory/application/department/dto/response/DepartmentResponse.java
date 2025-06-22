package com.e.bambi.inventory.application.department.dto.response;

import java.util.UUID;

public record DepartmentResponse(
    UUID id,
    String name
) {
}
