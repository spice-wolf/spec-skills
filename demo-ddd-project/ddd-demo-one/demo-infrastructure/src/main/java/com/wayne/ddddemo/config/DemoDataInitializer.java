package com.wayne.ddddemo.config;

import com.wayne.ddddemo.catalog.application.CatalogApplicationService;
import com.wayne.ddddemo.catalog.application.command.RegisterProductCommand;
import com.wayne.ddddemo.customer.application.CustomerApplicationService;
import com.wayne.ddddemo.customer.application.command.RegisterCustomerCommand;
import com.wayne.ddddemo.customer.domain.model.CustomerLevel;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DemoDataInitializer {

    @Bean
    CommandLineRunner loadDemoData(
            CatalogApplicationService catalogApplicationService,
            CustomerApplicationService customerApplicationService
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
        };
    }
}
