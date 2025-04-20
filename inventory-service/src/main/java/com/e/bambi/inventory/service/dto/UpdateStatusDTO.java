package com.e.bambi.inventory.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;

@Setter
public class UpdateStatusDTO {

    @UUID
    @NotNull
    private String statusId;

    public java.util.UUID getStatusId() {
        return java.util.UUID.fromString(statusId);
    }
}
