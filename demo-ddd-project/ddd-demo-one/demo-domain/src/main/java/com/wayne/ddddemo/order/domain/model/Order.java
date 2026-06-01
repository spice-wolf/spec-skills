package com.wayne.ddddemo.order.domain.model;

import com.wayne.ddddemo.shared.domain.DomainException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {

    private final String id;
    private final String customerId;
    private final List<OrderLine> orderLines;
    private final LocalDateTime createdAt;
    private OrderStatus status;

    public Order(String id, String customerId, List<OrderLine> orderLines) {
        if (id == null || id.isBlank()) {
            throw new DomainException("Order id cannot be blank");
        }
        if (customerId == null || customerId.isBlank()) {
            throw new DomainException("Customer id cannot be blank");
        }
        if (orderLines == null || orderLines.isEmpty()) {
            throw new DomainException("Order must contain at least one line");
        }
        this.id = id;
        this.customerId = customerId;
        this.orderLines = new ArrayList<>(orderLines);
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
    }

    public BigDecimal totalAmount() {
        return orderLines.stream()
                .map(OrderLine::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void markPaid() {
        if (status != OrderStatus.CREATED) {
            throw new DomainException("Only created orders can be paid");
        }
        this.status = OrderStatus.PAID;
    }

    public void markDelivered() {
        if (status != OrderStatus.PAID) {
            throw new DomainException("Only paid orders can be delivered");
        }
        this.status = OrderStatus.DELIVERED;
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public List<OrderLine> getOrderLines() {
        return List.copyOf(orderLines);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Order order)) {
            return false;
        }
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
