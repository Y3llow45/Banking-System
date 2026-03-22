package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.service.BankService;

import java.util.function.Consumer;

public class CreateUserWindow extends BasicWindow {

    public final BankService bankService;
    private final Consumer<Void> onUserCreated;

    public CreateUserWindow(BankService bankService, Consumer<Void> onUserCreated) {
        super("Create New User");
        this.bankService = bankService;
        this.onUserCreated = onUserCreated;

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

            bankService.createUser(firstName, lastName, 0, "");

            MessageDialog.showMessageDialog(getTextGUI(), "Success",
                    "User created!\nName: " + firstName + " " + lastName,
                    MessageDialogButton.OK);

            close();
            onUserCreated.accept(null);
        });

        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}