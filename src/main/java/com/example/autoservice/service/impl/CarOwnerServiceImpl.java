package com.example.autoservice.service.impl;

import com.example.autoservice.model.CarOwner;
import com.example.autoservice.repository.CarOwnerRepository;
import com.example.autoservice.service.CarOwnerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CarOwnerServiceImpl implements CarOwnerService {
    private final CarOwnerRepository carOwnerRepository;

    public CarOwnerServiceImpl(CarOwnerRepository carOwnerRepository) {
        this.carOwnerRepository = carOwnerRepository;
    }

    @Override
    public CarOwner save(CarOwner carOwner) {
        return carOwnerRepository.save(carOwner);
    }

    @Override
    public CarOwner getById(Long id) {
        return carOwnerRepository.getByIdWithCarsAndOrders(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find car owner with id = " + id));
    }
}
