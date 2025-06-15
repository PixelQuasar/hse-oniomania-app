package com.example.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@Tag(name = "Account Controller", description = "Endpoints for managing retail app payment")
public class PaymentController {

    @PostMapping("/create")
    @Operation(summary = "Create payment account", description = "Create payment account")
    public ResponseEntity create(@RequestParam Long userId) {

    }

    @PostMapping("/top-up")
    @Operation(summary = "Create payment account", description = "Create payment account")
    public ResponseEntity topUp(@RequestParam BigDecimal amount) {

    }

    @GetMapping("/balance")
    @Operation(summary = "Get account balance", description = "Retrieve the balance of the payment account")
    public ResponseEntity getBalance(@RequestParam Long userId) {
        // Logic to retrieve the balance for the user

    }
}
