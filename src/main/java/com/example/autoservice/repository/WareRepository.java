package com.example.autoservice.repository;

import com.example.autoservice.model.Ware;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WareRepository extends JpaRepository<Ware, Long> {
    List<Ware> findWaresByIdIn(Collection<Long> id);
}
