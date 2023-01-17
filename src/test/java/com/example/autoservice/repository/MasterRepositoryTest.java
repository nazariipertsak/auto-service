package com.example.autoservice.repository;

import com.example.autoservice.model.Master;
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
class MasterRepositoryTest {
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
    private MasterRepository masterRepository;

    @Test
    @Sql("/scripts/init_master_with_two_favors.sql")
    void shouldReturnMasterWithTwoFavors() {
        Master actual = masterRepository.getByIdWithFavors(1L).get();
        Assertions.assertEquals(2, actual.getFavors().size());
        Assertions.assertEquals(1, actual.getId());
        Assertions.assertEquals("Sergey", actual.getFullName());
        Assertions.assertEquals("Change belt", actual.getFavors().get(0).getName());
        Assertions.assertEquals("Change oil", actual.getFavors().get(1).getName());
    }

    @Test
    @Sql("/scripts/init_master_without_favors.sql")
    void shouldReturnMasterWithoutFavors() {
        Master actual = masterRepository.getByIdWithFavors(2L).get();
        Assertions.assertEquals(0, actual.getFavors().size());
        Assertions.assertEquals(2, actual.getId());
        Assertions.assertEquals("Alexey", actual.getFullName());
    }
}