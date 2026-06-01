package com.wayne.ddddemotwo.inventory.domain.model;

import com.wayne.ddddemotwo.shared.domain.DomainException;

import java.util.Objects;

public class StockItem {

    private final String productId;
    private int availableQuantity;

    public StockItem(String productId, int availableQuantity) {
        if (productId == null || productId.isBlank()) {
            throw new DomainException("Product id cannot be blank");
        }
        if (availableQuantity < 0) {
            throw new DomainException("Available quantity cannot be negative");
        }
        this.productId = productId;
        this.availableQuantity = availableQuantity;
    }

    public boolean hasEnough(int quantity) {
        return availableQuantity >= quantity;
    }

    public void decrease(int quantity) {
        if (quantity <= 0) {
            throw new DomainException("Decrease quantity must be greater than zero");
        }
        if (!hasEnough(quantity)) {
            throw new DomainException("Not enough stock for product: " + productId);
        }
        this.availableQuantity -= quantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        if (availableQuantity < 0) {
            throw new DomainException("Available quantity cannot be negative");
        }
        this.availableQuantity = availableQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof StockItem stockItem)) {
            return false;
        }
        return Objects.equals(productId, stockItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
