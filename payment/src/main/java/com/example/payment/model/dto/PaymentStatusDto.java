package com.example.payment.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentStatusDto {
    private OrderStatus status;
    private Long orderId;
}
