package com.example.autoservice.service;

import com.example.autoservice.model.Car;

public interface CarService {
    Car save(Car car);

    Car getById(Long id);
}
