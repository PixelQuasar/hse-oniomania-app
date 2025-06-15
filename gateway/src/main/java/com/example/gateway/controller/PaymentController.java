package com.example.gateway.controller;

import com.example.gateway.model.Account;
import com.example.gateway.service.PaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Gateway - Account Controller", description = "Endpoints for managing accounts via gateway")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestParam Long userId) {
        System.out.println("Account create request received: " + userId);
        paymentService.createAccount(userId);
        return ResponseEntity.ok("Account creation request sent.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable Long userId) {
        System.out.println("Account get request received: " + userId);
        return paymentService.getAccountByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/top-up")
    public ResponseEntity<Account> topUpAccount(
            @RequestParam Long userId,
            @RequestParam BigDecimal amount
    ) {
        Account updatedAccount = paymentService.topUpAccount(userId, amount);
        System.out.println("Account top-up request received: " + userId + " " + amount);
        return ResponseEntity.ok(updatedAccount);
    }
}
