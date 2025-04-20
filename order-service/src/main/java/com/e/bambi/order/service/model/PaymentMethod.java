package com.e.bambi.order.service.model;

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
@Table("payment_methods")
public class PaymentMethod {

    @Id
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String description;
}
