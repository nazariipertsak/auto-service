package com.example.autoservice.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavorRequestDto {
    private BigDecimal price;
    private String name;
    private Long masterId;
}
