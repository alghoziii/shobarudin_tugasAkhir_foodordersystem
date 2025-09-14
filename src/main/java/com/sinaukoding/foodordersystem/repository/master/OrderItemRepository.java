package com.sinaukoding.foodordersystem.repository.master;

import com.sinaukoding.foodordersystem.entity.transaction.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}