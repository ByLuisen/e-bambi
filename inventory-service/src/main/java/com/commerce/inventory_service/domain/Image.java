package com.commerce.inventory_service.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("images")
public class Image {
    @Id
    private UUID id;

    @NotNull
    @Column("product_id")
    private UUID productId;

    @NotNull
    @Column("url_image")
    private String urlImage;
}
