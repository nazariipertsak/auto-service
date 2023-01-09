package com.example.autoservice.controller;

import com.example.autoservice.dto.CarOwnerRequestDto;
import com.example.autoservice.dto.CarOwnerResponseDto;
import com.example.autoservice.dto.OrderResponseDto;
import com.example.autoservice.dto.mapper.CarOwnerMapper;
import com.example.autoservice.dto.mapper.OrderMapper;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.service.CarOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/car-owners")
public class CarOwnerController {
    private final CarOwnerService carOwnerService;
    private final CarOwnerMapper carOwnerMapper;
    private final OrderMapper orderMapper;

    public CarOwnerController(CarOwnerService carOwnerService, CarOwnerMapper carOwnerMapper,
                              OrderMapper orderMapper) {
        this.carOwnerService = carOwnerService;
        this.carOwnerMapper = carOwnerMapper;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @Operation(summary = "Create new car owner with full name and cars ids")
    public CarOwnerResponseDto create(@RequestBody CarOwnerRequestDto requestDto) {
        CarOwner carOwner = carOwnerMapper.toModel(requestDto);
        carOwnerService.save(carOwner);
        return carOwnerMapper.toResponseDto(carOwner);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update car owner by id, with new full name and new cars ids")
    public CarOwnerResponseDto update(@PathVariable Long id,
                                      @RequestBody CarOwnerRequestDto requestDto) {
        CarOwner carOwner = carOwnerMapper.toModel(requestDto);
        carOwner.setId(id);
        carOwnerService.save(carOwner);
        return carOwnerMapper.toResponseDto(carOwner);
    }

    @GetMapping("/{id}/orders")
    @Operation(summary = "Get list of orders of car owner by id")
    public List<OrderResponseDto> getCarOwnersOrders(@PathVariable Long id) {
        return carOwnerService.getById(id).getOrders().stream()
                .map(orderMapper::toResponseDto)
                .toList();
    }
}
