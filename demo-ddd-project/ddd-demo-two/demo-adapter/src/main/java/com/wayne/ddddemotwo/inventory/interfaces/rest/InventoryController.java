package com.wayne.ddddemotwo.inventory.interfaces.rest;

import com.wayne.ddddemotwo.inventory.application.InventoryApplicationService;
import com.wayne.ddddemotwo.inventory.application.dto.StockItemDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory/items")
public class InventoryController {

    private final InventoryApplicationService inventoryApplicationService;

    public InventoryController(InventoryApplicationService inventoryApplicationService) {
        this.inventoryApplicationService = inventoryApplicationService;
    }

    @PostMapping
    public StockItemDto upsertStockItem(@Valid @RequestBody UpsertStockItemRequest request) {
        return inventoryApplicationService.upsertStockItem(request.toCommand());
    }

    @GetMapping
    public List<StockItemDto> listStockItems() {
        return inventoryApplicationService.listStockItems();
    }
}
