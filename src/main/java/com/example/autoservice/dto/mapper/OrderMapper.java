package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.OrderRequestDto;
import com.example.autoservice.dto.OrderResponseDto;
import com.example.autoservice.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper implements Mapper<Order, OrderRequestDto, OrderResponseDto> {
    private final FavorMapper favorMapper;
    private final WareMapper wareMapper;

    public OrderMapper(FavorMapper favorMapper, WareMapper wareMapper) {
        this.favorMapper = favorMapper;
        this.wareMapper = wareMapper;
    }

    @Override
    public Order toModel(OrderRequestDto requestDto) {
        Order order = new Order();
        order.setDetails(requestDto.getDetails());
        order.setCarId(requestDto.getCarId());
        order.setCarOwnerId(requestDto.getCarOwnerId());
        return order;
    }

    @Override
    public OrderResponseDto toResponseDto(Order order) {
        OrderResponseDto responseDto = new OrderResponseDto();
        responseDto.setId(order.getId());
        responseDto.setCarId(order.getCarId());
        responseDto.setCarOwnerId(order.getCarOwnerId());
        responseDto.setDetails(order.getDetails());
        responseDto.setAcquireDate(order.getAcquireDate());
        responseDto.setEndingDate(order.getEndingDate());
        responseDto.setStatus(order.getStatus());
        responseDto.setFavors(order.getFavors().stream()
                .map(favorMapper::toResponseDto)
                .toList());
        responseDto.setWares(order.getWares().stream()
                .map(wareMapper::toResponseDto)
                .toList());
        return responseDto;
    }
}
