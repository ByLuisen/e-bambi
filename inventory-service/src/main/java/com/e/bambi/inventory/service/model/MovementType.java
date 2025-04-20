package com.e.bambi.inventory.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("movement_types")
public class MovementType {
    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
