package com.example.autoservice.controller;

import com.example.autoservice.dto.CarRequestDto;
import com.example.autoservice.dto.CarResponseDto;
import com.example.autoservice.dto.mapper.CarMapper;
import com.example.autoservice.service.CarService;
import com.example.autoservice.model.Car;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;
    private final CarMapper carMapper;

    public CarController(CarService carService, CarMapper carMapper) {
        this.carService = carService;
        this.carMapper = carMapper;
    }

    @PostMapping
    @Operation(summary = "Create new car with brand, model, number and year")
    public CarResponseDto create(@RequestBody CarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        carService.save(car);
        return carMapper.toResponseDto(car);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing car with new brand, model, number and year")
    public CarResponseDto update(@PathVariable Long id,
                                 @RequestBody CarRequestDto requestDto) {
        Car car = carMapper.toModel(requestDto);
        car.setId(id);
        carService.save(car);
        return carMapper.toResponseDto(car);
    }
}
