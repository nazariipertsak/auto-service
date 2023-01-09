package com.example.autoservice.service;

import java.math.BigDecimal;
import java.util.List;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;

public interface MasterService {
    Master save(Master master);

    Master getById(Long id);

    BigDecimal countSalary(List<Favor> favors);
}
