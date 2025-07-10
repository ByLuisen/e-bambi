package com.e.bambi.inventory.domain.inventorymovement.entity;

import com.e.bambi.inventory.domain.exception.InventoryDomainException;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementId;
import com.e.bambi.inventory.domain.inventorymovement.valueobject.InventoryMovementProduct;
import com.e.bambi.inventory.domain.shared.valueobject.MovementTypeId;
import com.e.bambi.inventory.domain.shared.valueobject.Stock;
import com.e.bambi.shared.kernel.domain.entity.AggregateRoot;
import com.e.bambi.shared.kernel.domain.valueobject.SupplierId;
import lombok.Getter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
public class InventoryMovement extends AggregateRoot<InventoryMovementId> {
    private final SupplierId supplierId;
    private final InventoryMovementProduct product;
    private final MovementTypeId movementTypeId;
    private final Integer quantity;
    private Integer previousStock;
    private Integer newStock;
    private OffsetDateTime createdAt;

    private InventoryMovement(Builder builder) {
        super.setId(builder.id);
        supplierId = builder.supplierId;
        product = builder.product;
        movementTypeId = builder.movementTypeId;
        quantity = builder.quantity;
        previousStock = builder.previousStock;
        newStock = builder.newStock;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public void initializeInventoryMovement() {
        super.setId(new InventoryMovementId(UUID.randomUUID()));
        createdAt = OffsetDateTime.now();
    }

    public void calculateStock(Stock stock, Integer quantity) {
        if ((stock.getQuantity() + quantity) < 0) {
            throw new InventoryDomainException("The product does not have enough stock to carry out the operation." +
                    " Current stock: " + stock.getQuantity());
        }
        previousStock = stock.getQuantity();
        newStock = stock.getQuantity() + quantity;
    }

    public static final class Builder {
        private InventoryMovementId id;
        private SupplierId supplierId;
        private InventoryMovementProduct product;
        private MovementTypeId movementTypeId;
        private Integer quantity;
        private Integer previousStock;
        private Integer newStock;
        private OffsetDateTime createdAt;

        private Builder() {
        }

        public Builder id(InventoryMovementId val) {
            id = val;
            return this;
        }

        public Builder supplierId(SupplierId val) {
            supplierId = val;
            return this;
        }

        public Builder product(InventoryMovementProduct val) {
            product = val;
            return this;
        }

        public Builder movementTypeId(MovementTypeId val) {
            movementTypeId = val;
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

        public Builder createdAt(OffsetDateTime val) {
            createdAt = val;
            return this;
        }

        public InventoryMovement build() {
            return new InventoryMovement(this);
        }
    }
}
