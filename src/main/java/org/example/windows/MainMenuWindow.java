package org.example.windows;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import org.example.service.BankService;

public class MainMenuWindow extends BasicWindow {
    private final MultiWindowTextGUI gui;
    private final BankService bankService;

    public MainMenuWindow(MultiWindowTextGUI gui, BankService bankService) {
        super("Bank Simulator - Main Menu");
        this.gui = gui;
        this.bankService = bankService;

        Panel panel = new Panel();
        panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        Label header = new Label("Welcome to Bank Simulator");
        header.setForegroundColor(TextColor.ANSI.GREEN);
        panel.addComponent(header);

        panel.addComponent(new EmptySpace());

        ActionListBox menu = new ActionListBox();
        menu.addItem("Create New User", this::openUserCreation);
        menu.addItem("Open/Create Account", this::openAccountCreation);
        menu.addItem("Deposit", this::openDeposit);
        menu.addItem("Withdraw", this::openWithdraw);
        menu.addItem("Transfer", this::openTransfer);
        menu.addItem("View Balance", this::viewBalance);
        menu.addItem("View Transactions", this::viewTransactions);
        menu.addItem("Exit", this::close);

        panel.addComponent(menu);

        setComponent(panel);
    }

    private void openUserCreation() {
        CreateUserWindow createUserWindow = new CreateUserWindow(bankService);
        gui.addWindowAndWait(createUserWindow);
    }

    private void openAccountCreation() {
        CreateAccountWindow createAccountWindow = new CreateAccountWindow(bankService);
        gui.addWindowAndWait(createAccountWindow);
    }

    private void openDeposit() {
        DepositWindow depositWindow = new DepositWindow(bankService);
        gui.addWindowAndWait(depositWindow);
    }

    private void openWithdraw() {
        WithdrawWindow withdrawWindow = new WithdrawWindow(bankService);
        gui.addWindowAndWait(withdrawWindow);
    }

    private void openTransfer() {
        TransferWindow transferWindow = new TransferWindow(bankService);
        gui.addWindowAndWait(transferWindow);
    }

    private void viewBalance() {
        // asdsad
        MessageDialog.showMessageDialog(gui, "Balance", "Your balance is €1000.00", MessageDialogButton.OK);
    }

    private void viewTransactions() {
        // asdsad
        MessageDialog.showMessageDialog(gui, "Transactions", "No transactions yet.", MessageDialogButton.OK);
    }
}