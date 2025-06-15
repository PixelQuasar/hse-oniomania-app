package model;


import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderCreatedDto {

    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;
}
