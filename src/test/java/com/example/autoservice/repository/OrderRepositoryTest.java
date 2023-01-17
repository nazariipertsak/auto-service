package com.example.autoservice.repository;

import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Ware;
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
class OrderRepositoryTest {
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
    private OrderRepository orderRepository;

    @Test
    @Sql("/scripts/init_order_with_two_wares_and_one_favor.sql")
    void shouldReturnCarOwnerWithTwoCarsAndOneOrder() {
        Order actual = orderRepository.getByIdWithFavorsAndWares(1L).get();
        List<Ware> actualWares = actual.getWares();
        List<Favor> actualFavors = actual.getFavors().stream().toList();
        Assertions.assertEquals(2, actualWares.size());
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("Brakes", actual.getDetails());
        Assertions.assertEquals("Oil", actualWares.get(0).getName());
        Assertions.assertEquals("Belt", actualWares.get(1).getName());
        Assertions.assertEquals(1, actualFavors.size());
        Assertions.assertEquals("Diagnosis", actualFavors.get(0).getName());
    }

    @Test
    @Sql("/scripts/init_order_without_favors_and_wares.sql")
    void shouldReturnOrderWithoutWaresAndFavors() {
        Order actual = orderRepository.getByIdWithFavorsAndWares(2L).get();
        Assertions.assertEquals(0, actual.getWares().size());
        Assertions.assertEquals(0, actual.getFavors().size());
        Assertions.assertEquals(2, actual.getId());
        Assertions.assertEquals("Noise", actual.getDetails());
    }

}