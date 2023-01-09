package com.example.autoservice.service;

import com.example.autoservice.model.Order;

public interface OrderService {
    Order save(Order order);

    Order getById(Long id);

    Order updateStatus(Order order, String status);
}
