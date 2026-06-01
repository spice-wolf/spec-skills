package com.wayne.ddddemotwo.inventory.application;

import com.wayne.ddddemotwo.inventory.application.command.UpsertStockItemCommand;
import com.wayne.ddddemotwo.inventory.application.dto.StockItemDto;
import com.wayne.ddddemotwo.inventory.domain.model.StockItem;
import com.wayne.ddddemotwo.inventory.domain.repository.StockItemRepository;
import com.wayne.ddddemotwo.util.TraceIdUtil;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class InventoryApplicationService {

    private final StockItemRepository stockItemRepository;

    public InventoryApplicationService(StockItemRepository stockItemRepository) {
        this.stockItemRepository = stockItemRepository;
    }

    public StockItemDto upsertStockItem(UpsertStockItemCommand command) {
        StockItem stockItem = stockItemRepository.findByProductId(command.productId())
                .map(existing -> {
                    existing.setAvailableQuantity(command.availableQuantity());
                    return existing;
                })
                .orElseGet(() -> new StockItem(command.productId(), command.availableQuantity()));
        return toDto(stockItemRepository.save(stockItem));
    }

    public List<StockItemDto> listStockItems() {
        return stockItemRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(StockItem::getProductId))
                .map(this::toDto)
                .toList();
    }

    private StockItemDto toDto(StockItem stockItem) {
        return new StockItemDto(stockItem.getProductId(), stockItem.getAvailableQuantity(), TraceIdUtil.getTraceId());
    }
}
