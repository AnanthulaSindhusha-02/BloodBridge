/**
 * package com.bloodbridge;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class GuiConsole {
    private static JTextArea textArea;

    public static void createGui() {
        // Set system look and feel for modern appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("BloodBridge Console");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null); // center on screen

        // Text area styling
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14)); // monospaced font
        textArea.setBackground(new Color(30, 30, 30));          // dark background
        textArea.setForeground(new Color(0, 255, 0));           // green text like a terminal
        textArea.setCaretColor(Color.WHITE);

        // Scroll pane styling
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem clearItem = new JMenuItem("Clear Console");
        clearItem.addActionListener(e -> textArea.setText(""));
        optionsMenu.add(clearItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        optionsMenu.add(exitItem);

        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        // Add everything to frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Redirect System.out and System.err
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
    }
}
*/

package com.bloodbridge;

import javax.swing.*;
import java.awt.*;
import java.io.OutputStream;
import java.io.PrintStream;

public class GuiConsole {
    private static JTextArea textArea;

    public static void createGui() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        JFrame frame = new JFrame("BloodBridge Console");
        frame.setSize(700, 400);
        frame.setLocationRelativeTo(null);

        // Text area styling
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        textArea.setBackground(new Color(30, 30, 30));
        textArea.setForeground(new Color(0, 255, 0));
        textArea.setCaretColor(Color.WHITE);

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu optionsMenu = new JMenu("Options");

        JMenuItem clearItem = new JMenuItem("Clear Console");
        clearItem.addActionListener(e -> textArea.setText(""));
        optionsMenu.add(clearItem);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        optionsMenu.add(exitItem);

        menuBar.add(optionsMenu);
        frame.setJMenuBar(menuBar);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Redirect System.out and System.err
        PrintStream printStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
                textArea.append(String.valueOf((char) b));
                textArea.setCaretPosition(textArea.getDocument().getLength());
            }
        });
        System.setOut(printStream);
        System.setErr(printStream);
    }

    /** Show a popup message to the user */
    public static void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /** Ask user for input via popup dialog */
    public static String getInput(String message) {
        return JOptionPane.showInputDialog(null, message);
    }
}
