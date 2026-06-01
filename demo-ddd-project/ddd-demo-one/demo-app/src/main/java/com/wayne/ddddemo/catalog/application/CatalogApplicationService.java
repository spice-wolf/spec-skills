package com.wayne.ddddemo.catalog.application;

import com.wayne.ddddemo.catalog.application.command.RegisterProductCommand;
import com.wayne.ddddemo.catalog.application.dto.ProductDto;
import com.wayne.ddddemo.catalog.domain.model.Product;
import com.wayne.ddddemo.catalog.domain.repository.ProductRepository;
import com.wayne.ddddemo.shared.domain.DomainException;
import com.wayne.ddddemo.util.TraceIdUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CatalogApplicationService {

    private final ProductRepository productRepository;

    public CatalogApplicationService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDto registerProduct(RegisterProductCommand command) {
        productRepository.findById(command.productId()).ifPresent(existing -> {
            throw new DomainException("Product already exists: " + existing.getId());
        });
        Product product = new Product(command.productId(), command.name(), command.price());
        return toDto(productRepository.save(product));
    }

    public List<ProductDto> listProducts() {
        return productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getId))
                .map(this::toDto)
                .toList();
    }

    private ProductDto toDto(Product product) {
        return new ProductDto(product.getId(), product.getName(), product.getPrice(), product.isActive(), TraceIdUtil.getTraceId());
    }
}
