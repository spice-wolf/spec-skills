package com.wayne.ddddemotwo.order.domain.model;

import com.wayne.ddddemotwo.shared.domain.DomainException;

import java.math.BigDecimal;

public class OrderLine {

    private final String productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int quantity;

    public OrderLine(String productId, String productName, BigDecimal unitPrice, int quantity) {
        if (productId == null || productId.isBlank()) {
            throw new DomainException("Product id cannot be blank");
        }
        if (productName == null || productName.isBlank()) {
            throw new DomainException("Product name cannot be blank");
        }
        if (unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Unit price must be greater than zero");
        }
        if (quantity <= 0) {
            throw new DomainException("Quantity must be greater than zero");
        }
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public BigDecimal subtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
