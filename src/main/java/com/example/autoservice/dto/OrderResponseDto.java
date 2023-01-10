package com.example.autoservice.dto;

import com.example.autoservice.model.Order;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private Long carId;
    private Long carOwnerId;
    private String details;
    private List<FavorResponseDto> favors;
    private List<WareResponseDto> wares;
    private LocalDate acquireDate;
    private LocalDate endingDate;
    private Order.OrderStatus status;
}
