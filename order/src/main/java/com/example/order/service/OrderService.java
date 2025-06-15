package com.example.order.service;

import com.example.order.kafka.OrderCreatedProducer;
import com.example.order.kafka.OrderStatusListener;
import com.example.order.kafka.OrderStatusProducer;
import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.model.dto.OrderCreatedDto;
import com.example.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    private final OrderCreatedProducer orderCreatedProducer;

    private final OrderStatusProducer orderStatusProducer;

    @Autowired
    public OrderService(OrderRepository orderRepository, OrderCreatedProducer orderCreatedProducer, OrderStatusProducer orderStatusProducer) {
        this.orderRepository = orderRepository;
        this.orderCreatedProducer = orderCreatedProducer;
        this.orderStatusProducer = orderStatusProducer;
    }

    public Order createOrder(long userId, BigDecimal price, String metadata) {
        Order order = new Order();
        order.setUserId(userId);
        order.setPrice(price);
        order.setMetadata(metadata);
        order.setStatus(OrderStatus.CREATED);

        Order savedOrder = orderRepository.save(order);

        OrderCreatedDto orderCreatedDto = new OrderCreatedDto();
        orderCreatedDto.setOrderId(savedOrder.getId());
        orderCreatedDto.setUserId(savedOrder.getUserId());
        orderCreatedDto.setAmount(savedOrder.getPrice());

        orderCreatedProducer.createOrder(savedOrder.getUserId(), savedOrder.getId(), savedOrder.getPrice());

        return savedOrder;
    }

    public List<Order> getOrdersByUserId(long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Optional<Order> getOrderById(long orderId) {
        return orderRepository.findById(orderId);
    }

    public Optional<OrderStatus> get(long orderId) {
        return orderRepository.findById(orderId).map(Order::getStatus);
    }

    public Order updateOrderStatus(long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }

    List<Order> findByStatus(OrderStatus status) { return orderRepository.findByStatus(status); }

    List<Order> findByStatusIn(List<OrderStatus> statuses) { return orderRepository.findByStatusIn(statuses); }
}
