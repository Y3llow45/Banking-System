package org.example.windows;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.model.Account;
import org.example.model.Transaction;
import org.example.service.BankService;
import java.util.List;

public class ViewTransactionsWindow extends BasicWindow {

    public ViewTransactionsWindow(BankService bankService) {
        super("View Transactions");

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        panel.addComponent(new Label("Select Account:"));
        ComboBox<Account> accountCombo = new ComboBox<>();
        List<Account> accounts = bankService.getAllAccounts();
        for (Account acc : accounts) {
            accountCombo.addItem(acc);
        }
        panel.addComponent(accountCombo);

        panel.addComponent(new EmptySpace());

        ActionListBox transactionList = new ActionListBox();

        Button showButton = new Button("Show Transactions", () -> {
            Account selected = accountCombo.getSelectedItem();
            if (selected == null) {
                MessageDialog.showMessageDialog(getTextGUI(), "Error",
                        "No account selected", MessageDialogButton.OK);
                return;
            }

            transactionList.clearItems();
            List<Transaction> transactions = selected.getTransactions();

            if (transactions.isEmpty()) {
                transactionList.addItem("No transactions yet", () -> {});
            } else {
                for (Transaction t : transactions) {
                    transactionList.addItem(t.toString(), () -> {});
                }
            }
        });

        panel.addComponent(showButton);
        panel.addComponent(new EmptySpace());
        panel.addComponent(new Label("Transactions:"));
        panel.addComponent(transactionList);

        panel.addComponent(new EmptySpace());
        panel.addComponent(new Button("Close", this::close));

        setComponent(panel);
    }
}