package com.example.autoservice.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import com.example.autoservice.model.FavorStatus;

@Getter
@Setter
public class FavorResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long masterId;
    private FavorStatus status;
}
