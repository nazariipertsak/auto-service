package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.CarOwnerRequestDto;
import com.example.autoservice.dto.CarOwnerResponseDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.service.CarService;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class CarOwnerMapper implements Mapper<CarOwner, CarOwnerRequestDto, CarOwnerResponseDto> {
    private final CarService carService;
    private final CarMapper carMapper;
    private final OrderMapper orderMapper;

    public CarOwnerMapper(CarService carService, CarMapper carMapper, OrderMapper orderMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public CarOwner toModel(CarOwnerRequestDto requestDto) {
        CarOwner carOwner = new CarOwner();
        Set<Car> cars = requestDto.getCarsId().stream()
                .map(carService::getById)
                .collect(Collectors.toSet());
        carOwner.setFullName(requestDto.getFullName());
        carOwner.setCars(cars);
        return carOwner;
    }

    @Override
    public CarOwnerResponseDto toResponseDto(CarOwner carOwner) {
        CarOwnerResponseDto responseDto = new CarOwnerResponseDto();
        responseDto.setId(carOwner.getId());
        responseDto.setFullName(carOwner.getFullName());
        responseDto.setCars(carOwner.getCars().stream()
                .map(carMapper::toResponseDto)
                .toList());
        responseDto.setOrders(carOwner.getOrders().stream()
                .map(orderMapper::toResponseDto)
                .toList());
        return responseDto;
    }
}
