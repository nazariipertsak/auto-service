package com.example.autoservice.dto;

import com.example.autoservice.model.Favor;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavorResponseDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long masterId;
    private Favor.FavorStatus status;
}
