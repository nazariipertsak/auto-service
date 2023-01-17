package com.example.autoservice.service;

import com.example.autoservice.model.Favor;
import java.util.List;

public interface FavorService {
    Favor save(Favor favor);

    Favor getById(Long id);

    Favor updateStatus(Favor favor, String status);

    List<Favor> findFavorsWithIds(List<Long> favorsIds);
}
