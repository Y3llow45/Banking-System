package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class WithdrawWindow extends BasicWindow {
    public WithdrawWindow() {
        super("Withdraw Funds");

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("Account ID:"));
        TextBox accountIdBox = new TextBox(new TerminalSize(20, 1));
        panel.addComponent(accountIdBox);

        panel.addComponent(new Label("Amount:"));
        TextBox amountBox = new TextBox(new TerminalSize(20, 1));
        panel.addComponent(amountBox);

        Button confirmButton = new Button("Confirm", () -> {
            // asdasd
            MessageDialog.showMessageDialog(getTextGUI(), "Success", "Withdrawal completed!", MessageDialogButton.OK);
            close();
        });
        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}
