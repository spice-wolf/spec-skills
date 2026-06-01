package com.wayne.ddddemo.customer.domain.model;

import com.wayne.ddddemo.shared.domain.DomainException;

import java.math.BigDecimal;
import java.util.Objects;

public class Customer {

    private final String id;
    private final String name;
    private final CustomerLevel level;
    private final BigDecimal creditLimit;
    private boolean active;

    public Customer(String id, String name, CustomerLevel level, BigDecimal creditLimit) {
        if (id == null || id.isBlank()) {
            throw new DomainException("Customer id cannot be blank");
        }
        if (name == null || name.isBlank()) {
            throw new DomainException("Customer name cannot be blank");
        }
        if (level == null) {
            throw new DomainException("Customer level is required");
        }
        if (creditLimit == null || creditLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Credit limit cannot be negative");
        }
        this.id = id;
        this.name = name;
        this.level = level;
        this.creditLimit = creditLimit;
        this.active = true;
    }

    public boolean canPlaceOrder(BigDecimal orderAmount) {
        return active && creditLimit.compareTo(orderAmount) >= 0;
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

    public CustomerLevel getLevel() {
        return level;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Customer customer)) {
            return false;
        }
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
