package com.example.payment.kafka;

import com.example.payment.model.dto.OrderCreatedDto;
import com.example.payment.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final AccountService accountService;

    @Autowired
    public OrderCreatedListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processOrderCreated(OrderCreatedDto orderCreatedDto) {
        System.out.println("Received order created event: " + orderCreatedDto);

        accountService.subtractFromBalance(
                orderCreatedDto.getUserId(),
                orderCreatedDto.getAmount(),
                orderCreatedDto.getOrderId()
        );
    }
}
