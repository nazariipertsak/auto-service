package com.example.autoservice.controller;

import com.example.autoservice.dto.FavorRequestDto;
import com.example.autoservice.dto.StatusDto;
import com.example.autoservice.model.Favor;
import com.example.autoservice.model.Master;
import com.example.autoservice.service.FavorService;
import com.example.autoservice.service.MasterService;
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
class FavorControllerTest {
    @MockBean
    private FavorService favorService;
    @MockBean
    private MasterService masterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateFavor() {
        Favor favorToSave = new Favor();
        favorToSave.setName("Diagnosis");
        favorToSave.setMasterId(1L);
        favorToSave.setPrice(BigDecimal.valueOf(500));
        favorToSave.setStatus(Favor.FavorStatus.NOT_PAID);
        Mockito.when(favorService.save(favorToSave)).thenReturn(new Favor(11L, favorToSave.getName(),
                favorToSave.getPrice(), favorToSave.getMasterId(), favorToSave.getStatus()));
        Mockito.when(masterService.getById(1L)).thenReturn(new Master());
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new FavorRequestDto(favorToSave.getPrice(), favorToSave.getName(),
                        favorToSave.getMasterId()))
                .when()
                .post("/favors")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(11))
                .body("name", Matchers.equalTo("Diagnosis"))
                .body("price", Matchers.equalTo(500))
                .body("masterId", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateAnExistingFavor() {
        Favor favorToUpdate = new Favor();
        favorToUpdate.setName("Diagnosis");
        favorToUpdate.setMasterId(1L);
        favorToUpdate.setPrice(BigDecimal.valueOf(500));
        favorToUpdate.setId(10L);
        Mockito.when(favorService.save(favorToUpdate)).thenReturn(favorToUpdate);
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new FavorRequestDto(favorToUpdate.getPrice(), favorToUpdate.getName(),
                        favorToUpdate.getMasterId()))
                .when()
                .put("favors/{id}", 10)
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("name", Matchers.equalTo("Diagnosis"))
                .body("price", Matchers.equalTo(500))
                .body("masterId", Matchers.equalTo(1));
    }

    @Test
    void shouldUpdateFavorStatus() {
        Favor favorToUpdate = new Favor();
        favorToUpdate.setName("Diagnosis");
        favorToUpdate.setMasterId(1L);
        favorToUpdate.setPrice(BigDecimal.valueOf(500));
        favorToUpdate.setId(10L);
        favorToUpdate.setStatus(Favor.FavorStatus.NOT_PAID);
        String newStatus = "paid";
        Mockito.when(favorService.getById(10L)).thenReturn(favorToUpdate);
        Mockito.when(favorService.updateStatus(favorToUpdate, newStatus)).thenReturn(
                new Favor(favorToUpdate.getId(), favorToUpdate.getName(),
                        favorToUpdate.getPrice(), favorToUpdate.getMasterId(),
                        Favor.FavorStatus.PAID));
        RestAssuredMockMvc.given()
                .contentType(ContentType.JSON)
                .body(new StatusDto(newStatus))
                .when()
                .put("favors/{id}/status", favorToUpdate.getId())
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("name", Matchers.equalTo("Diagnosis"))
                .body("price", Matchers.equalTo(500))
                .body("masterId", Matchers.equalTo(1))
                .body("status", Matchers.equalTo("PAID"));
    }
}
