package com.example.autoservice.repository;

import com.example.autoservice.model.Favor;
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
class FavorRepositoryTest {
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
    private FavorRepository favorRepository;

    @Test
    @Sql("/scripts/init_four_favors.sql")
    void shouldReturnFavorsWithIdInList() {
        List<Favor> actual = favorRepository.findFavorsByIdIn(List.of(2L, 4L));
        Assertions.assertEquals(2 ,actual.size());
        Assertions.assertEquals("Change belt", actual.get(0).getName());
        Assertions.assertEquals(Favor.FavorStatus.NOT_PAID, actual.get(0).getStatus());
        Assertions.assertEquals("Change tires", actual.get(1).getName());
        Assertions.assertEquals(Favor.FavorStatus.PAID, actual.get(1).getStatus());
    }
}