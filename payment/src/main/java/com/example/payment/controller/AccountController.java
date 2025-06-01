package com.example.payment.controller;

import com.example.payment.model.Account;
import com.example.payment.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Controller", description = "Endpoints for managing accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @Operation(summary = "Create Account", description = "Creates a new account for the given user ID")
    public ResponseEntity<String> createAccount(@RequestParam Long userId) {
        accountService.createAccount(userId);
        return ResponseEntity.ok("Account created successfully.");
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get Account by User ID", description = "Retrieves account details for the given user ID")
    public ResponseEntity<Account> getAccountByUserId(@PathVariable Long userId) {
        return accountService.getAccountByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/top-up")
    @Operation(summary = "Top Up Account", description = "Adds the specified amount to the user's account balance")
    public ResponseEntity<Account> topUpAccount(
            @RequestParam Long userId,
            @RequestParam BigDecimal amount
    ) {
        Account updatedAccount = accountService.addToBalance(userId, amount);
        return ResponseEntity.ok(updatedAccount);
    }
}
