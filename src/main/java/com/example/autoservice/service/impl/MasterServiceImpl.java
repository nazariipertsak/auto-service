package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;
import com.example.autoservice.repository.MasterRepository;
import com.example.autoservice.service.FavorService;
import com.example.autoservice.service.MasterService;
import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MasterServiceImpl implements MasterService {
    private static final double PERCENTAGE_OF_MASTER_SALARY = 0.4;
    private final FavorService favorService;
    private final MasterRepository masterRepository;

    public MasterServiceImpl(FavorService favorService, MasterRepository masterRepository) {
        this.favorService = favorService;
        this.masterRepository = masterRepository;
    }

    @Override
    public Master save(Master master) {
        return masterRepository.save(master);
    }

    @Override
    public Master getById(Long id) {
        return masterRepository.getByIdWithFavors(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find master with id = " + id));
    }

    @Override
    public BigDecimal countSalary(List<Favor> favors) {
        BigDecimal sum = BigDecimal.valueOf(favors.stream()
                .filter(f -> f.getStatus().equals(Favor.FavorStatus.NOT_PAID))
                .mapToDouble(f -> f.getPrice().doubleValue() * PERCENTAGE_OF_MASTER_SALARY)
                .sum());
        for (Favor favor: favors) {
            favor.setStatus(Favor.FavorStatus.PAID);
            favorService.save(favor);
        }
        return sum;
    }
}
