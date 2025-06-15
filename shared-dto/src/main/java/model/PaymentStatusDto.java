package model;

import com.example.order.model.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaymentStatusDto {
    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

    @NotNull(message = "Message cannot be null")
    private String message;
}
