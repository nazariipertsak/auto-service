package com.example.autoservice.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WareResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
