package com.example.payment.service;

import com.example.payment.model.Account;
import com.example.payment.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void createAccount(Long userId) {
        if (accountRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("Account already exists for user id: " + userId);
        }
        Account newAccount = new Account(userId);
        accountRepository.save(newAccount);
    }

    @Override
    public Optional<Account> getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public Account addToBalance(Long userId, BigDecimal amount) {
        Account account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user id: " + userId));

        account.addToBalance(amount);
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void subtractFromBalance(Long userId, BigDecimal amount, Long orderId) {
        Account account = accountRepository
                .findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found for user id: " + userId));

        account.subtractFromBalance(amount);
        accountRepository.save(account);
    }

    @Override
    public Account getAccountById(Long id) {
        return accountRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + id));
    }
}
