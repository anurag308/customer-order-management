package com.example.customerorders.customer;

public record CustomerResponse(Long id, String name, String email) {
    static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getName(), customer.getEmail());
    }
}