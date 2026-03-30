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

public class TransferWindow extends BasicWindow {

    private final BankService bankService;
    private final FileStorageService storage;

    public TransferWindow(BankService bankService, FileStorageService storage) {
        super("Transfer Funds");
        this.bankService = bankService;
        this.storage = storage;

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("From Account:"));
        ComboBox<Account> fromCombo = new ComboBox<>();
        List<Account> accounts = bankService.getAllAccounts();
        for (Account acc : accounts) {
            fromCombo.addItem(acc);
        }
        panel.addComponent(fromCombo);

        panel.addComponent(new Label("To Account:"));
        ComboBox<Account> toCombo = new ComboBox<>();
        for (Account acc : accounts) {
            toCombo.addItem(acc);
        }
        panel.addComponent(toCombo);

        panel.addComponent(new Label("Amount (€):"));
        TextBox amountBox = new TextBox(new TerminalSize(20, 1));
        amountBox.setText("50.00");
        panel.addComponent(amountBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Transfer", () -> {
            Account from = fromCombo.getSelectedItem();
            Account to = toCombo.getSelectedItem();

            if (from == null || to == null) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Please select both accounts", MessageDialogButton.OK);
                return;
            }

            try {
                BigDecimal amount = new BigDecimal(amountBox.getText().trim());
                bankService.transfer(from, to, amount);

                storage.saveToFile(bankService);   // ← auto save

                MessageDialog.showMessageDialog(getTextGUI(), "Success",
                        "Transfer completed!\n€" + amount + " from " + from.getAccountName() +
                                " to " + to.getAccountName(), MessageDialogButton.OK);

                close();
            } catch (Exception e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        e.getMessage(), MessageDialogButton.OK);
            }
        });

        panel.addComponent(confirmButton);
        panel.addComponent(new Button("Cancel", this::close));

        setComponent(panel);
    }
}