package com.myproject.rewards.controller;

import com.myproject.rewards.model.Transaction;
import com.myproject.rewards.service.RewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RewardsController {

    @Autowired
    private RewardsService rewardsService;

    @PostMapping("/transactions")
    public ResponseEntity<String> recordTransaction(@RequestBody Transaction transaction) {
        rewardsService.recordTransaction(transaction);
        return new ResponseEntity<>("Transaction made recorded", HttpStatus.CREATED);
    }

    @GetMapping("/points/{customerId}/{year}")
    public ResponseEntity<Map<LocalDate, Integer>> getPointsByMonth(@PathVariable Long customerId, @PathVariable int year) {
        Map<LocalDate, Integer> points = rewardsService.getMonthlyPoints(customerId, Year.of(year));
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @PutMapping("/transactions/{transactionId}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable long transactionId, @RequestBody Transaction transaction) {
        Transaction updatedTransaction = rewardsService.updateTransaction(transactionId, transaction);
        if(updatedTransaction == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

}
