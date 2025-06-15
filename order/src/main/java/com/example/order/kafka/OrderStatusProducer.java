package com.example.order.kafka;

import com.example.order.model.OrderStatus;
import com.example.order.model.dto.PaymentStatusDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderStatusProducer {

    private final KafkaTemplate<String, PaymentStatusDto> kafkaTemplate;

    @Value("${spring.kafka.payment_status_topic}")
    private String paymentStatusTopic;

    @Autowired
    public OrderStatusProducer(KafkaTemplate<String, PaymentStatusDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void orderShipped(Long orderId, String message) {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        paymentStatusDto.setStatus(OrderStatus.SHIPPED);
        paymentStatusDto.setOrderId(orderId);
        paymentStatusDto.setMessage(message);
        kafkaTemplate.send(paymentStatusTopic, paymentStatusDto);
    }

    public void orderDelivered(Long orderId, String message) {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        paymentStatusDto.setStatus(OrderStatus.DELIVERED);
        paymentStatusDto.setOrderId(orderId);
        paymentStatusDto.setMessage(message);
        kafkaTemplate.send(paymentStatusTopic, paymentStatusDto);
    }

    public void orderProcessing(Long orderId, String message) {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        paymentStatusDto.setStatus(OrderStatus.PAYMENT_PROCESSING);
        paymentStatusDto.setOrderId(orderId);
        paymentStatusDto.setMessage(message);
        kafkaTemplate.send(paymentStatusTopic, paymentStatusDto);
    }
}
