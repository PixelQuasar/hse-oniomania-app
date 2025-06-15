package com.example.gateway.kafka;

import com.example.gateway.model.Order;
import com.example.gateway.model.dto.PaymentStatusDto;
import com.example.gateway.service.OrderService;
import com.example.gateway.websocket.StatusUpdateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusListener {

    private final StatusUpdateHandler statusUpdateHandler;

    private final OrderService orderService;

    @Autowired
    public OrderStatusListener(StatusUpdateHandler statusUpdateHandler, OrderService orderService) {
        this.statusUpdateHandler = statusUpdateHandler;
        this.orderService = orderService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processOrderCreated(PaymentStatusDto paymentStatusDto) {
        System.out.println("Received payment status event: " + paymentStatusDto.getStatus());

        Order order = orderService.getOrderById(paymentStatusDto.getOrderId()).orElseThrow();

        statusUpdateHandler.sendStatus(order.getUserId(), paymentStatusDto.getStatus());
    }
}
