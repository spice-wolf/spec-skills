package com.wayne.ddddemotwo.customer.domain.repository;

import com.wayne.ddddemotwo.customer.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Optional<Customer> findById(String id);

    List<Customer> findAll();

    Customer save(Customer customer);
}
