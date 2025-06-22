package com.e.bambi.inventory.infrastructure.persistence.productstatus.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("product_statuses")
public class ProductStatusEntity {

    @Id
    private UUID id;
    private String name;
}
