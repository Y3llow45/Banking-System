package org.example.windows;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;

public class TransferWindow extends BasicWindow {
    public TransferWindow() {
        super("Transfer Funds");

        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(2));

        panel.addComponent(new Label("From Account ID:"));
        TextBox fromAccountBox = new TextBox(new TerminalSize(20, 1));
        panel.addComponent(fromAccountBox);

        panel.addComponent(new Label("To Account ID:"));
        TextBox toAccountBox = new TextBox(new TerminalSize(20, 1));
        panel.addComponent(toAccountBox);

        panel.addComponent(new Label("Amount:"));
        TextBox amountBox = new TextBox(new TerminalSize(20, 1));
        panel.addComponent(amountBox);

        Button confirmButton = new Button("Confirm", () -> {
            // s
            MessageDialog.showMessageDialog(getTextGUI(), "Success", "Transfer completed!", MessageDialogButton.OK);
            close();
        });
        panel.addComponent(confirmButton);

        Button cancelButton = new Button("Cancel", this::close);
        panel.addComponent(cancelButton);

        setComponent(panel);
    }
}
