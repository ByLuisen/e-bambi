package com.e.bambi.order.service.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("order_status_history")
public class OrderStatusHistory {

    @Id
    private UUID id;

    @NotNull
    @Column("order_id")
    private UUID orderId;

    @NotNull
    @Column("order_status_id")
    private UUID orderStatusId;

    @NotNull
    private String reason;

    private LocalDateTime changedAt;
}
