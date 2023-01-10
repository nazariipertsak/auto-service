package com.example.autoservice.dto;

import com.example.autoservice.model.Favor;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavorResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long masterId;
    private Favor.FavorStatus status;
}
