package com.e.bambi.inventory.infrastructure.persistence.supplier.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table("suppliers")
public class SupplierEntity {

    @Id
    private UUID id;
    private String name;
}
