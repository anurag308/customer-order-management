package com.example.customerorders.order;

import com.example.customerorders.common.NotFoundException;
import com.example.customerorders.customer.Customer;
import com.example.customerorders.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orders;
    private final CustomerRepository customers;

    public OrderService(OrderRepository orders, CustomerRepository customers) {
        this.orders = orders;
        this.customers = customers;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orders.findAll().stream().map(OrderResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<CustomerOrderSummary> findByCustomer(long customerId) {
        if (!customers.existsById(customerId)) {
            throw new NotFoundException("Customer %d was not found".formatted(customerId));
        }
        return orders.findOrderSummariesByCustomerId(customerId);
    }

    @Transactional(readOnly = true)
    public OrderResponse getById(long id) {
        return OrderResponse.from(findEntity(id));
    }

    public OrderResponse create(OrderRequest request) {
        Customer customer = customers.findById(Objects.requireNonNull(request.customerId()))
                .orElseThrow(() -> new NotFoundException("Customer %d was not found".formatted(request.customerId())));
        Order order = new Order(request.orderNumber(), request.amount(), request.status(), customer);
        return OrderResponse.from(orders.save(order));
    }

    public OrderResponse update(long id, OrderRequest request) {
        Order order = findEntity(id);
        Customer customer = customers.findById(Objects.requireNonNull(request.customerId()))
                .orElseThrow(() -> new NotFoundException("Customer %d was not found".formatted(request.customerId())));
        order.setOrderNumber(request.orderNumber());
        order.setAmount(request.amount());
        order.setStatus(request.status());
        order.setCustomer(customer);
        return OrderResponse.from(orders.save(order));
    }

    public void delete(long id) {
        orders.delete(Objects.requireNonNull(findEntity(id)));
    }

    private Order findEntity(long id) {
        return orders.findById(id)
                .orElseThrow(() -> new NotFoundException("Order %d was not found".formatted(id)));
    }
}