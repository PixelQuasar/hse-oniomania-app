package com.example.gateway.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class CreateOrderBodyDto {
    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price must be at least 0.00")
    private BigDecimal price;

    private String metadata;
}
