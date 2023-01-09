package com.example.autoservice.repository;

import com.example.autoservice.model.CarOwner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CarOwnerRepository extends JpaRepository<CarOwner, Long> {
    @Query("from CarOwner co LEFT JOIN fetch co.cars LEFT JOIN FETCH co.orders WHERE co.id=?1")
    Optional<CarOwner> getByIdWithCarsAndOrders(Long id);
}
