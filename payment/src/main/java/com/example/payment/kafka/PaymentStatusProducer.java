package com.example.payment.kafka;

import com.example.payment.model.dto.OrderStatus;
import com.example.payment.model.dto.PaymentStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusProducer {

    private final KafkaTemplate<String, PaymentStatusDto> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String paymentStatusTopic;

    public PaymentStatusProducer(KafkaTemplate<String, PaymentStatusDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void fulfillPayment(Long orderId) {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        paymentStatusDto.setStatus(OrderStatus.FULFILLED);
        paymentStatusDto.setOrderId(orderId);
        kafkaTemplate.send(paymentStatusTopic, paymentStatusDto);
    }

    public void rejectPayment(Long orderId) {
        PaymentStatusDto paymentStatusDto = new PaymentStatusDto();
        paymentStatusDto.setStatus(OrderStatus.REJECTED);
        paymentStatusDto.setOrderId(orderId);
        kafkaTemplate.send(paymentStatusTopic, paymentStatusDto);
    }
}
