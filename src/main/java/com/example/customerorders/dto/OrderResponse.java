package com.example.customerorders.dto;

import com.example.customerorders.entity.Order;

import java.math.BigDecimal;

public record OrderResponse(Long id, String orderNumber, BigDecimal amount, String status, Long customerId, String customerName) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getAmount(), order.getStatus(),
                order.getCustomer().getId(), order.getCustomer().getName());
    }
}