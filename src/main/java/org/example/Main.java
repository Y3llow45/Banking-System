package org.example;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;
import org.example.service.BankService;
import org.example.service.FileStorageService;
import org.example.windows.MainMenuWindow;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        //setup
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setPreferTerminalEmulator(true);
        factory.setTerminalEmulatorTitle("Bank Simulator");

        Font baseFont = new Font("Cascadia Code", Font.PLAIN, 18);

        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        attributes.put(TextAttribute.SIZE, 18f);
        Font thickerFont = baseFont.deriveFont(attributes);

        AWTTerminalFontConfiguration fontConfig = SwingTerminalFontConfiguration.newInstance(thickerFont);

        factory.setTerminalEmulatorFontConfiguration(fontConfig);

        Terminal terminal = factory.createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        //end setup
        BankService bankService = new BankService();
        FileStorageService storage = new FileStorageService();

        storage.loadFromFile(bankService);

        MainMenuWindow mainMenu = new MainMenuWindow(gui, bankService);
        //MessageDialog.showMessageDialog(gui, "Error", "Invalid amount", MessageDialogButton.OK); //nice error
        gui.addWindowAndWait(mainMenu);

        storage.saveToFile(bankService);
        screen.stopScreen();
    }
}