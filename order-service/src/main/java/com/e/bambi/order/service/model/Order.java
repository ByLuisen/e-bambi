package com.e.bambi.order.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("orders")
public class Order {

    @Id
    private UUID id;

    @NotNull
    @Column("status_id")
    private UUID statusId;

    @NotNull
    @Column("payment_method_id")
    private UUID paymentMethodId;

    @NotNull
    @Column("user_id")
    private UUID userId;

    @NotNull
    @Column("total_price")
    private BigDecimal totalPrice;

    @NotNull
    private String country;

    @NotNull
    private String address;

    @NotNull
    private String city;

    @NotNull
    private String province;

    @NotNull
    @Column("postal_code")
    private String postalCode;

    @NotNull
    @Column("phone_number")
    private String phoneNumber;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;
}
