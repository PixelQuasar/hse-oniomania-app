package com.example.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Tag(name = "Order Controller", description = "Endpoints for managing retail app orders")
public class OrderController {

    @PostMapping("/create")
    @Operation(summary = "Create order", description = "create order")
    public ResponseEntity getOrderById(@RequestParam Long userId) {

    }

    @GetMapping("/list")
    @Operation(summary = "Get order list by user id", description = "Get order list by user id")
    public ResponseEntity getBalance(@RequestParam Long userId) {
        // Logic to retrieve the balance for the user

    }

    @GetMapping("/status")
    @Operation(summary = "Get order status", description = "Get order status by order id")
    public ResponseEntity getBalance(@RequestParam Long orderId) {
        // Logic to retrieve the balance for the user

    }
}
