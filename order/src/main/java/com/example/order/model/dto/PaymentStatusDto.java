package com.example.order.model.dto;

import com.example.order.model.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentStatusDto {
    private Long orderId;
    private OrderStatus status;
    private String message;
}
