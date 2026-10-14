package com.example.customerorders.order;

import com.example.customerorders.customer.Customer;
import com.example.customerorders.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void joinQueryReturnsCustomerAndOrderDetails() {
        Customer customer = customerRepository.save(new Customer("Test Customer", "test@example.com"));
        orderRepository.save(new Order("TEST-1", new BigDecimal("19.99"), "PLACED", customer));

        var summaries = orderRepository.findOrderSummariesByCustomerId(customer.getId());

        assertThat(summaries).hasSize(1);
        assertThat(summaries.get(0).customerName()).isEqualTo("Test Customer");
        assertThat(summaries.get(0).orderNumber()).isEqualTo("TEST-1");
    }
}