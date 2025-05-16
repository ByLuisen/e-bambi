package com.commerce.inventory_service.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ResponseProductSupplierOutputDTO {
    private UUID id;
    private UUID brandId;
    private UUID departmentId;
    private UUID productStatusId;
    private String sku;
    private String name;
    private BigDecimal originalPrice;
    private BigDecimal supplierPrice;
    private Integer supplierStock;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
