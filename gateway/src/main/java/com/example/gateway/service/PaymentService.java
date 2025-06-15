package com.example.gateway.service;

import com.example.gateway.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String paymentServiceUrl;

    @Autowired
    public PaymentService(RestTemplate restTemplate, @Value("${service.url.payment}") String paymentServiceUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceUrl = paymentServiceUrl;
    }

    public void createAccount(Long userId) {
        String url = UriComponentsBuilder.fromHttpUrl(paymentServiceUrl + "/accounts")
                .queryParam("userId", userId)
                .toUriString();
        restTemplate.postForEntity(url, null, String.class);
    }

    public Optional<Account> getAccountByUserId(Long userId) {
        String url = paymentServiceUrl + "/accounts/" + userId;
        try {
            Account account = restTemplate.getForObject(url, Account.class);
            return Optional.ofNullable(account);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    public Account topUpAccount(Long userId, BigDecimal amount) {
        String url = UriComponentsBuilder.fromHttpUrl(paymentServiceUrl + "/accounts/top-up")
                .queryParam("userId", userId)
                .queryParam("amount", amount)
                .toUriString();
        return restTemplate.postForObject(url, null, Account.class);
    }
}
