package com.example.autoservice.controller;

import com.example.autoservice.dto.GoodsToOrderDto;
import com.example.autoservice.dto.OrderRequestDto;
import com.example.autoservice.dto.OrderResponseDto;
import com.example.autoservice.dto.StatusDto;
import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.FavorService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.PriceService;
import com.example.autoservice.service.WareService;
import io.swagger.v3.oas.annotations.Operation;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final CarOwnerService carOwnerService;
    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final PriceService priceService;
    private final FavorService favorService;
    private final WareService wareService;

    public OrderController(CarOwnerService carOwnerService, OrderService orderService,
                           OrderMapper orderMapper, PriceService priceService,
                           FavorService favorService, WareService wareService) {
        this.carOwnerService = carOwnerService;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.priceService = priceService;
        this.favorService = favorService;
        this.wareService = wareService;
    }

    @PostMapping
    @Operation(summary = "Create new order with details, car id and car owner id")
    public OrderResponseDto create(@RequestBody OrderRequestDto requestDto) {
        Order order = orderMapper.toModel(requestDto);
        order.setStatus(Order.OrderStatus.ACCEPTED);
        order.setAcquireDate(LocalDate.now());
        CarOwner carOwner = carOwnerService.getById(requestDto.getCarOwnerId());
        orderService.save(order);
        carOwner.getOrders().add(order);
        carOwnerService.save(carOwner);
        return orderMapper.toResponseDto(order);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order with new details, car id and car owner id")
    public OrderResponseDto update(@PathVariable Long id,
                                   @RequestBody OrderRequestDto requestDto) {
        Order order = orderMapper.toModel(requestDto);
        order.setId(id);
        orderService.save(order);
        return orderMapper.toResponseDto(order);
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "Update status of order by id, "
            + "can be accepted, processing, failure, success, paid")
    public OrderResponseDto updateStatus(@PathVariable Long id,
                                         @RequestBody StatusDto status) {
        Order order = orderService.getById(id);
        orderService.updateStatus(order, status.getStatus());
        return orderMapper.toResponseDto(order);
    }

    @GetMapping("/{id}/price")
    @Operation(summary = "Get price of order by id, including discounts")
    public BigDecimal getOrderPrice(@PathVariable Long id) {
        return priceService.countPrice(id);
    }

    @PostMapping("/{id}/goods")
    @Operation(summary = "Add goods to order by id, favors ids and wares ids")
    public OrderResponseDto addGoodsToOrder(@PathVariable Long id,
                                            @RequestBody GoodsToOrderDto goodsToOrderDto) {
        Order order = orderService.getById(id);
        favorService.findFavorsWithIds(goodsToOrderDto.getFavorsIds())
                .forEach(f -> order.getFavors().add(f));
        wareService.findWaresWithIds(goodsToOrderDto.getWaresIds())
                .forEach(w -> order.getWares().add(w));
        return orderMapper.toResponseDto(orderService.save(order));
    }
}
