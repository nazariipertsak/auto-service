package com.example.autoservice.service;

import com.example.autoservice.model.CarOwner;

public interface CarOwnerService {
    CarOwner save(CarOwner carOwner);

    CarOwner getById(Long id);
}
