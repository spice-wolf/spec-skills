package com.wayne.ddddemotwo.payment.domain.model;

import com.wayne.ddddemotwo.shared.domain.DomainException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {

    private final String id;
    private final String orderId;
    private final BigDecimal amount;
    private final String method;
    private final LocalDateTime paidAt;
    private final PaymentStatus status;

    public Payment(String id, String orderId, BigDecimal amount, String method) {
        if (id == null || id.isBlank()) {
            throw new DomainException("Payment id cannot be blank");
        }
        if (orderId == null || orderId.isBlank()) {
            throw new DomainException("Order id cannot be blank");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("Payment amount must be greater than zero");
        }
        if (method == null || method.isBlank()) {
            throw new DomainException("Payment method cannot be blank");
        }
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
        this.paidAt = LocalDateTime.now();
        this.status = PaymentStatus.RECORDED;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getMethod() {
        return method;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Payment payment)) {
            return false;
        }
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
