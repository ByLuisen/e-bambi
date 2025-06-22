package com.e.bambi.inventory.infrastructure.persistence.image.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table("images")
public class ImageEntity {

    @Id
    private UUID id;
    @Column("product_id")
    private UUID productId;
    @Column("image_url")
    private String imageUrl;
}
