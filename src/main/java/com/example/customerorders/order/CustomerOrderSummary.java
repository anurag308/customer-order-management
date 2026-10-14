package com.example.customerorders.order;

import java.math.BigDecimal;

public record CustomerOrderSummary(Long orderId, String orderNumber, String status, BigDecimal amount,
                                   Long customerId, String customerName, String customerEmail) {
}