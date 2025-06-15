package com.example.order.kafka;

import com.example.order.model.dto.OrderCreatedDto;
import com.example.order.model.dto.PaymentStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderCreatedProducer {
    private final KafkaTemplate<String, OrderCreatedDto> kafkaTemplate;

    @Value("${spring.kafka.order_created_topic}")
    private String paymentStatusTopic;

    @Autowired
    public OrderCreatedProducer(KafkaTemplate<String, OrderCreatedDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void createOrder(long userId, long orderId, BigDecimal amount) {
        OrderCreatedDto orderCreatedDto = new OrderCreatedDto();
        orderCreatedDto.setUserId(userId);
        orderCreatedDto.setOrderId(orderId);
        orderCreatedDto.setAmount(amount);

        kafkaTemplate.send(paymentStatusTopic, orderCreatedDto);
    }
}
