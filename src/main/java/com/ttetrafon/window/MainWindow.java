package com.ttetrafon.window;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("System Intelligence: Windows Backup Utility");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // center on screen

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowIconified(WindowEvent e) {
                setVisible(false);
                setExtendedState(NORMAL); // required to open again
            }
        });
    }

    public void showWindow() {
        setVisible(true);
        setExtendedState(NORMAL);
        toFront();
        requestFocus();
    }
}
