package com.example.autoservice.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsToOrderDto {
    private List<Long> favorsIds;
    private List<Long> waresIds;
}
