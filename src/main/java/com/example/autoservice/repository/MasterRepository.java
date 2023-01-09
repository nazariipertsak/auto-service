package com.example.autoservice.repository;

import com.example.autoservice.model.Master;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    @Query("from Master m LEFT JOIN fetch m.favors WHERE m.id=?1")
    Optional<Master> getByIdWithFavors(Long id);
}
