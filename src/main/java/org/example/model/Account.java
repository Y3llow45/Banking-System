package org.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long accountId;
    private String accountName;
    private BigDecimal balance;

    private transient User owner;
    private String password;
    private List<Transaction> transactions;

    public Account(String accountName, User owner, String password) {
        this.accountId = System.currentTimeMillis();
        this.accountName = accountName;
        this.password = password;
        this.balance = BigDecimal.valueOf(200.00);
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    public String getAccountId() {
        return accountId.toString();
    }
}

