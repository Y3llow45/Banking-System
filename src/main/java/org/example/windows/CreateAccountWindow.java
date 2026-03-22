package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.service.BankService;

public class CreateAccountWindow extends BasicWindow {

    private final BankService bankService;

    public CreateAccountWindow(BankService bankService) {
        super("Open / Create Account");
        this.bankService = bankService;

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Account Name:"));
        TextBox accountBox = new TextBox(new TerminalSize(25, 1));
        accountBox.setText("My Checking");
        panel.addComponent(accountBox);

        panel.addComponent(new Label("Password:"));
        TextBox passwordBox = new TextBox(new TerminalSize(25, 1));
        passwordBox.setText("password123");
        panel.addComponent(passwordBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Create Account", () -> {
            String accountName = accountBox.getText().trim();
            String password = passwordBox.getText().trim();

            if (accountName.length() < 3 || accountName.length() > 30) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Account name must be 3-30 characters", MessageDialogButton.OK);
                return;
            }

            bankService.createAccount(accountName, password);

            MessageDialog.showMessageDialog(getTextGUI(), "Success",
                    "Account created!\nName: " + accountName, MessageDialogButton.OK);
            close();
        });

        panel.addComponent(confirmButton);
        panel.addComponent(new Button("Cancel", this::close));

        setComponent(panel);
    }
}