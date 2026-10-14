package com.example.customerorders.service;

import com.example.customerorders.common.NotFoundException;
import com.example.customerorders.dto.CustomerResponse;
import com.example.customerorders.entity.Customer;
import com.example.customerorders.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @Test
    void getByIdReturnsCustomerResponse() {
        Customer customer = new Customer("Alice Johnson", "alice@example.com");
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerResponse result = service.getById(1L);

        assertThat(result.name()).isEqualTo("Alice Johnson");
        assertThat(result.email()).isEqualTo("alice@example.com");
    }

    @Test
    void getByIdThrowsWhenCustomerDoesNotExist() {
        when(repository.findById(404L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(404L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Customer 404 was not found");
    }

    @Test
    void deleteRemovesExistingCustomer() {
        Customer customer = new Customer("Alice Johnson", "alice@example.com");
        when(repository.findById(1L)).thenReturn(Optional.of(customer));

        service.delete(1L);

        verify(repository).delete(customer);
    }
}