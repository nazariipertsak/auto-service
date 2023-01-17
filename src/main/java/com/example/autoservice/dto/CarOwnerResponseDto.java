package com.example.autoservice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarOwnerResponseDto {
    private Long id;
    private String fullName;
    private List<CarResponseDto> cars;
    private List<OrderResponseDto> orders;
}
