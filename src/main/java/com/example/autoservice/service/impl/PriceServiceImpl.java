package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Ware;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.PriceService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements PriceService {
    private static final double PRICE_OF_DIAGNOSIS = 500;
    private static final double ONE_HUNDRED_PERCENT = 100.0;
    private static final double DISCOUNT_PERCENT_PER_FAVOR = 1.0;
    private static final double DISCOUNT_PERCENT_PER_WARE = 2.0;
    private static final String DIAGNOSIS_FAVOR_NAME = "Diagnosis";
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
        List<Order> orders = getPreviousSuccessCarOwnersOrders(order.getCarOwnerId());
        int carOwnersPreviousOrdersCount = orders.size();
        double priceOfWares = countPriceOfWares(order.getWares(), carOwnersPreviousOrdersCount);
        double priceOfFavors = countPriceOfFavors(order.getFavors(), carOwnersPreviousOrdersCount);
        double priceOfOrder = priceOfFavors + priceOfWares;
        priceOfOrder -= countPriceOfDiagnosis(order.getFavors());
        order.setFinalPrice(BigDecimal.valueOf(priceOfOrder));
        orderService.save(order);
        return BigDecimal.valueOf(priceOfOrder);
    }

    private List<Order> getPreviousSuccessCarOwnersOrders(Long carOwnerId) {
        return carOwnerService.getById(carOwnerId).getOrders().stream()
                .filter(o -> o.getStatus().equals(Order.OrderStatus.SUCCESS))
                .toList();
    }

    private double countPriceOfWares(List<Ware> wares, int carOwnersPreviousOrdersCount) {
        return (wares.stream()
                .mapToDouble(w -> w.getPrice().doubleValue())
                .sum()
                * (ONE_HUNDRED_PERCENT -
                (carOwnersPreviousOrdersCount * DISCOUNT_PERCENT_PER_WARE)))
                / ONE_HUNDRED_PERCENT;
    }

    private double countPriceOfFavors(Set<Favor> favors, int carOwnersPreviousOrdersCount) {
        return (favors.stream()
                .mapToDouble(f -> f.getPrice().doubleValue())
                .sum()
                * (ONE_HUNDRED_PERCENT -
                (carOwnersPreviousOrdersCount * DISCOUNT_PERCENT_PER_FAVOR)))
                / ONE_HUNDRED_PERCENT;
    }

    private double countPriceOfDiagnosis(Set<Favor> favors) {
        List<String> favorsNames = favors.stream()
                .map(Favor::getName)
                .toList();
        return (favors.size() > 1 && favorsNames.contains(DIAGNOSIS_FAVOR_NAME))
                ? PRICE_OF_DIAGNOSIS : 0.0;
    }
}
