package com.sinaukoding.foodordersystem.repository.master;

import com.sinaukoding.foodordersystem.entity.transaction.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, String>, JpaSpecificationExecutor<Order> {
}