package com.example.order.controller;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.model.dto.CreateOrderBodyDto;
import com.example.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "Order Controller", description = "Endpoints for managing orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create Order", description = "Creates a new order")
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderBodyDto createOrderBodyDto) {
        Order order = orderService.createOrder(
                createOrderBodyDto.getUserId(),
                createOrderBodyDto.getPrice(),
                createOrderBodyDto.getMetadata()
        );

        return ResponseEntity.ok(Long.toString(order.getId()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Order by ID", description = "Retrieves order details for the given ID")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list/{userId}")
    @Operation(summary = "Get Order list by user id", description = "Retrieves order details for the given ID")
    public ResponseEntity<List<Order>> getOrderList(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/status/{id}")
    @Operation(summary = "Get Order status", description = "Retrieves order details for the given ID")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        return orderService.get(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
