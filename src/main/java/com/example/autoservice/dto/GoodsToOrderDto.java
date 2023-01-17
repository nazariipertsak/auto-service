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
public class GoodsToOrderDto {
    private List<Long> favorsIds;
    private List<Long> waresIds;
}
