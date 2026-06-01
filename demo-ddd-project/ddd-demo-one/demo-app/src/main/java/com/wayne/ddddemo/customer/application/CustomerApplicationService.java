package com.wayne.ddddemo.customer.application;

import com.wayne.ddddemo.customer.application.command.RegisterCustomerCommand;
import com.wayne.ddddemo.customer.application.dto.CustomerDto;
import com.wayne.ddddemo.customer.domain.model.Customer;
import com.wayne.ddddemo.customer.domain.repository.CustomerRepository;
import com.wayne.ddddemo.shared.domain.DomainException;
import com.wayne.ddddemo.util.TraceIdUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CustomerApplicationService {

    private final CustomerRepository customerRepository;

    public CustomerApplicationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerDto registerCustomer(RegisterCustomerCommand command) {
        customerRepository.findById(command.customerId()).ifPresent(existing -> {
            throw new DomainException("Customer already exists: " + existing.getId());
        });
        Customer customer = new Customer(command.customerId(), command.name(), command.level(), command.creditLimit());
        return toDto(customerRepository.save(customer));
    }

    public List<CustomerDto> listCustomers() {
        return customerRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Customer::getId))
                .map(this::toDto)
                .toList();
    }

    private CustomerDto toDto(Customer customer) {
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getLevel(),
                customer.getCreditLimit(),
                customer.isActive(),
                TraceIdUtil.getTraceId()
        );
    }
}
