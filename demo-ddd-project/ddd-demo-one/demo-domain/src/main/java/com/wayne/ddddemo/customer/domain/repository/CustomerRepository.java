package com.wayne.ddddemo.customer.domain.repository;

import com.wayne.ddddemo.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(String id);

    List<Customer> findAll();

    Customer save(Customer customer);
}
