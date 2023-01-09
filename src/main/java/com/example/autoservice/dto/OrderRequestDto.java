package com.example.autoservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequestDto {
    private Long carId;
    private Long carOwnerId;
    private String details;
}
