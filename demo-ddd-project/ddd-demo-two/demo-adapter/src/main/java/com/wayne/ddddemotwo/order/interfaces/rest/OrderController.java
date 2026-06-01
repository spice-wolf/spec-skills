package com.wayne.ddddemotwo.order.interfaces.rest;

import com.wayne.ddddemotwo.order.application.OrderApplicationService;
import com.wayne.ddddemotwo.order.application.dto.OrderDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public List<OrderDto> listOrders() {
        return orderApplicationService.listOrders();
    }
}
