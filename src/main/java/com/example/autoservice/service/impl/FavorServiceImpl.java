package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.repository.FavorRepository;
import com.example.autoservice.service.FavorService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FavorServiceImpl implements FavorService {
    private final FavorRepository favorRepository;

    public FavorServiceImpl(FavorRepository favorRepository) {
        this.favorRepository = favorRepository;
    }

    @Override
    public Favor save(Favor favor) {
        return favorRepository.save(favor);
    }

    @Override
    public Favor getById(Long id) {
        return favorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find favor with id = " + id));
    }

    @Override
    public Favor updateStatus(Favor favor,String status) {
        favor.setStatus(getFavorStatus(status));
        return favorRepository.save(favor);
    }

    @Override
    public List<Favor> findFavorsWithIds(List<Long> favorsIds) {
        return favorRepository.findFavorsByIdIn(favorsIds);
    }

    private Favor.FavorStatus getFavorStatus(String status) {
        return Favor.FavorStatus.valueOf(status.replaceAll(" ", "_").toUpperCase());
    }
}
