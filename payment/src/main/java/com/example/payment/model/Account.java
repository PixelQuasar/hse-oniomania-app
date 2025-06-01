package com.example.payment.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts", uniqueConstraints = @UniqueConstraint(columnNames = "user_id"))
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private long userId;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    public Account(long userId) {
        this.userId = userId;
        this.balance = BigDecimal.ZERO;
    }

    /**
     * Adds the specified amount to the account balance.
     * @param amount - the amount to add, must be non-negative
     */
    public void addToBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount to add must be non-negative");
        }
        this.balance = this.balance.add(amount);
    }

    /**
     * Subtracts the specified amount from the account balance.
     * @param amount - the amount to subtract, must be non-negative
     */
    public void subtractFromBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount to subtract must be non-negative");
        }
        BigDecimal newBalance = this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Insufficient funds for the transaction");
        }
        this.balance = newBalance;
    }
}
