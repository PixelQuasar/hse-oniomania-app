package com.example.payment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedDto {
    private Long orderId;
    private Long userId;
    private BigDecimal amount;
}
