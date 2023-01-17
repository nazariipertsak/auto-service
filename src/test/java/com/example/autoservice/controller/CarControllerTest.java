package com.example.autoservice.controller;

import com.example.autoservice.dto.CarRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.service.CarService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {
    @MockBean
    private CarService carService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateCar() {
        Car carToSave = new Car();
        carToSave.setBrand("Toyota");
        carToSave.setModel("Camry");
        carToSave.setNumber("11111");
        carToSave.setYear(2016);
        Mockito.when(carService.save(carToSave)).thenReturn(
                new Car(21L, "Toyota", "Camry", "11111", 2016));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarRequestDto(carToSave.getBrand(), carToSave.getModel(),
                        carToSave.getNumber(), carToSave.getYear()))
                .when()
                .post("/cars")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(21))
                .body("model", Matchers.equalTo("Camry"))
                .body("brand", Matchers.equalTo("Toyota"))
                .body("number", Matchers.equalTo("11111"))
                .body("year", Matchers.equalTo(2016));
    }

    @Test
    void shouldUpdateAnExistingCar() {
        Car carToUpdate = new Car();
        carToUpdate.setId(10L);
        carToUpdate.setBrand("Toyota");
        carToUpdate.setModel("Camry");
        carToUpdate.setNumber("11111");
        carToUpdate.setYear(2016);
        Mockito.when(carService.save(carToUpdate)).thenReturn(carToUpdate);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarRequestDto(carToUpdate.getBrand(), carToUpdate.getModel(),
                        carToUpdate.getNumber(), carToUpdate.getYear()))
                .when()
                .put("cars/{id}", 10)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("model", Matchers.equalTo("Camry"))
                .body("brand", Matchers.equalTo("Toyota"))
                .body("number", Matchers.equalTo("11111"))
                .body("year", Matchers.equalTo(2016));
    }
}