package com.sinaukoding.foodordersystem.mapper.master;

import com.sinaukoding.foodordersystem.entity.managementuser.User;
import com.sinaukoding.foodordersystem.entity.transaction.Order;
import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import org.springframework.stereotype.Component;


@Component
public class OrderMapper {

    public Order newOrder(User user, OrderStatus status) {
        return Order.builder()
                .user(user)
                .status(status)
                .totalAmount(0.0)
                .build();
    }

    public void updateStatus(Order entity, OrderStatus status) {
        entity.setStatus(status);
    }
}