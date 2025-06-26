package com.e.bambi.inventory.domain.shared.valueobject;

import lombok.Getter;

@Getter
public class Stock {

    private final Integer quantity;

    public Stock(Integer quantity) {
        this.quantity = quantity;
    }

    public Stock subtract(Stock stock) {
        return new Stock(this.quantity - stock.getQuantity());
    }

    public Stock add(Stock stock) {
        return new Stock(this.quantity + stock.getQuantity());
    }
}
