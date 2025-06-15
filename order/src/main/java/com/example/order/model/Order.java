package com.example.order.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "metadata", length = 511)
    private String metadata;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Order(long userId, BigDecimal price, String metadata) {
        this.userId = userId;
        this.status = OrderStatus.CREATED;
        this.price = price;
        this.createdAt = LocalDateTime.now();
        this.metadata = metadata;
    }
}
