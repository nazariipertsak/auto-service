package com.example.autoservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long carId;
    private Long carOwnerId;
    private String details;
    @OneToMany
    private Set<Favor> favors = new HashSet<>();
    @ManyToMany
    private List<Ware> wares = new ArrayList<>();
    private LocalDate acquireDate;
    private LocalDate endingDate;
    @Enumerated(value = EnumType.STRING)
    private Order.OrderStatus status;
    private BigDecimal finalPrice;

    public Order(Long id, Long carId, Long carOwnerId, String details, LocalDate acquireDate,
                 OrderStatus status) {
        this.id = id;
        this.carId = carId;
        this.carOwnerId = carOwnerId;
        this.details = details;
        this.acquireDate = acquireDate;
        this.status = status;
    }

    public enum OrderStatus {
        ACCEPTED("accepted"),
        PROCESSING("processing"),
        SUCCESS("success"),
        FAILURE("failure"),
        PAID("paid");

        private final String value;

        OrderStatus(String value) {
            this.value = value;
        }
    }
}
