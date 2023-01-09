package com.example.autoservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarRequestDto {
    private String brand;
    private String model;
    private String number;
    private int year;
}
