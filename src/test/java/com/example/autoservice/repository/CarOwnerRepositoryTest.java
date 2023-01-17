package com.example.autoservice.repository;

import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarOwnerRepositoryTest {
    @Container
    static PostgreSQLContainer<?> database = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("auto-service")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void setDatasourceProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add("spring.datasource.url", database::getJdbcUrl);
        propertyRegistry.add("spring.datasource.username", database::getUsername);
        propertyRegistry.add("spring.datasource.password", database::getPassword);
    }

    @Autowired
    private CarOwnerRepository carOwnerRepository;

    @Test
    @Sql("/scripts/init_car_owner_with_two_cars_and_one_order.sql")
    void shouldReturnCarOwnerWithTwoCarsAndOneOrder() {
        CarOwner actual = carOwnerRepository.getByIdWithCarsAndOrders(1L).get();
        List<Car> actualCars = actual.getCars().stream().toList();
        List<Order> actualOrders = actual.getOrders().stream().toList();
        Assertions.assertEquals(2, actualCars.size());
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("Alexey", actual.getFullName());
        Assertions.assertEquals("Audi", actualCars.get(0).getBrand());
        Assertions.assertEquals("Toyota", actualCars.get(1).getBrand());
        Assertions.assertEquals(1, actualOrders.size());
        Assertions.assertEquals("Noise", actualOrders.get(0).getDetails());
    }

    @Test
    @Sql("/scripts/init_car_owner_without_cars_and_orders.sql")
    void shouldReturnCarWithoutCarsAndOrders() {
        CarOwner actual = carOwnerRepository.getByIdWithCarsAndOrders(2L).get();
        Assertions.assertEquals(0, actual.getCars().size());
        Assertions.assertEquals(0, actual.getOrders().size());
        Assertions.assertEquals(2, actual.getId());
        Assertions.assertEquals("Sergey", actual.getFullName());
    }
}