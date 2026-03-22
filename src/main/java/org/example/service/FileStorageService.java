package org.example.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.example.model.BankData;
import org.example.model.User;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileStorageService {

    private static final String FILE_PATH = "bank_data.json";
    private final Gson gson;

    public FileStorageService() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void saveToFile(BankService bankService) {
        BankData data = new BankData();
        data.setUsers(bankService.getAllUsers());
        System.out.println("Saving " + data.getUsers().size() + " users to file (bankService@" + System.identityHashCode(bankService) + ").");
        String json = gson.toJson(data);

        try (Writer writer = new FileWriter(FILE_PATH)) {
            writer.write(json);
            System.out.println("Data successfully saved to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to save data: " + e.getMessage());
        }
    }

    public void loadFromFile(BankService bankService) {
        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            System.out.println("No data file found. Starting empty.");
            return;
        }

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            Type type = new TypeToken<BankData>() {}.getType();
            BankData data = gson.fromJson(reader, type);

            if (data != null && data.getUsers() != null) {
                for (User user : data.getUsers()) {
                    bankService.addUser(user);
                }
                System.out.println("Loaded " + data.getUsers().size() + " users from file.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load data: " + e.getMessage());
        }
    }
}
