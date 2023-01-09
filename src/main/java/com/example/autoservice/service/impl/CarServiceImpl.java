package com.example.autoservice.service.impl;

import com.example.autoservice.service.CarService;
import com.example.autoservice.model.Car;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.example.autoservice.repository.CarRepository;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find car with id = " + id));
    }
}
