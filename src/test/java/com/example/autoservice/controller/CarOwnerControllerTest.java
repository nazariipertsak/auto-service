package com.example.autoservice.controller;

import com.example.autoservice.dto.CarOwnerRequestDto;
import com.example.autoservice.model.Car;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Order;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.CarService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.util.List;
import java.util.Set;
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
class CarOwnerControllerTest {
    @MockBean
    private CarOwnerService carOwnerService;
    @MockBean
    private CarService carService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateNewCarOwnerWithCars() {
        CarOwner carOwner = new CarOwner();
        carOwner.setFullName("Alexey");
        carOwner.getCars().add(new Car());
        Mockito.when(carService.getById(1L)).thenReturn(new Car());
        Mockito.when(carOwnerService.save(carOwner)).thenReturn(new CarOwner(1L,
                carOwner.getFullName(), carOwner.getCars(), carOwner.getOrders()));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarOwnerRequestDto(carOwner.getFullName(), List.of(1L)))
                .when()
                .post("/car-owners")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Alexey"))
                .body("cars.size()", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateAnExistingCarOwner() {
        CarOwner carOwner = new CarOwner();
        carOwner.setId(1L);
        carOwner.setFullName("Alexey");
        carOwner.getCars().add(new Car());
        Mockito.when(carService.getById(1L)).thenReturn(new Car());
        Mockito.when(carOwnerService.save(carOwner)).thenReturn(carOwner);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new CarOwnerRequestDto(carOwner.getFullName(), List.of(1L)))
                .when()
                .put("/car-owners/{id}", 1)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Alexey"))
                .body("cars.size()", Matchers.equalTo(1));
    }

    @Test
    void shouldReturnCarOwnersOrders() {
        Order firstOrder = new Order();
        firstOrder.setId(1L);
        firstOrder.setStatus(Order.OrderStatus.SUCCESS);
        CarOwner carOwner = new CarOwner();
        carOwner.setId(1L);
        carOwner.setFullName("Alexey");
        carOwner.getOrders().add(firstOrder);
        Mockito.when(carOwnerService.getById(1L)).thenReturn(carOwner);
        RestAssuredMockMvc.given()
                .when()
                .get("/car-owners/{id}/orders", 1L)
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1))
                .body("[0].status", Matchers.equalTo("SUCCESS"));
    }
}
