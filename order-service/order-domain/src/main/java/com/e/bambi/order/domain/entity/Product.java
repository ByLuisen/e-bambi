package com.e.bambi.order.domain.entity;

import com.e.bambi.shared.kernel.domain.valueobject.Money;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import com.e.bambi.shared.kernel.domain.entity.BaseEntity;
import lombok.Getter;

@Getter
public class Product extends BaseEntity<ProductId> {
    private String name;
    private String sku;
    private Money price;

    public Product(ProductId productId, String name, String sku, Money price) {
        super.setId(productId);
        this.name = name;
        this.sku = sku;
        this.price = price;
    }

    public Product(String name) {
        this.name = name;
    }

    public void updateWithConfirmedNameAndSkuAndPrice(String name, String sku, Money price) {
        this.name = name;
        this.sku = sku;
        this.price = price;
    }
}
