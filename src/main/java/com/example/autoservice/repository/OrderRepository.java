package com.example.autoservice.repository;

import com.example.autoservice.model.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("from Order o LEFT JOIN fetch o.favors LEFT JOIN FETCH o.wares WHERE o.id=?1")
    Optional<Order> getByIdWithCarsAndOrders(Long id);
}
