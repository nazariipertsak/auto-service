package com.example.autoservice.service.impl;

import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Ware;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.OrderService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceServiceImplTest {
    @InjectMocks
    private PriceServiceImpl priceService;
    @Mock
    private OrderService orderService;
    @Mock
    private CarOwnerService carOwnerService;

    @Test
    void shouldCountSalaryWithDiscountAndSetFinalPrice() {
        Set<Favor> favors = Set.of(new Favor(1L, "Diagnosis", BigDecimal.valueOf(1000),
                1L, Favor.FavorStatus.NOT_PAID));
        List<Ware> wares = List.of(new Ware(1L, "Oil", BigDecimal.valueOf(1000)),
                new Ware(2L, "Belt", BigDecimal.valueOf(1000)));
        Order order = new Order(1L, 1L, 1L, "Noise", favors, wares,
                LocalDate.now(), null, Order.OrderStatus.ACCEPTED, null);
        Mockito.when(orderService.getById(1L)).thenReturn(order);
        Order firstTestOrder = new Order();
        firstTestOrder.setStatus(Order.OrderStatus.SUCCESS);
        Order secondTestOrder = new Order();
        secondTestOrder.setStatus(Order.OrderStatus.SUCCESS);
        CarOwner carOwner = new CarOwner();
        carOwner.getOrders().add(firstTestOrder);
        carOwner.getOrders().add(secondTestOrder);
        Mockito.when(carOwnerService.getById(1L)).thenReturn(carOwner);
        BigDecimal actual = priceService.countPrice(1L);
        Assertions.assertEquals(BigDecimal.valueOf(2970.0), actual);
        Assertions.assertNotNull(order.getFinalPrice());
    }

    @Test
    void shouldSubtractDiagnosisPriceIfFavorsAreNotOnlyDiagnosis() {
        Order order = new Order();
        Set<Favor> favors = Set.of(new Favor(1L, "Diagnosis", BigDecimal.valueOf(500),
                1L, Favor.FavorStatus.NOT_PAID),
                new Favor(2L, "Change belt", BigDecimal.valueOf(1000),
                        2L, Favor.FavorStatus.NOT_PAID));
        order.setId(1L);
        order.setCarOwnerId(1L);
        order.setFavors(favors);
        order.setStatus(Order.OrderStatus.ACCEPTED);
        Mockito.when(orderService.getById(1L)).thenReturn(order);
        Mockito.when(carOwnerService.getById(1L)).thenReturn(new CarOwner());
        BigDecimal actual = priceService.countPrice(1L);
        Assertions.assertEquals(BigDecimal.valueOf(1000.0), actual);
    }

    @Test
    void shouldReturnFinalPriceIfStatusIsNotAccepted() {
        Order order = new Order();
        order.setStatus(Order.OrderStatus.PROCESSING);
        order.setFinalPrice(BigDecimal.valueOf(10000));
        Mockito.when(orderService.getById(1L)).thenReturn(order);
        Assertions.assertEquals(BigDecimal.valueOf(10000), priceService.countPrice(1L));
    }
}
