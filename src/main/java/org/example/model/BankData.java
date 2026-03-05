package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class BankData {
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
