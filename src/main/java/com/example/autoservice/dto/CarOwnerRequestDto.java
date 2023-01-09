package com.example.autoservice.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarOwnerRequestDto {
    private String fullName;
    private List<Long> carsId;
}
