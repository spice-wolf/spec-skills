package com.wayne.ddddemotwo.inventory.infrastructure.repository;

import com.wayne.ddddemotwo.inventory.domain.model.StockItem;
import com.wayne.ddddemotwo.inventory.domain.repository.StockItemRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryStockItemRepository implements StockItemRepository {

    private final ConcurrentMap<String, StockItem> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<StockItem> findByProductId(String productId) {
        return Optional.ofNullable(storage.get(productId));
    }

    @Override
    public List<StockItem> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public StockItem save(StockItem stockItem) {
        storage.put(stockItem.getProductId(), stockItem);
        return stockItem;
    }
}
