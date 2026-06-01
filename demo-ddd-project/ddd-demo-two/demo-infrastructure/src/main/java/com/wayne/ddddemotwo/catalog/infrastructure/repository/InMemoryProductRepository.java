package com.wayne.ddddemotwo.catalog.infrastructure.repository;

import com.wayne.ddddemotwo.catalog.domain.model.Product;
import com.wayne.ddddemotwo.catalog.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final ConcurrentMap<String, Product> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Product save(Product product) {
        storage.put(product.getId(), product);
        return product;
    }
}
