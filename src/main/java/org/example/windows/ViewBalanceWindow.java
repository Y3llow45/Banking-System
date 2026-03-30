package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.model.Account;
import org.example.service.BankService;
import java.util.List;

public class ViewBalanceWindow extends BasicWindow {

    public ViewBalanceWindow(BankService bankService) {
        super("View Balance");

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Select Account:"));
        ComboBox<Account> accountCombo = new ComboBox<>();
        List<Account> accounts = bankService.getAllAccounts();
        for (Account acc : accounts) {
            accountCombo.addItem(acc);
        }
        panel.addComponent(accountCombo);

        panel.addComponent(new EmptySpace());

        Button showButton = new Button("Show Balance", () -> {
            Account selected = accountCombo.getSelectedItem();
            if (selected == null) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "No account selected", MessageDialogButton.OK);
                return;
            }

            MessageDialog.showMessageDialog(getTextGUI(), "Balance",
                    "Account: " + selected.getAccountName() + "\n" +
                            "Current Balance: €" + selected.getBalance(),
                    MessageDialogButton.OK);
        });

        panel.addComponent(showButton);
        panel.addComponent(new Button("Close", this::close));

        setComponent(panel);
    }
}