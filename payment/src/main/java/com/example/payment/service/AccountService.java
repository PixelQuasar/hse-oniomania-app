package com.example.payment.service;

import com.example.payment.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountService {

    void createAccount(Long userId);

    Optional<Account> getAccountByUserId(Long userId);

    Account addToBalance(Long userId, BigDecimal amount);

    void subtractFromBalance(Long userId, BigDecimal amount, Long orderId);

    Account getAccountById(Long id);
}
