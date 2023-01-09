package com.example.autoservice.repository;

import com.example.autoservice.model.Favor;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavorRepository extends JpaRepository<Favor, Long> {
    List<Favor> findFavorsByIdIn(Collection<Long> id);
}
