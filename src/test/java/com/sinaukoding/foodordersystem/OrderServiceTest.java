package com.sinaukoding.foodordersystem;

import com.sinaukoding.foodordersystem.entity.managementuser.User;
import com.sinaukoding.foodordersystem.entity.transaction.Order;
import com.sinaukoding.foodordersystem.mapper.master.OrderMapper;
import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import com.sinaukoding.foodordersystem.repository.managementuser.UserRepository;
import com.sinaukoding.foodordersystem.repository.master.MenuItemRepository;
import com.sinaukoding.foodordersystem.repository.master.OrderRepository;
import com.sinaukoding.foodordersystem.service.app.ValidatorService;
import com.sinaukoding.foodordersystem.service.transaction.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ValidatorService validatorService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private UserRepository userRepository;

    @Mock private MenuItemRepository menuItemRepository;

    @Test
    void testFindById_Success() {
        var user = new User(); user.setId("8681d4c7-2e80-46a0-8984-38f30fc02e3c");

        var order = new Order();
        order.setId("c1f0f2a5-cd61-4e88-ab99-8aa4161d3590");
        order.setUser(user);
        order.setStatus(OrderStatus.PAID);
        order.setTotalAmount(10000D);

        when(orderRepository.findById("c1f0f2a5-cd61-4e88-ab99-8aa4161d3590")).thenReturn(Optional.of(order));

        var result = orderService.findById("c1f0f2a5-cd61-4e88-ab99-8aa4161d3590");

        verify(orderRepository, times(1)).findById("c1f0f2a5-cd61-4e88-ab99-8aa4161d3590");
        assertEquals("c1f0f2a5-cd61-4e88-ab99-8aa4161d3590", result.get("id"));
        assertEquals("8681d4c7-2e80-46a0-8984-38f30fc02e3c", result.get("userId"));
        assertEquals(OrderStatus.PAID, result.get("status"));
        assertEquals(10000D, result.get("totalAmount"));
    }

    @Test
    void testFindById_NotFound() {
        when(orderRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.findById("missing"));
        verify(orderRepository, times(1)).findById("missing");
    }
}