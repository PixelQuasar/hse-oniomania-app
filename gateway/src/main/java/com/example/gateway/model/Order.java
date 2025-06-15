package com.example.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class Order {
    private long id;

    private long userId;

    private OrderStatus status;

    private BigDecimal price;

    private String metadata;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
