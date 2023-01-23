package com.example.autoservice.controller;

import com.example.autoservice.dto.WareRequestDto;
import com.example.autoservice.model.Ware;
import com.example.autoservice.service.WareService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
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
class WareControllerTest {
    @MockBean
    private WareService wareService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateWare() {
        Ware wareToSave = new Ware();
        wareToSave.setName("Oil");
        wareToSave.setPrice(BigDecimal.valueOf(1000));
        Mockito.when(wareService.save(wareToSave)).thenReturn(
                new Ware(11L, "Oil", BigDecimal.valueOf(1000)));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new WareRequestDto(wareToSave.getName(), wareToSave.getPrice()))
                .when()
                .post("/wares")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(11))
                .body("name", Matchers.equalTo("Oil"))
                .body("price", Matchers.equalTo(1000));
    }

    @Test
    void shouldUpdateAnExistingWare() {
        Ware wareToUpdate = new Ware();
        wareToUpdate.setName("Oil");
        wareToUpdate.setPrice(BigDecimal.valueOf(1000));
        int newId = 10;
        Mockito.when(wareService.save(wareToUpdate)).thenReturn(wareToUpdate);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new WareRequestDto(wareToUpdate.getName(), wareToUpdate.getPrice()))
                .when()
                .put("wares/{id}", newId)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("name", Matchers.equalTo("Oil"))
                .body("price", Matchers.equalTo(1000));
    }
}