package com.wayne.ddddemo.catalog.interfaces.rest;

import com.wayne.ddddemo.catalog.application.CatalogApplicationService;
import com.wayne.ddddemo.catalog.application.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog/products")
public class CatalogController {

    private final CatalogApplicationService catalogApplicationService;

    public CatalogController(CatalogApplicationService catalogApplicationService) {
        this.catalogApplicationService = catalogApplicationService;
    }

    @PostMapping
    public ProductDto registerProduct(@Valid @RequestBody RegisterProductRequest request) {
        return catalogApplicationService.registerProduct(request.toCommand());
    }

    @GetMapping
    public List<ProductDto> listProducts() {
        return catalogApplicationService.listProducts();
    }
}
