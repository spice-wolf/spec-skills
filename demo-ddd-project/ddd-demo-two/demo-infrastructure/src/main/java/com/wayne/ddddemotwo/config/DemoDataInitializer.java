package com.wayne.ddddemotwo.config;

import com.wayne.ddddemotwo.catalog.application.CatalogApplicationService;
import com.wayne.ddddemotwo.catalog.application.command.RegisterProductCommand;
import com.wayne.ddddemotwo.customer.application.CustomerApplicationService;
import com.wayne.ddddemotwo.customer.application.command.RegisterCustomerCommand;
import com.wayne.ddddemotwo.customer.domain.model.CustomerLevel;
import com.wayne.ddddemotwo.inventory.application.InventoryApplicationService;
import com.wayne.ddddemotwo.inventory.application.command.UpsertStockItemCommand;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner loadDemoData(
            CatalogApplicationService catalogApplicationService,
            CustomerApplicationService customerApplicationService,
            InventoryApplicationService inventoryApplicationService
    ) {
        return args -> {
            if (catalogApplicationService.listProducts().isEmpty()) {
                catalogApplicationService.registerProduct(new RegisterProductCommand("P-100", "Coffee Beans", new BigDecimal("48.00")));
                catalogApplicationService.registerProduct(new RegisterProductCommand("P-200", "Hand Grinder", new BigDecimal("159.00")));
                catalogApplicationService.registerProduct(new RegisterProductCommand("P-300", "Filter Paper", new BigDecimal("29.90")));
            }
            if (customerApplicationService.listCustomers().isEmpty()) {
                customerApplicationService.registerCustomer(new RegisterCustomerCommand("C-100", "Alice", CustomerLevel.STANDARD, new BigDecimal("500.00")));
                customerApplicationService.registerCustomer(new RegisterCustomerCommand("C-200", "Bob", CustomerLevel.VIP, new BigDecimal("1500.00")));
            }
            if (inventoryApplicationService.listStockItems().isEmpty()) {
                inventoryApplicationService.upsertStockItem(new UpsertStockItemCommand("P-100", 100));
                inventoryApplicationService.upsertStockItem(new UpsertStockItemCommand("P-200", 50));
                inventoryApplicationService.upsertStockItem(new UpsertStockItemCommand("P-300", 200));
            }
        };
    }
}
