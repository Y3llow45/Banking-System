// src/main/java/org/example/windows/DepositWindow.java
package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.model.Account;
import org.example.service.BankService;
import org.example.service.FileStorageService;

import java.math.BigDecimal;
import java.util.List;

public class DepositWindow extends BasicWindow {
    private final BankService bankService;
    private final FileStorageService storage;

    public DepositWindow(BankService bankService, FileStorageService storage) {
        super("Deposit Funds");
        this.bankService = bankService;
        this.storage = storage;

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Select Account:"));
        ComboBox<Account> accountCombo = new ComboBox<>();
        List<Account> accounts = bankService.getAllAccounts();
        for (Account acc : accounts) {
            accountCombo.addItem(acc);
        }
        panel.addComponent(accountCombo);

        panel.addComponent(new Label("Amount (€):"));
        TextBox amountBox = new TextBox(new TerminalSize(20, 1));
        amountBox.setText("50.00");
        panel.addComponent(amountBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Deposit", () -> {
            Account selected = accountCombo.getSelectedItem();
            if (selected == null) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", "No account selected", MessageDialogButton.OK);
                return;
            }

            try {
                BigDecimal amount = new BigDecimal(amountBox.getText().trim());
                bankService.deposit(selected, amount);

                storage.saveToFile(bankService);   // ← auto save

                MessageDialog.showMessageDialog(getTextGUI(), "Success",
                        "Deposited €" + amount + " to " + selected.getAccountName(), MessageDialogButton.OK);
                close();
            } catch (Exception e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error", e.getMessage(), MessageDialogButton.OK);
            }
        });

        panel.addComponent(confirmButton);
        panel.addComponent(new Button("Cancel", this::close));

        setComponent(panel);
    }
}