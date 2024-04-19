package com.myproject.rewards.controller;

import com.myproject.rewards.model.Transaction;
import com.myproject.rewards.service.RewardsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.time.Year;
import java.util.Map;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class RewardsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RewardsService rewardsService;

    @Test
    void testRecordTransaction() throws Exception {
        mockMvc.perform(post("/api/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"customerId\":1,\"amount\":120.0,\"date\":\"2024-04-18\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Transaction made recorded"));
        verify(rewardsService, times(1)).recordTransaction(any(Transaction.class));
    }

    @Test
    void testGetPointsByMonth() throws Exception {
        when(rewardsService.getMonthlyPoints(anyLong(), any())).thenReturn(Map.of(LocalDate.of(2024, 4, 1), 90));
        mockMvc.perform(get("/api/points/1/2024")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"2024-04-01\":90}"));
        verify(rewardsService, times(1)).getMonthlyPoints(1L, Year.of(2024));
    }
}
