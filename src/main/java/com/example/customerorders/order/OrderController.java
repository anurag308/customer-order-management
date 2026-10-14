package com.example.customerorders.order;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(summary = "List all orders")
    @GetMapping
    public List<OrderResponse> findAll() {
        return service.findAll();
    }

    @Operation(summary = "List orders for a customer", description = "Uses a JPQL join between orders and customers.")
    @GetMapping("/by-customer/{customerId}")
    public List<CustomerOrderSummary> findByCustomer(@PathVariable long customerId) {
        return service.findByCustomer(customerId);
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/{id}")
    public OrderResponse getById(@PathVariable long id) {
        return service.getById(id);
    }

    @Operation(summary = "Create an order")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@Valid @RequestBody OrderRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Replace an order")
    @PutMapping("/{id}")
    public OrderResponse update(@PathVariable long id, @Valid @RequestBody OrderRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Delete an order")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}