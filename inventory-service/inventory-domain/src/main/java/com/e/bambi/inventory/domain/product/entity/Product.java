package com.e.bambi.inventory.domain.product.entity;

import com.e.bambi.inventory.domain.productstatus.valueobject.ProductStatusId;
import com.e.bambi.inventory.domain.shared.valueobject.BrandId;
import com.e.bambi.inventory.domain.shared.valueobject.DepartmentId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class Product extends AggregateRoot<ProductId> {
    private final BrandId brandId;
    private final DepartmentId departmentId;
    private final ProductStatusId productStatusId;
    private final String sku;
    private final String name;
    private final String description;
    private Instant createAt;
    private Instant updatedAt;

    private Product(Builder builder) {
        super.setId(builder.id);
        brandId = builder.brandId;
        departmentId = builder.departmentId;
        productStatusId = builder.productStatusId;
        sku = builder.sku;
        name = builder.name;
        description = builder.description;
        createAt = builder.createAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeProduct() {
        super.setId(new ProductId(UUID.randomUUID()));
        createAt = Instant.now();
    }

    public void updateProduct() {
        updatedAt = Instant.now();
    }


    public static final class Builder {
        private ProductId id;
        private BrandId brandId;
        private DepartmentId departmentId;
        private ProductStatusId productStatusId;
        private String sku;
        private String name;
        private String description;
        private Instant createAt;
        private Instant updatedAt;

        private Builder() {
        }

        public Builder id(ProductId val) {
            id = val;
            return this;
        }

        public Builder brandId(BrandId val) {
            brandId = val;
            return this;
        }

        public Builder departmentId(DepartmentId val) {
            departmentId = val;
            return this;
        }

        public Builder productStatusId(ProductStatusId val) {
            productStatusId = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
            return this;
        }

        public Builder createAt(Instant val) {
            createAt = val;
            return this;
        }

        public Builder updatedAt(Instant val) {
            updatedAt = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
