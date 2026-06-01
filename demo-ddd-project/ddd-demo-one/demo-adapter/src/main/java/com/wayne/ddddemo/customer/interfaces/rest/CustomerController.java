package com.wayne.ddddemo.customer.interfaces.rest;

import com.wayne.ddddemo.customer.application.CustomerApplicationService;
import com.wayne.ddddemo.customer.application.dto.CustomerDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerApplicationService customerApplicationService;

    public CustomerController(CustomerApplicationService customerApplicationService) {
        this.customerApplicationService = customerApplicationService;
    }

    @PostMapping
    public CustomerDto registerCustomer(@Valid @RequestBody RegisterCustomerRequest request) {
        return customerApplicationService.registerCustomer(request.toCommand());
    }

    @GetMapping
    public List<CustomerDto> listCustomers() {
        return customerApplicationService.listCustomers();
    }
}
