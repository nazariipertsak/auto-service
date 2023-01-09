package com.example.autoservice.service;

import com.example.autoservice.model.Ware;
import java.util.List;

public interface WareService {
    Ware save(Ware ware);

    List<Ware> findWaresWithIds(List<Long> waresIds);
}
