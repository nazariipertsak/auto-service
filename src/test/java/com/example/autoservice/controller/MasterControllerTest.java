package com.example.autoservice.controller;

import com.example.autoservice.dto.MasterRequestDto;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;
import com.example.autoservice.service.MasterService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import java.math.BigDecimal;
import java.util.ArrayList;
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
class MasterControllerTest {
    @MockBean
    private MasterService masterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateMaster() {
        Master master = new Master();
        master.setFullName("Herasym");
        Mockito.when(masterService.save(master)).thenReturn(
                new Master(1L, "Herasym", new ArrayList<>()));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MasterRequestDto("Herasym"))
                .when()
                .post("/masters")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Herasym"));
    }

    @Test
    void shouldUpdateMaster() {
        Master master = new Master();
        master.setId(1L);
        master.setFullName("Herasym");
        Mockito.when(masterService.save(master)).thenReturn(master);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new MasterRequestDto("Herasym"))
                .when()
                .put("/masters/{id}", 1)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(1))
                .body("fullName", Matchers.equalTo("Herasym"));
    }

    @Test
    void shouldReturnMastersFavors() {
        Master master = new Master();
        master.setId(1L);
        master.setFullName("Herasym");
        Favor firstFavor = new Favor();
        firstFavor.setId(1L);
        master.getFavors().add(firstFavor);
        Mockito.when(masterService.getById(1L)).thenReturn(master);
        RestAssuredMockMvc.given()
                .when()
                .get("/masters/{id}/favors", 1)
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(1))
                .body("[0].id", Matchers.equalTo(1));
    }

    @Test
    void shouldCountSalary() {
        Favor firstFavor = new Favor();
        firstFavor.setId(1L);
        firstFavor.setPrice(BigDecimal.valueOf(1000));
        firstFavor.setStatus(Favor.FavorStatus.NOT_PAID);
        Favor secondFavor = new Favor();
        secondFavor.setId(2L);
        secondFavor.setPrice(BigDecimal.valueOf(1000));
        secondFavor.setStatus(Favor.FavorStatus.NOT_PAID);
        Master master = new Master(1L, "Herasym", List.of(firstFavor, secondFavor));
        Mockito.when(masterService.getById(1L)).thenReturn(master);
        Mockito.when(masterService.countSalary(master.getFavors()))
                .thenReturn(BigDecimal.valueOf(800));
        RestAssuredMockMvc.given()
                .when()
                .get("/masters/{id}/salary", 1)
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("800"));
    }

}