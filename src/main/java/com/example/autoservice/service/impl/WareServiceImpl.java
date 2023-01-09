package com.example.autoservice.service.impl;

import com.example.autoservice.model.Ware;
import java.util.List;
import org.springframework.stereotype.Service;
import com.example.autoservice.repository.WareRepository;
import com.example.autoservice.service.WareService;

@Service
public class WareServiceImpl implements WareService {
    private final WareRepository repository;

    public WareServiceImpl(WareRepository repository) {
        this.repository = repository;
    }

    @Override
    public Ware save(Ware ware) {
        return repository.save(ware);
    }

    @Override
    public List<Ware> findWaresWithIds(List<Long> waresIds) {
        return repository.findWaresByIdIn(waresIds);
    }
}
