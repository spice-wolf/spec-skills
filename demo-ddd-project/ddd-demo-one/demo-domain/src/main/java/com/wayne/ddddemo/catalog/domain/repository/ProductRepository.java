package com.wayne.ddddemo.catalog.domain.repository;

import com.wayne.ddddemo.catalog.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(String id);

    List<Product> findAll();

    Product save(Product product);
}
