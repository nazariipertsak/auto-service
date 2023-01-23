package com.example.autoservice.service.impl;

import com.example.autoservice.model.Order;
import com.example.autoservice.repository.OrderRepository;
import com.example.autoservice.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getById(Long id) {
        return orderRepository.getByIdWithFavorsAndWares(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find order with id = " + id));
    }

    @Override
    public Order updateStatus(Order order, String status) {
        order.setStatus(getOrderStatus(status));
        if (order.getStatus().equals(Order.OrderStatus.SUCCESS)
                || order.getStatus().equals(Order.OrderStatus.FAILURE)) {
            order.setEndingDate(LocalDate.now());
        }
        return orderRepository.save(order);
    }

    private Order.OrderStatus getOrderStatus(String status) {
        return Order.OrderStatus.valueOf(status.toUpperCase());
    }
}
