package org.example.service;

import org.example.model.Account;
import org.example.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.RandomStringUtils;

public class BankService {
    private final Map<Long, User> usersById = new HashMap<>();
    public static final int ID_LENGTH = 10;

    public User createUser(String firstName, String lastName, int age, String email) {
        User user = new User();
        user.setId(Long.parseLong(RandomStringUtils.randomNumeric(ID_LENGTH)));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setAccounts(new ArrayList<>());

        usersById.put(user.getId(), user);
        return user;
    }

    public void addUser(User user) {
        usersById.put(user.getId(), user);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(usersById.values());
    }

    public User findUserById(Long id) {
        return usersById.get(id);
    }

    public Account createAccount(String accountName, User selectedUser, String password) {
        if (selectedUser == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        Account newAccount = new Account(accountName, selectedUser, password);
        // Set ID, type, balance etc. as needed
        selectedUser.getAccounts().add(newAccount);
        return newAccount;
    }
}
