package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.model.User;
import org.example.service.BankService;

public class CreateUserWindow extends BasicWindow {

    private final BankService bankService;
    private final String regexEmail = "[A-z, \\d]{2,64}@[a-z]{2,63}\\.[a-z]{2,63}";

    public CreateUserWindow(BankService bankService) {
        super("Create New User");
        this.bankService = bankService;

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("First Name:"));
        TextBox firstNameBox = new TextBox(new TerminalSize(30, 1));
        panel.addComponent(firstNameBox);

        panel.addComponent(new Label("Last Name:"));
        TextBox lastNameBox = new TextBox(new TerminalSize(30, 1));
        panel.addComponent(lastNameBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Create User", () -> {
            String firstName = firstNameBox.getText().trim();
            String lastName = lastNameBox.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty()) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Full Name is required!", MessageDialogButton.OK);
                return;
            }

            User user = bankService.createUser(firstName, lastName, 0, "");

            MessageDialog.showMessageDialog(getTextGUI(), "Success",
                    "User created!\nName: " + firstName + " " + lastName,
                    MessageDialogButton.OK);

            close();
        });

        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}