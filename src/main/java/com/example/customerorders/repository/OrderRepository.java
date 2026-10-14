package com.example.customerorders.repository;

import com.example.customerorders.dto.CustomerOrderSummary;
import com.example.customerorders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select new com.example.customerorders.dto.CustomerOrderSummary(" +
            "o.id, o.orderNumber, o.status, o.amount, c.id, c.name, c.email) " +
            "from Order o join o.customer c where c.id = :customerId order by o.id")
    List<CustomerOrderSummary> findOrderSummariesByCustomerId(@Param("customerId") Long customerId);
}