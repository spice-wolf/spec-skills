package com.wayne.ddddemotwo.catalog.domain.model;

import com.wayne.ddddemotwo.shared.domain.DomainException;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private final String id;
    private final String name;
    private BigDecimal price;
    private boolean active;

    public Product(String id, String name, BigDecimal price) {
        if (id == null || id.isBlank()) {
            throw new DomainException("Product id cannot be blank");
        }
        if (name == null || name.isBlank()) {
            throw new DomainException("Product name cannot be blank");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Product price must be greater than zero");
        }
        this.id = id;
        this.name = name;
        this.price = price;
        this.active = true;
    }

    public void changePrice(BigDecimal newPrice) {
        if (newPrice == null || newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Product price must be greater than zero");
        }
        this.price = newPrice;
    }

    public void deactivate() {
        this.active = false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Product product)) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
