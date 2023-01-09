package com.example.autoservice.service;

import java.math.BigDecimal;

public interface PriceService {
    BigDecimal countPrice(Long orderId);
}
