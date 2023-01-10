package com.example.autoservice.service;

import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;
import java.math.BigDecimal;
import java.util.List;

public interface MasterService {
    Master save(Master master);

    Master getById(Long id);

    BigDecimal countSalary(List<Favor> favors);
}
