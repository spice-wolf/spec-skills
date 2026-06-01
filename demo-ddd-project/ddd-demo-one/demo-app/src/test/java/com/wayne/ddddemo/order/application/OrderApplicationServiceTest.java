package com.wayne.ddddemo.order.application;

import com.wayne.ddddemo.catalog.domain.model.Product;
import com.wayne.ddddemo.catalog.domain.repository.ProductRepository;
import com.wayne.ddddemo.customer.domain.model.Customer;
import com.wayne.ddddemo.customer.domain.model.CustomerLevel;
import com.wayne.ddddemo.customer.domain.repository.CustomerRepository;
import com.wayne.ddddemo.order.application.event.OrderStatusChangedEvent;
import com.wayne.ddddemo.order.domain.model.Order;
import com.wayne.ddddemo.order.domain.model.OrderLine;
import com.wayne.ddddemo.order.domain.model.OrderStatus;
import com.wayne.ddddemo.order.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderApplicationServiceTest {

    @Test
    void shouldPublishOrderStatusChangedEventWhenOrderIsPaid() {
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();
        Order order = new Order(
                "O-100",
                "C-100",
                List.of(new OrderLine("P-100", "Coffee Beans", new BigDecimal("48.00"), 2))
        );
        orderRepository.save(order);
        CapturingApplicationEventPublisher eventPublisher = new CapturingApplicationEventPublisher();
        OrderApplicationService service = new OrderApplicationService(
                orderRepository,
                new StubCustomerRepository(),
                new StubProductRepository(),
                eventPublisher
        );

        var result = service.payOrder("O-100");

        assertEquals(OrderStatus.PAID, result.status());
        assertEquals(1, eventPublisher.events.size());
        OrderStatusChangedEvent event = eventPublisher.events.get(0);
        assertEquals(OrderStatus.CREATED, event.previousStatus());
        assertEquals(OrderStatus.PAID, event.currentStatus());
        assertEquals("O-100", event.order().id());
        assertEquals(OrderStatus.PAID, event.order().status());
        assertEquals(new BigDecimal("96.00"), event.order().totalAmount());
        assertNotNull(event.order().traceId());
    }

    @Test
    void shouldPublishOrderStatusChangedEventWhenOrderIsDelivered() {
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();
        Order order = new Order(
                "O-101",
                "C-100",
                List.of(new OrderLine("P-100", "Coffee Beans", new BigDecimal("48.00"), 1))
        );
        order.markPaid();
        orderRepository.save(order);
        CapturingApplicationEventPublisher eventPublisher = new CapturingApplicationEventPublisher();
        OrderApplicationService service = new OrderApplicationService(
                orderRepository,
                new StubCustomerRepository(),
                new StubProductRepository(),
                eventPublisher
        );

        var result = service.deliverOrder("O-101");

        assertEquals(OrderStatus.DELIVERED, result.status());
        assertEquals(1, eventPublisher.events.size());
        OrderStatusChangedEvent event = eventPublisher.events.get(0);
        assertEquals(OrderStatus.PAID, event.previousStatus());
        assertEquals(OrderStatus.DELIVERED, event.currentStatus());
        assertEquals("O-101", event.order().id());
        assertEquals(OrderStatus.DELIVERED, event.order().status());
    }

    @Test
    void shouldNotPublishEventWhenStatusChangeFails() {
        InMemoryOrderRepository orderRepository = new InMemoryOrderRepository();
        Order order = new Order(
                "O-102",
                "C-100",
                List.of(new OrderLine("P-100", "Coffee Beans", new BigDecimal("48.00"), 1))
        );
        orderRepository.save(order);
        CapturingApplicationEventPublisher eventPublisher = new CapturingApplicationEventPublisher();
        OrderApplicationService service = new OrderApplicationService(
                orderRepository,
                new StubCustomerRepository(),
                new StubProductRepository(),
                eventPublisher
        );

        try {
            service.deliverOrder("O-102");
        } catch (RuntimeException ex) {
            assertTrue(eventPublisher.events.isEmpty());
            return;
        }

        throw new AssertionError("Expected deliverOrder to fail for unpaid order");
    }

    private static final class CapturingApplicationEventPublisher implements ApplicationEventPublisher {

        private final List<OrderStatusChangedEvent> events = new ArrayList<>();

        @Override
        public void publishEvent(Object event) {
            if (event instanceof OrderStatusChangedEvent orderStatusChangedEvent) {
                events.add(orderStatusChangedEvent);
            }
        }
    }

    private static final class InMemoryOrderRepository implements OrderRepository {

        private final List<Order> orders = new ArrayList<>();

        @Override
        public Optional<Order> findById(String id) {
            return orders.stream()
                    .filter(order -> order.getId().equals(id))
                    .findFirst();
        }

        @Override
        public List<Order> findAll() {
            return List.copyOf(orders);
        }

        @Override
        public Order save(Order order) {
            orders.removeIf(existing -> existing.getId().equals(order.getId()));
            orders.add(order);
            return order;
        }
    }

    private static final class StubCustomerRepository implements CustomerRepository {

        @Override
        public Optional<Customer> findById(String id) {
            return Optional.of(new Customer(id, "Alice", CustomerLevel.VIP, new BigDecimal("1000.00")));
        }

        @Override
        public List<Customer> findAll() {
            return List.of();
        }

        @Override
        public Customer save(Customer customer) {
            return customer;
        }
    }

    private static final class StubProductRepository implements ProductRepository {

        @Override
        public Optional<Product> findById(String id) {
            return Optional.of(new Product(id, "Coffee Beans", new BigDecimal("48.00")));
        }

        @Override
        public List<Product> findAll() {
            return List.of();
        }

        @Override
        public Product save(Product product) {
            return product;
        }
    }
}
