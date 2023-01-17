package com.example.autoservice.service.impl;

import com.example.autoservice.model.Favor;
import com.example.autoservice.repository.FavorRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FavorServiceImplTest {
    @InjectMocks
    private FavorServiceImpl favorService;

    @Mock
    private FavorRepository favorRepository;

    @Test
    void shouldReturnFavorWithOtherStatus() {
        Favor favor = new Favor(1L, "Diagnosis", BigDecimal.valueOf(500),
                1L, Favor.FavorStatus.NOT_PAID);
        String statusPaid = "paid";
        Mockito.when(favorRepository.save(favor)).thenReturn(favor);
        favorService.updateStatus(favor, statusPaid);
        Assertions.assertEquals(Favor.FavorStatus.PAID, favor.getStatus());
    }

}