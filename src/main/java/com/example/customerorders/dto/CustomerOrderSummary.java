package com.example.customerorders.dto;

import java.math.BigDecimal;

public record CustomerOrderSummary(Long orderId, String orderNumber, String status, BigDecimal amount,
                                   Long customerId, String customerName, String customerEmail) {
}