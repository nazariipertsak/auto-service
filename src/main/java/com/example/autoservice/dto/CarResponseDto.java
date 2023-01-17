package com.example.autoservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponseDto {
    private Long id;
    private String brand;
    private String model;
    private String number;
    private int year;
}
