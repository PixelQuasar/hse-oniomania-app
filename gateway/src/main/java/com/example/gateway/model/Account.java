package com.example.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Account {
    private long id;

    private long userId;

    private BigDecimal balance;
}
