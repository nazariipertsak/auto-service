package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.repository.MasterRepository;
import com.example.autoservice.service.FavorService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MasterServiceImplTest {
    @InjectMocks
    private MasterServiceImpl masterService;

    @Mock
    private MasterRepository masterRepository;
    @Mock
    private FavorService favorService;

    @Test
    void shouldCountSalaryAndSkipAlreadyPaidFavors() {
        Favor firstFavor = new Favor(1L, "Diagnosis", BigDecimal.valueOf(500),
                1L, Favor.FavorStatus.NOT_PAID);
        Favor secondFavor = new Favor(2L, "Diagnosis", BigDecimal.valueOf(500),
                2L, Favor.FavorStatus.PAID);
        Favor thirdFavor = new Favor(3L, "Diagnosis", BigDecimal.valueOf(500),
                3L, Favor.FavorStatus.NOT_PAID);
        List<Favor> testFavors = List.of(firstFavor, secondFavor, thirdFavor);
        BigDecimal actual = masterService.countSalary(testFavors);
        Assertions.assertEquals(BigDecimal.valueOf(400.0), actual);
    }

    @Test
    void shouldChangeStatusOfFavor() {
        Favor firstFavor = new Favor(1L, "Diagnosis", BigDecimal.valueOf(500),
                1L, Favor.FavorStatus.NOT_PAID);
        Favor secondFavor = new Favor(2L, "Diagnosis", BigDecimal.valueOf(500),
                2L, Favor.FavorStatus.PAID);
        List<Favor> testFavors = List.of(firstFavor, secondFavor);
        masterService.countSalary(testFavors);
        Assertions.assertEquals(Favor.FavorStatus.PAID, testFavors.get(0).getStatus());
        Assertions.assertEquals(Favor.FavorStatus.PAID, testFavors.get(1).getStatus());
    }
}