package com.example.order.cron;

import com.example.order.model.Order;
import com.example.order.model.OrderStatus;
import com.example.order.repository.OrderRepository;
import com.example.order.kafka.OrderStatusProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DeliveryScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DeliveryScheduler.class);

    private final OrderRepository orderRepository;
    private final OrderStatusProducer orderStatusProducer;

    @Autowired
    public DeliveryScheduler(OrderRepository orderRepository, OrderStatusProducer orderStatusProducer) {
        this.orderRepository = orderRepository;
        this.orderStatusProducer = orderStatusProducer;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void updateOrderStatuses() {
        logger.info("Running scheduled task to update order statuses...");

        List<Order> shippedOrders = orderRepository.findByStatus(OrderStatus.SHIPPED);
        for (Order order : shippedOrders) {
            order.setStatus(OrderStatus.DELIVERED);
            orderRepository.save(order);

            orderStatusProducer.orderDelivered(order.getId(), "Your order has been delivered.");
            logger.info("Order ID {} status updated to DELIVERED.", order.getId());
        }

        List<Order> paidOrders = orderRepository.findByStatus(OrderStatus.PAYMENT_SUCCESS);
        for (Order order : paidOrders) {
            order.setStatus(OrderStatus.SHIPPED);
            orderRepository.save(order);

            orderStatusProducer.orderShipped(order.getId(), "Your order has been shipped.");
            logger.info("Order ID {} status updated to SHIPPED.", order.getId());
        }

        logger.info("Finished updating statuses. Updated {} SHIPPED and {} PAID orders.", shippedOrders.size(), paidOrders.size());
    }
}
