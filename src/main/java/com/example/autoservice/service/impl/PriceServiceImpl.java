package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.PriceService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {
    private static final double PRICE_OF_DIAGNOSIS = 500;
    private static final double ONE_HUNDRED_PERCENT = 100.0;
    private final CarOwnerService carOwnerService;
    private final OrderService orderService;

    public PriceServiceImpl(CarOwnerService carOwnerService, OrderService orderService) {
        this.carOwnerService = carOwnerService;
        this.orderService = orderService;
    }

    @Override
    public BigDecimal countPrice(Long orderId) {
        Order order = orderService.getById(orderId);
        if (!order.getStatus().equals(Order.OrderStatus.ACCEPTED)) {
            return order.getFinalPrice();
        }
        List<Order> orders = carOwnerService.getById(order.getCarOwnerId()).getOrders().stream()
                .filter(o -> o.getStatus().equals(Order.OrderStatus.SUCCESS))
                .toList();
        int carOwnersPreviousOrdersCount = orders.size();
        double priceOfWares = (order.getWares().stream()
                .mapToDouble(w -> w.getPrice().doubleValue())
                .sum()
                * (ONE_HUNDRED_PERCENT - carOwnersPreviousOrdersCount)) / ONE_HUNDRED_PERCENT;
        double priceOfFavors = (order.getFavors().stream()
                .mapToDouble(f -> f.getPrice().doubleValue())
                .sum()
                * (ONE_HUNDRED_PERCENT - carOwnersPreviousOrdersCount)) / ONE_HUNDRED_PERCENT;
        double priceOfOrder = priceOfFavors + priceOfWares;
        List<String> favorsNames = order.getFavors().stream()
                .map(Favor::getName)
                .toList();
        if (order.getFavors().size() > 1 && favorsNames.contains("Diagnosis")) {
            priceOfOrder -= PRICE_OF_DIAGNOSIS;
        }
        order.setFinalPrice(BigDecimal.valueOf(priceOfOrder));
        orderService.save(order);
        return BigDecimal.valueOf(priceOfOrder);
    }
}
