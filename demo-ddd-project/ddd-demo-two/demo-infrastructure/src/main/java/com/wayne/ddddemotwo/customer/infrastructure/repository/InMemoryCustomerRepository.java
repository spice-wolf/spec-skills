package com.wayne.ddddemotwo.customer.infrastructure.repository;

import com.wayne.ddddemotwo.customer.domain.model.Customer;
import com.wayne.ddddemotwo.customer.domain.repository.CustomerRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final ConcurrentMap<String, Customer> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Customer> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Customer> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Customer save(Customer customer) {
        storage.put(customer.getId(), customer);
        return customer;
    }
}
