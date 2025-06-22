package com.e.bambi.inventory.infrastructure.persistence.product.mapper;

import com.e.bambi.inventory.application.product.dto.response.ProductSummaryReadResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsBrandResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsDepartmentResponse;
import com.e.bambi.inventory.application.product.dto.response.ProductWithDetailsReadResponse;
import com.e.bambi.inventory.domain.product.entity.Product;
import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.inventory.infrastructure.persistence.product.entity.ProductEntity;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Component
public class ProductPersistenceMapper {

    public ProductEntity toProductEntity(Product product) {
        return ProductEntity.builder()
                .id(product.getId().getValue())
                .brandId(product.getBrandId().getValue())
                .departmentId(product.getDepartmentId().getValue())
                .productStatusId(product.getProductStatusId().getValue())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .createdAt(product.getCreateAt())
                .updatedAt(product.getUpdatedAt() != null ? product.getUpdatedAt() : null)
                .build();
    }

    public Product toProduct(ProductEntity entity) {
        return Product.builder()
                .id(new ProductId(entity.getId()))
                .brandId(new BrandId(entity.getBrandId()))
                .departmentId(new DepartmentId(entity.getDepartmentId()))
                .productStatusId(new ProductStatusId(entity.getProductStatusId()))
                .sku(entity.getSku())
                .name(entity.getName())
                .description(entity.getDescription())
                .createAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt() != null ? entity.getUpdatedAt() : null)
                .build();
    }

    public ProductSummaryReadResponse toProductSummaryReadResponse(Record r) {
        return new ProductSummaryReadResponse(
                r.get("id", UUID.class),
                r.get("sku", String.class),
                r.get("name", String.class),
                r.get("brand", String.class),
                r.get("image_url", String.class),
                r.get("created_at", OffsetDateTime.class)
        );
    }

    public ProductWithDetailsReadResponse toProductWithDetailsReadResponse(Record r) {
        Result<Record> images = r.get("images", Result.class);

        return ProductWithDetailsReadResponse.builder()
                .sku(r.get("product_sku", String.class))
                .name(r.get("product_name", String.class))
                .description(r.get("product_description", String.class))
                .brand(new ProductWithDetailsBrandResponse(
                        r.get("brand_id", UUID.class),
                        r.get("brand_name", String.class)
                ))
                .department(new ProductWithDetailsDepartmentResponse(
                        r.get("department_id", UUID.class),
                        r.get("department_name", String.class)
                ))
                .status(r.get("product_status_name", String.class))
                .images(images.map(image ->
                        image.get("image_url", String.class))
                )
                .build();
    }

}
