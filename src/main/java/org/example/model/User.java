package org.example.model;

import java.util.List;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private List<Account> accounts;

    public String getUserId() {
        return id.toString();
    }

    public String getName() {
        return firstName + " " + lastName;
    }
}
