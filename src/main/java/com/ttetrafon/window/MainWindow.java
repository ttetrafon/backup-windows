package com.ttetrafon.window;

import com.ttetrafon.watcher.WatchedDirectoriesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    public MainWindow() {
        super("System Intelligence: Windows Backup Utility");
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // center on screen

        WatchedDirectoriesModel model = new WatchedDirectoriesModel();

        AddDirectoryPanel addDirectoryPanel = new AddDirectoryPanel(path -> {
            if (!model.add(path)) {
                JOptionPane.showMessageDialog(this, "That directory is already in the list.",
                        "Already added", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        addDirectoryPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        add(addDirectoryPanel, BorderLayout.NORTH);

        DirectoryListPanel directoryListPanel = new DirectoryListPanel(model);
        directoryListPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 12, 12));
        add(directoryListPanel, BorderLayout.CENTER);

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
