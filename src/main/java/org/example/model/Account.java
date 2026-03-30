package org.example.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private Long accountId;
    private String accountName;
    private BigDecimal balance;

    private transient User owner;
    private String password;
    private List<Transaction> transactions = new ArrayList<>();

    public Account(String accountName, User owner, String password) {
        this.accountId = System.currentTimeMillis();
        this.accountName = accountName;
        this.password = password;
        this.balance = BigDecimal.valueOf(200.00);
        this.owner = owner;
        this.transactions = new ArrayList<>();
    }

    /*public String getAccountId() {
        return accountId.toString();
    }*/

    @Override
    public String toString() {
        return accountName + " (€" + balance + ")";
    }

    public Long getAccountId() { return accountId; }
    public String getAccountName() { return accountName; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public User getOwner() { return owner; }
    public String getPassword() { return password; }
    public List<Transaction> getTransactions() { return transactions; }
}

