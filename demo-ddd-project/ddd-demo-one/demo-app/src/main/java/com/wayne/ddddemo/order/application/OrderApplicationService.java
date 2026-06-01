package com.wayne.ddddemo.order.application;

import com.wayne.ddddemo.catalog.domain.model.Product;
import com.wayne.ddddemo.catalog.domain.repository.ProductRepository;
import com.wayne.ddddemo.customer.domain.model.Customer;
import com.wayne.ddddemo.customer.domain.repository.CustomerRepository;
import com.wayne.ddddemo.order.application.command.CreateOrderCommand;
import com.wayne.ddddemo.order.application.command.CreateOrderItemCommand;
import com.wayne.ddddemo.order.application.dto.OrderDto;
import com.wayne.ddddemo.order.application.dto.OrderLineDto;
import com.wayne.ddddemo.order.application.event.OrderStatusChangedEvent;
import com.wayne.ddddemo.order.domain.model.Order;
import com.wayne.ddddemo.order.domain.model.OrderLine;
import com.wayne.ddddemo.order.domain.model.OrderStatus;
import com.wayne.ddddemo.order.domain.repository.OrderRepository;
import com.wayne.ddddemo.shared.domain.DomainException;
import com.wayne.ddddemo.util.TraceIdUtil;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderApplicationService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
    }

    public OrderDto createOrder(CreateOrderCommand command) {
        orderRepository.findById(command.orderId()).ifPresent(existing -> {
            throw new DomainException("Order already exists: " + existing.getId());
        });

        Customer customer = customerRepository.findById(command.customerId())
                .orElseThrow(() -> new DomainException("Customer not found: " + command.customerId()));

        if (!customer.isActive()) {
            throw new DomainException("Customer is inactive: " + customer.getId());
        }

        List<OrderLine> orderLines = command.items()
                .stream()
                .map(this::toOrderLine)
                .toList();

        Order order = new Order(command.orderId(), customer.getId(), orderLines);

        if (!customer.canPlaceOrder(order.totalAmount())) {
            throw new DomainException("Customer credit limit is not enough for this order");
        }

        return toDto(orderRepository.save(order));
    }

    public OrderDto payOrder(String orderId) {
        return changeOrderStatus(orderId, Order::markPaid);
    }

    public OrderDto deliverOrder(String orderId) {
        return changeOrderStatus(orderId, Order::markDelivered);
    }

    public List<OrderDto> listOrders() {
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .map(this::toDto)
                .toList();
    }

    private Order loadOrder(String orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new DomainException("Order not found: " + orderId));
    }

    private OrderDto changeOrderStatus(String orderId, Consumer<Order> statusChange) {
        Order order = loadOrder(orderId);
        OrderStatus previousStatus = order.getStatus();
        statusChange.accept(order);
        Order savedOrder = orderRepository.save(order);
        OrderDto orderDto = toDto(savedOrder);
        eventPublisher.publishEvent(new OrderStatusChangedEvent(orderDto, previousStatus, savedOrder.getStatus()));
        return orderDto;
    }

    private OrderLine toOrderLine(CreateOrderItemCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new DomainException("Product not found: " + command.productId()));
        if (!product.isActive()) {
            throw new DomainException("Product is inactive: " + product.getId());
        }
        return new OrderLine(product.getId(), product.getName(), product.getPrice(), command.quantity());
    }

    private OrderDto toDto(Order order) {
        List<OrderLineDto> lines = order.getOrderLines()
                .stream()
                .map(line -> new OrderLineDto(
                        line.getProductId(),
                        line.getProductName(),
                        line.getUnitPrice(),
                        line.getQuantity(),
                        line.subtotal()
                ))
                .toList();
        return new OrderDto(
                order.getId(),
                order.getCustomerId(),
                order.getStatus(),
                order.totalAmount(),
                order.getCreatedAt(),
                lines,
                TraceIdUtil.getTraceId()
        );
    }
}
