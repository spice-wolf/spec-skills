package com.wayne.ddddemotwo.inventory.domain.repository;

import com.wayne.ddddemotwo.inventory.domain.model.StockItem;

import java.util.List;
import java.util.Optional;

public interface StockItemRepository {

    Optional<StockItem> findByProductId(String productId);

    List<StockItem> findAll();

    StockItem save(StockItem stockItem);
}
