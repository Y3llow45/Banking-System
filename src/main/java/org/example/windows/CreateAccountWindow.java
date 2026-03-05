package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.model.Account;
import org.example.model.User;
import org.example.service.BankService;

import java.util.List;

public class CreateAccountWindow extends BasicWindow {

    private final BankService bankService;

    public CreateAccountWindow(BankService bankService) {
        super("Open / Create Account");
        this.bankService = bankService;

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Select User:"));

        ComboBox<User> userCombo = new ComboBox<>();
        List<User> users = bankService.getAllUsers();

        if (users.isEmpty()) {
            userCombo.addItem(null);
            userCombo.setSelectedIndex(-1);
        } else {
            for (User user : users) {
                userCombo.addItem(user);
            }
        }

        panel.addComponent(userCombo);

        panel.addComponent(new Label("Account name:"));
        TextBox accountBox = new TextBox(new TerminalSize(20, 1));
        accountBox.setText("name");
        panel.addComponent(accountBox);

        panel.addComponent(new EmptySpace());

        Button confirmButton = new Button("Create Account", () -> {
            User selectedUser = userCombo.getSelectedItem();

            if (selectedUser == null) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "No users exist yet. Please create a user first.",
                        MessageDialogButton.OK);
                return;
            }


            String accountName = accountBox.getText().trim();
            System.out.println("Account Name: " + accountName);
            try {
                /*if (accountName.length() > 30 || accountName.length()<3) {
                    throw new StringFormatException();
                }*/
            } catch (Exception e) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "Invalid account name! Must be between 3 and 30 characters.",
                        MessageDialogButton.OK);
                return;
            }

            Account account = bankService.createAccount(accountName, selectedUser);

            /*MessageDialog.showMessageDialog(getTextGUI(), "Success",
                    "Account created!\nID: " + account.getAccountId() + "\nName: " + accountName + "\nOwner: " + selectedUser.getName(),
                    MessageDialogButton.OK);*/

            close();
        });

        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}
