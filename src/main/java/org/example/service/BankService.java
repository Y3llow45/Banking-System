package org.example.service;

import org.example.model.Account;
import org.example.model.Transaction;
import org.example.model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.RandomStringUtils;

public class BankService {

    private final Map<Long, User> usersById = new HashMap<>();

    public User createUser(String firstName, String lastName, int age, String email) {
        if (!usersById.isEmpty()) {
            throw new IllegalStateException("Only one user is allowed in this app");
        }

        User user = new User();
        user.setId(Long.parseLong(RandomStringUtils.randomNumeric(10)));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setAccounts(new ArrayList<>());

        usersById.put(user.getId(), user);
        return user;
    }

    public boolean hasUser() {
        return !usersById.isEmpty();
    }

    public User getOnlyUser() {
        return usersById.isEmpty() ? null : usersById.values().iterator().next();
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    public void addUser(User user) {
        usersById.put(user.getId(), user);
    }

    public Account createAccount(String accountName, String password) {
        User user = getOnlyUser();
        if (user == null) {
            throw new IllegalStateException("Create a user first");
        }

        Account newAccount = new Account(accountName, user, password);
        user.getAccounts().add(newAccount);
        return newAccount;
    }

    public List<Account> getAllAccounts() {
        User user = getOnlyUser();
        return user != null ? user.getAccounts() : new ArrayList<>();
    }

    public void deposit(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");

        account.setBalance(account.getBalance().add(amount));
        account.getTransactions().add(new Transaction(amount, "DEPOSIT", "Deposit to account"));
    }

    public void withdraw(Account account, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (account.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        account.setBalance(account.getBalance().subtract(amount));
        account.getTransactions().add(new Transaction(amount, "WITHDRAW", "Withdrawal from account"));
    }

    public void transfer(Account fromAccount, Account toAccount, BigDecimal amount) {
        if (fromAccount.equals(toAccount))
            throw new IllegalArgumentException("Cannot transfer to the same account");
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Amount must be positive");
        if (fromAccount.getBalance().compareTo(amount) < 0)
            throw new IllegalArgumentException("Insufficient funds");

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        fromAccount.getTransactions().add(new Transaction(amount, "TRANSFER_OUT", "To " + toAccount.getAccountName()));
        toAccount.getTransactions().add(new Transaction(amount, "TRANSFER_IN", "From " + fromAccount.getAccountName()));
    }
}