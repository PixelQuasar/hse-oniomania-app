package com.example.order.kafka;

import com.example.order.model.OrderStatus;
import com.example.order.model.dto.OrderCreatedDto;
import com.example.order.model.dto.PaymentStatusDto;
import com.example.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {

    private final OrderService orderService;

    @Autowired
    public OrderStatusListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processOrderCreated(PaymentStatusDto paymentStatusDto) {
        System.out.println("Received payment status event: " + paymentStatusDto);

        orderService.updateOrderStatus(paymentStatusDto.getOrderId(), paymentStatusDto.getStatus());
    }
}
