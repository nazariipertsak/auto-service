package com.example.autoservice.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarOwnerResponseDto {
    private Long id;
    private String fullName;
    private List<CarResponseDto> cars;
    private List<OrderResponseDto> orders;
}
