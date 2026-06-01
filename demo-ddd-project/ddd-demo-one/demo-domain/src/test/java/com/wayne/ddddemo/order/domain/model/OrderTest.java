package com.wayne.ddddemo.order.domain.model;

import com.wayne.ddddemo.shared.domain.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderTest {

    @Test
    void shouldCalculateTotalAndCompleteHappyPath() {
        Order order = new Order(
                "O-100",
                "C-100",
                List.of(
                        new OrderLine("P-100", "Coffee Beans", new BigDecimal("48.00"), 2),
                        new OrderLine("P-300", "Filter Paper", new BigDecimal("29.90"), 1)
                )
        );

        assertEquals(new BigDecimal("125.90"), order.totalAmount());

        order.markPaid();
        order.markDelivered();

        assertEquals(OrderStatus.DELIVERED, order.getStatus());
    }

    @Test
    void shouldRejectDeliveringUnpaidOrder() {
        Order order = new Order(
                "O-101",
                "C-100",
                List.of(new OrderLine("P-100", "Coffee Beans", new BigDecimal("48.00"), 1))
        );

        assertThrows(DomainException.class, order::markDelivered);
    }
}
