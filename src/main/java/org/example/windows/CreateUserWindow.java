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

        // Name
        panel.addComponent(new Label("Full Name:"));
        TextBox nameBox = new TextBox(new TerminalSize(30, 1));
        panel.addComponent(nameBox);

        panel.addComponent(new Label("Email:"));
        TextBox emailBox = new TextBox(new TerminalSize(30, 1));
        panel.addComponent(emailBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Create User", () -> {
            String name = nameBox.getText().trim();
            String email = emailBox.getText().trim();

            if (name.isEmpty()) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Full Name is required!", MessageDialogButton.OK);
                return;
            }

            if(!email.matches(regexEmail)){
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Invalid email format!", MessageDialogButton.OK);
                return;
            }

            User user = bankService.createUser(name, email);

            MessageDialog.showMessageDialog(getTextGUI(), "Success",
                    "User created!\nName: " + name,
                    MessageDialogButton.OK);

            close();
        });

        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}