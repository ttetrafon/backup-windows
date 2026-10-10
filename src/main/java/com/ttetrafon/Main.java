package com.ttetrafon;

import com.formdev.flatlaf.FlatDarkLaf;
import com.ttetrafon.window.MainWindow;
import com.ttetrafon.window.Singleton;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static volatile MainWindow window;

    static void main() {
        boolean first = Singleton.acquire(() ->
                SwingUtilities.invokeLater(() -> {
                    if (window != null) window.showWindow();
                }));
        if (!first) return;

        FlatDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            if (!SystemTray.isSupported()) return;

            window = new MainWindow();

            PopupMenu menu = new PopupMenu();
            MenuItem open = new MenuItem("Open");
            menu.add(open);
            MenuItem exit = new MenuItem("Exit");
            menu.add(exit);

            Image img = Toolkit.getDefaultToolkit()
                    .getImage(Main.class.getResource("/icon.png"));
            TrayIcon icon = new TrayIcon(img, "SI Backup", menu);
            icon.setImageAutoSize(true);

            open.addActionListener(e -> window.showWindow());
            icon.addActionListener(e -> window.showWindow());
            exit.addActionListener(e -> System.exit(0));

            exit.addActionListener(e -> {
                Singleton.release();
                SystemTray.getSystemTray().remove(icon);
                System.exit(0);
            });

            try {
                SystemTray.getSystemTray().add(icon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        });
    }
}
