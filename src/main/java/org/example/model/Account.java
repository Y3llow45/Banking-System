package org.example.model;

import java.math.BigDecimal;
import java.util.List;

public class Account {
    private Long accountId;
    private String accountName;
    private BigDecimal balance;
    private User owner;
    private List<Transaction> transactions;

    public Account(String accountName, User owner) {
        this.accountId = System.currentTimeMillis();
        this.accountName = accountName;
        this.balance = BigDecimal.valueOf(200.00);
        this.owner = owner;
        this.transactions = null;
    }

    public String getAccountId() {
        return accountId.toString();
    }
}

