package com.wayne.ddddemo.order.interfaces.rest;

import com.wayne.ddddemo.order.application.OrderApplicationService;
import com.wayne.ddddemo.order.application.dto.OrderDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderApplicationService orderApplicationService;

    public OrderController(OrderApplicationService orderApplicationService) {
        this.orderApplicationService = orderApplicationService;
    }

    @PostMapping
    public OrderDto createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderApplicationService.createOrder(request.toCommand());
    }

    @PostMapping("/{orderId}/pay")
    public OrderDto payOrder(@PathVariable String orderId) {
        return orderApplicationService.payOrder(orderId);
    }

    @PostMapping("/{orderId}/deliver")
    public OrderDto deliverOrder(@PathVariable String orderId) {
        return orderApplicationService.deliverOrder(orderId);
    }

    @GetMapping
    public List<OrderDto> listOrders() {
        return orderApplicationService.listOrders();
    }
}
