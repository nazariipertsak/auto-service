package com.example.autoservice.dto.mapper;

import com.example.autoservice.dto.CarRequestDto;
import com.example.autoservice.dto.CarResponseDto;
import com.example.autoservice.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements Mapper<Car, CarRequestDto, CarResponseDto> {
    @Override
    public Car toModel(CarRequestDto requestDto) {
        Car car = new Car();
        car.setBrand(requestDto.getBrand());
        car.setModel(requestDto.getModel());
        car.setNumber(requestDto.getNumber());
        car.setYear(requestDto.getYear());
        return car;
    }

    @Override
    public CarResponseDto toResponseDto(Car car) {
        CarResponseDto carResponseDto = new CarResponseDto();
        carResponseDto.setId(car.getId());
        carResponseDto.setBrand(car.getBrand());
        carResponseDto.setModel(car.getModel());
        carResponseDto.setNumber(car.getNumber());
        carResponseDto.setYear(car.getYear());
        return carResponseDto;
    }
}
