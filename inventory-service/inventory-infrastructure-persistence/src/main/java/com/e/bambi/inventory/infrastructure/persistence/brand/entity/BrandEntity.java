package com.e.bambi.inventory.infrastructure.persistence.brand.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("brands")
public class BrandEntity {

    @Id
    private UUID id;
    private String name;
}
