package com.myproject.rewards.service;

import com.myproject.rewards.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RewardsServiceTest {
    private RewardsService service;
    @BeforeEach
    void setUp() {
        service = new RewardsService();
    }
    @Test
    public void testCalculateRewards() {
        assertEquals(90, service.calculateRewards(120.0));
        assertEquals(0, service.calculateRewards(49.0));
        assertEquals(1, service.calculateRewards(51.0));
    }
    @Test
    public void testUpdateTransaction() {
        Transaction t1 = new Transaction(1L, 100.0, LocalDate.now());
        service.recordTransaction(t1);
        Transaction transaction = new Transaction(1L, 150.0, LocalDate.now());
        Transaction updatedTransaction = service.updateTransaction(t1.getId(), transaction);

        assertNotNull(updatedTransaction);
        assertEquals(150.0, updatedTransaction.getAmount());
    }
}
