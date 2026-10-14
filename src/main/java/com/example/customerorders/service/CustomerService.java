package com.example.customerorders.service;

import com.example.customerorders.common.NotFoundException;
import com.example.customerorders.dto.CustomerRequest;
import com.example.customerorders.dto.CustomerResponse;
import com.example.customerorders.entity.Customer;
import com.example.customerorders.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CustomerResponse> search(String query, Pageable pageable) {
        return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(query, query, pageable)
                .map(CustomerResponse::from);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(long id) {
        return CustomerResponse.from(findEntity(id));
    }

    public CustomerResponse create(CustomerRequest request) {
        return CustomerResponse.from(repository.save(new Customer(request.name(), request.email())));
    }

    public CustomerResponse update(long id, CustomerRequest request) {
        Customer customer = findEntity(id);
        customer.setName(request.name());
        customer.setEmail(request.email());
        return CustomerResponse.from(repository.save(customer));
    }

    public void delete(long id) {
        Customer customer = findEntity(id);
        repository.delete(Objects.requireNonNull(customer));
    }

    private Customer findEntity(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer %d was not found".formatted(id)));
    }
}