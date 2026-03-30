package org.example.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDateTime timestamp;

    public Transaction(BigDecimal amount, String type, String description) {
        this.id = System.currentTimeMillis();
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    // Nice display string for the list
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String sign = type.startsWith("DEPOSIT") || type.equals("TRANSFER_IN") ? "+" : "-";
        return timestamp.format(fmt) + " | " + sign + " €" + amount + " | " + type + " | " + description;
    }

    // Getters
    public Long getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public LocalDateTime getTimestamp() { return timestamp; }
}