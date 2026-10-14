package com.example.customerorders.config;

import com.example.customerorders.entity.Customer;
import com.example.customerorders.entity.Order;
import com.example.customerorders.repository.CustomerRepository;
import com.example.customerorders.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(CustomerRepository customers, OrderRepository orders) {
        return args -> {
            if (customers.count() > 0) {
                return;
            }

            Customer alice = customers.save(new Customer("Alice Johnson", "alice@example.com"));
            Customer bob = customers.save(new Customer("Bob Smith", "bob@example.com"));
            orders.save(new Order("ORD-1001", new BigDecimal("129.95"), "PLACED", alice));
            orders.save(new Order("ORD-1002", new BigDecimal("49.50"), "SHIPPED", alice));
            orders.save(new Order("ORD-1003", new BigDecimal("250.00"), "PLACED", bob));
        };
    }
}