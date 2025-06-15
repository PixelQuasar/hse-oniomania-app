package com.example.gateway.service;

import com.example.gateway.model.OrderStatus;
import com.example.gateway.model.dto.CreateOrderBodyDto;
import com.example.gateway.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final RestTemplate restTemplate;
    private final String orderServiceUrl;

    @Autowired
    public OrderService(RestTemplate restTemplate, @Value("${service.url.order}") String orderServiceUrl) {
        this.restTemplate = restTemplate;
        this.orderServiceUrl = orderServiceUrl;
    }

    public Order createOrder(CreateOrderBodyDto createOrderBodyDto) {
        String url = orderServiceUrl + "/order";
        return restTemplate.postForObject(url, createOrderBodyDto, Order.class);
    }

    public Optional<Order> getOrderById(Long id) {
        String url = orderServiceUrl + "/order/" + id;
        try {
            Order order = restTemplate.getForObject(url, Order.class);
            return Optional.ofNullable(order);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public List<Order> getOrdersByUserId(Long userId) {
        String url = orderServiceUrl + "/order/list/" + userId;
        // Используем exchange с ParameterizedTypeReference для получения списка дженериков
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Order>>() {}
        ).getBody();
    }

    public Optional<OrderStatus> getOrderStatus(Long id) {
        String url = orderServiceUrl + "/order/status/" + id;
        try {
            OrderStatus status = restTemplate.getForObject(url, OrderStatus.class);
            return Optional.ofNullable(status);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
