package com.ttetrafon.window;

import com.ttetrafon.watcher.WatchedDirectoriesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

public class DirectoryListPanel extends JPanel {

    private final WatchedDirectoriesModel model;
    private final JList<Path> list;
    private final JButton removeButton = new JButton("Remove selected");

    public DirectoryListPanel(WatchedDirectoriesModel model) {
        super(new BorderLayout(0, 8));
        this.model = model;

        list = new JList<>(model);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        add(new JScrollPane(list), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttons.add(removeButton);
        add(buttons, BorderLayout.SOUTH);

        removeButton.setEnabled(false);
        list.addListSelectionListener(e -> removeButton.setEnabled(!list.isSelectionEmpty()));
        removeButton.addActionListener(e -> removeSelected());

        list.getInputMap(JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "removeSelected");
        list.getActionMap()
                .put("removeSelected", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeSelected();
                    }
                });
    }

    private void removeSelected() {
        int[] selected = list.getSelectedIndices();
        for (int i = selected.length - 1; i >= 0; i--) {
            model.remove(selected[i]);
        }
    }
}
