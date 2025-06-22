package com.e.bambi.inventory.domain.inventorymovement.entity;

import com.e.bambi.inventory.domain.exception.InsufficientStockException;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.ProductId;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class InventoryMovement extends AggregateRoot<InventoryMovementId> {
    private final SupplierId supplierId;
    private final ProductId productId;
    private final MovementTypeId movementTypeId;
    private final String productSku;
    private final String productName;
    private final Integer quantity;
    private Integer previousStock;
    private Integer newStock;
    private Instant createdAt;

    public void initializeInventoryMovement() {
        super.setId(new InventoryMovementId(UUID.randomUUID()));
        createdAt = Instant.now();
    }

    public void calculateStock(Stock stock, Integer quantity) {
        if ((stock.getQuantity() + quantity) < 0) {
            throw new InsufficientStockException("The product does not have enough stock to carry out the operation." +
                    " Current stock: " + stock.getQuantity());
        }
        previousStock = stock.getQuantity();
        newStock = stock.getQuantity() + quantity;
    }

    private InventoryMovement(Builder builder) {
        super.setId(builder.inventoryMovementId);
        supplierId = builder.supplierId;
        productId = builder.productId;
        movementTypeId = builder.movementTypeId;
        productSku = builder.productSku;
        productName = builder.productName;
        quantity = builder.quantity;
        previousStock = builder.previousStock;
        newStock = builder.newStock;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private InventoryMovementId inventoryMovementId;
        private SupplierId supplierId;
        private ProductId productId;
        private MovementTypeId movementTypeId;
        private String productSku;
        private String productName;
        private Integer quantity;
        private Integer previousStock;
        private Integer newStock;
        private Instant createdAt;

        private Builder() {
        }

        public Builder id(InventoryMovementId val) {
            inventoryMovementId = val;
            return this;
        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder productId(ProductId val) {
            productId = val;
            return this;
        }

        public Builder movementTypeId(MovementTypeId val) {
            movementTypeId = val;
            return this;
        }

        public Builder productSku(String val) {
            productSku = val;
            return this;
        }

        public Builder productName(String val) {
            productName = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder previousStock(Integer val) {
            previousStock = val;
            return this;
        }

        public Builder newStock(Integer val) {
            newStock = val;
            return this;
        }

        public Builder createdAt(Instant val) {
            createdAt = val;
            return this;
        }

        public InventoryMovement build() {
            return new InventoryMovement(this);
        }
    }
}
