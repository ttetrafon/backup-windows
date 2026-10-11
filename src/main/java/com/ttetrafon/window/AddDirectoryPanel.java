package com.ttetrafon.window;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

public class AddDirectoryPanel extends JPanel {

    private final JTextField pathField = new JTextField();
    private final Consumer<Path> onAdd;

    public AddDirectoryPanel(Consumer<Path> onAdd) {
        super(new GridBagLayout());
        this.onAdd = onAdd;

        pathField.putClientProperty("JTextField.placeholderText", "C:\\path\\to\\folder");

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.insets = new Insets(0, 4, 0, 4);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.weightx = 0;
        add(new JLabel("Directory:"), c);

        c.gridx = 1;
        c.weightx = 1;
        add(pathField, c);

        c.gridx = 2;
        c.weightx = 0;
        JButton browseButton = new JButton("Browse...");
        add(browseButton, c);

        c.gridx = 3;
        c.weightx = 0;
        JButton addButton = new JButton("Add Selected");
        add(addButton, c);

        browseButton.addActionListener(e -> browse());
        addButton.addActionListener(e -> addSelected());
        pathField.addActionListener(e -> addSelected()); // Handles hitting Return/Enter within the field
    }

    private void browse() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select a directory");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            String text = pathField.getText().trim();
            if (!text.isEmpty()) {
                try {
                    Path p = Path.of(text);
                    if (Files.isDirectory(p)) chooser.setCurrentDirectory(p.toFile());
                } catch (Exception ignored) {}
            }

            if (chooser.showOpenDialog(SwingUtilities.getWindowAncestor(this)) == JFileChooser.APPROVE_OPTION) {
                pathField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        } catch (Throwable t) {
            t.printStackTrace();
            JOptionPane.showMessageDialog(this, t.toString(), "Browse failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSelected() {
        String t = pathField.getText().trim();
        if (t.isEmpty()) return;

        Path p;
        try {
            p = Path.of(t);
        }
        catch (Exception e) {
            showError("This is not a valid path.");
            return;
        }

        if (!Files.isDirectory(p)) {
            showError("The directory does not exist");
            return;
        }

        onAdd.accept(p.toAbsolutePath().normalize());
        pathField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Invalid directory", JOptionPane.WARNING_MESSAGE);
    }
}
