package com.example.gateway.controller;

import com.example.gateway.model.Order;
import com.example.gateway.model.dto.CreateOrderBodyDto;
import com.example.gateway.model.OrderStatus;
import com.example.gateway.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Tag(name = "Gateway - Order Controller", description = "Endpoints for managing orders via gateway")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@Valid @RequestBody CreateOrderBodyDto createOrderBodyDto) {
        System.out.println("Order create request received: " + createOrderBodyDto.toString());
        Order createdOrder = orderService.createOrder(createOrderBodyDto);
        return ResponseEntity.ok(Long.toString(createdOrder.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        System.out.println("Order get request received: " + id);
        return orderService.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<Order>> getOrderList(@PathVariable Long userId) {
        System.out.println("Order list request received: " + userId);
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("/status/{id}")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        System.out.println("Order status request received: " + id);
        return orderService.getOrderStatus(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
