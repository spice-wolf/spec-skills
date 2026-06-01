package com.wayne.ddddemotwo.order.application;

import com.wayne.ddddemotwo.catalog.domain.model.Product;
import com.wayne.ddddemotwo.catalog.domain.repository.ProductRepository;
import com.wayne.ddddemotwo.customer.domain.model.Customer;
import com.wayne.ddddemotwo.customer.domain.repository.CustomerRepository;
import com.wayne.ddddemotwo.inventory.domain.model.StockItem;
import com.wayne.ddddemotwo.inventory.domain.repository.StockItemRepository;
import com.wayne.ddddemotwo.order.application.command.CreateOrderCommand;
import com.wayne.ddddemotwo.order.application.command.CreateOrderItemCommand;
import com.wayne.ddddemotwo.order.application.dto.OrderDto;
import com.wayne.ddddemotwo.order.application.dto.OrderLineDto;
import com.wayne.ddddemotwo.order.domain.model.Order;
import com.wayne.ddddemotwo.order.domain.model.OrderLine;
import com.wayne.ddddemotwo.order.domain.repository.OrderRepository;
import com.wayne.ddddemotwo.shared.domain.DomainException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final StockItemRepository stockItemRepository;

    public OrderApplicationService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            StockItemRepository stockItemRepository
    ) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.stockItemRepository = stockItemRepository;
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

        List<OrderLine> lines = command.items().stream().map(this::toOrderLine).toList();
        Order order = new Order(command.orderId(), customer.getId(), lines);

        if (!customer.canPlaceOrder(order.totalAmount())) {
            throw new DomainException("Customer credit limit is not enough for this order");
        }

        reserveStock(command.items());
        return toDto(orderRepository.save(order));
    }

    public List<OrderDto> listOrders() {
        return orderRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Order::getCreatedAt))
                .map(this::toDto)
                .toList();
    }

    private OrderLine toOrderLine(CreateOrderItemCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new DomainException("Product not found: " + command.productId()));
        if (!product.isActive()) {
            throw new DomainException("Product is inactive: " + product.getId());
        }
        StockItem stockItem = stockItemRepository.findByProductId(command.productId())
                .orElseThrow(() -> new DomainException("Stock item not found for product: " + command.productId()));
        if (!stockItem.hasEnough(command.quantity())) {
            throw new DomainException("Not enough stock for product: " + command.productId());
        }
        return new OrderLine(product.getId(), product.getName(), product.getPrice(), command.quantity());
    }

    private void reserveStock(List<CreateOrderItemCommand> items) {
        for (CreateOrderItemCommand item : items) {
            StockItem stockItem = stockItemRepository.findByProductId(item.productId())
                    .orElseThrow(() -> new DomainException("Stock item not found for product: " + item.productId()));
            stockItem.decrease(item.quantity());
            stockItemRepository.save(stockItem);
        }
    }

    private OrderDto toDto(Order order) {
        List<OrderLineDto> lines = order.getLines()
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
                lines
        );
    }
}
