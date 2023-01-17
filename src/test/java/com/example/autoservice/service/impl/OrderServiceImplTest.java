package com.example.autoservice.service.impl;

import com.example.autoservice.model.Order;
import com.example.autoservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;

    @Test
    void shouldReturnOrderWithOtherStatus() {
        Order order = new Order();
        order.setStatus(Order.OrderStatus.ACCEPTED);
        String status = "processing";
        orderService.updateStatus(order, status);
        Assertions.assertEquals(Order.OrderStatus.PROCESSING, order.getStatus());
    }

    @Test
    void shouldSetEndingDateIfStatusChangedToFailureOrSuccess() {
        Order order = new Order();
        order.setStatus(Order.OrderStatus.ACCEPTED);
        String successStatus = "success";
        orderService.updateStatus(order, successStatus);
        Assertions.assertNotNull(order.getEndingDate());
        order.setStatus(Order.OrderStatus.ACCEPTED);
        String failureStatus = "failure";
        orderService.updateStatus(order, failureStatus);
        Assertions.assertNotNull(order.getEndingDate());
    }
}
