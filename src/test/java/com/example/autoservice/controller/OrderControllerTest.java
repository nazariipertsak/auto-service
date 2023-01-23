package com.example.autoservice.controller;

import com.example.autoservice.dto.GoodsToOrderDto;
import com.example.autoservice.dto.OrderRequestDto;
import com.example.autoservice.dto.StatusDto;
import com.example.autoservice.model.CarOwner;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Order;
import com.example.autoservice.model.Ware;
import com.example.autoservice.service.CarOwnerService;
import com.example.autoservice.service.FavorService;
import com.example.autoservice.service.OrderService;
import com.example.autoservice.service.PriceService;
import com.example.autoservice.service.WareService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
class OrderControllerTest {
    @MockBean
    private CarOwnerService carOwnerService;
    @MockBean
    private OrderService orderService;
    @MockBean
    private PriceService priceService;
    @MockBean
    private FavorService favorService;
    @MockBean
    private WareService wareService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateOrderWithDateAndStatus() {
        Order order = new Order();
        order.setAcquireDate(LocalDate.now());
        order.setCarId(1L);
        order.setCarOwnerId(1L);
        order.setDetails("Noise");
        order.setStatus(Order.OrderStatus.ACCEPTED);
        Mockito.when(orderService.save(order)).thenReturn(new Order(1L, order.getCarId(),
                order.getCarOwnerId(), order.getDetails(), order.getAcquireDate(),
                order.getStatus()));
        Mockito.when(carOwnerService.getById(1L)).thenReturn(new CarOwner());
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(order.getCarId(), order.getCarOwnerId(),
                        order.getDetails()))
                .when()
                .post("/orders")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("carId", Matchers.equalTo(1))
                .body("carOwnerId", Matchers.equalTo(1))
                .body("details", Matchers.equalTo("Noise"))
                .body("status", Matchers.equalTo("ACCEPTED"))
                .body("acquireDate", Matchers.equalTo(order.getAcquireDate().toString()));
    }

    @Test
    void shouldUpdateAnExistingOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCarId(1L);
        order.setCarOwnerId(1L);
        order.setDetails("Noise");
        Mockito.when(orderService.save(order)).thenReturn(order);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new OrderRequestDto(order.getCarId(), order.getCarOwnerId(),
                        order.getDetails()))
                .when()
                .put("/orders/{id}", 1)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("carId", Matchers.equalTo(1))
                .body("carOwnerId", Matchers.equalTo(1))
                .body("details", Matchers.equalTo("Noise"));
    }

    @Test
    void shouldUpdateOrderStatus() {
        Order order = new Order();
        order.setId(10L);
        order.setAcquireDate(LocalDate.now());
        order.setCarId(1L);
        order.setCarOwnerId(1L);
        order.setDetails("Noise");
        order.setStatus(Order.OrderStatus.ACCEPTED);
        String newStatus = "processing";
        Mockito.when(orderService.getById(10L)).thenReturn(order);
        Mockito.when(orderService.updateStatus(order, newStatus)).thenReturn(new Order(10L,
                order.getCarId(), order.getCarOwnerId(), order.getDetails(),
                order.getAcquireDate(), Order.OrderStatus.PROCESSING));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new StatusDto(newStatus))
                .when()
                .put("/orders/{id}/status", 10)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("carId", Matchers.equalTo(1))
                .body("carOwnerId", Matchers.equalTo(1))
                .body("details", Matchers.equalTo("Noise"))
                .body("acquireDate", Matchers.equalTo(order.getAcquireDate().toString()))
                .body("status", Matchers.equalTo("PROCESSING"));
    }

    @Test
    void shouldReturnOrderPrice() {
        Mockito.when(priceService.countPrice(1L)).thenReturn(BigDecimal.valueOf(1000));
        RestAssuredMockMvc.given()
                .when()
                .get("/orders/{id}/price", 1)
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("1000"));
    }

    @Test
    void shouldAddWaresAndFavorsToOrder() {
        Order order = new Order();
        order.setId(10L);
        order.setAcquireDate(LocalDate.now());
        order.setCarId(1L);
        order.setCarOwnerId(1L);
        order.setDetails("Noise");
        order.setStatus(Order.OrderStatus.ACCEPTED);
        Mockito.when(orderService.getById(10L)).thenReturn(order);
        Order returnOrder = new Order();
        returnOrder.setId(10L);
        returnOrder.setAcquireDate(LocalDate.now());
        returnOrder.setCarId(1L);
        returnOrder.setCarOwnerId(1L);
        returnOrder.setDetails("Noise");
        returnOrder.setStatus(Order.OrderStatus.ACCEPTED);
        Ware firstWare = new Ware();
        Favor favor = new Favor();
        returnOrder.getWares().add(firstWare);
        returnOrder.getWares().add(firstWare);
        returnOrder.getFavors().add(favor);
        Mockito.when(wareService.findWaresWithIds(List.of(1L, 1L)))
                .thenReturn(List.of(new Ware(), new Ware()));
        Mockito.when(favorService.findFavorsWithIds(List.of(1L))).thenReturn(List.of(favor));
        Mockito.when(orderService.save(returnOrder)).thenReturn(returnOrder);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new GoodsToOrderDto(List.of(1L), List.of(1L, 1L)))
                .when()
                .post("/orders/{id}/goods", 10)
                .then()
                .body("id", Matchers.equalTo(10))
                .body("carId", Matchers.equalTo(1))
                .body("carOwnerId", Matchers.equalTo(1))
                .body("details", Matchers.equalTo("Noise"))
                .body("status", Matchers.equalTo("ACCEPTED"))
                .body("acquireDate", Matchers.equalTo(order.getAcquireDate().toString()))
                .body("favors.size()", Matchers.equalTo(1))
                .body("wares.size()", Matchers.equalTo(2));
    }
}
