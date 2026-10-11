package com.ttetrafon.watcher;

import javax.swing.*;
import java.nio.file.Path;
import java.util.*;

public class WatchedDirectoriesModel extends AbstractListModel<Path> {

    private static final Comparator<Path> ORDER = Comparator.comparing(Path::toString, String.CASE_INSENSITIVE_ORDER);
    private final List<Path> items = new ArrayList<>();

    @Override
    public int getSize() {
        return items.size();
    }

    @Override
    public Path getElementAt(int index) {
        return items.get(index);
    }

    public boolean add(Path path) {
        int idx = Collections.binarySearch(items, path, ORDER);
        if (idx >= 0) return false;

        int insertAt = -idx - 1;
        items.add(insertAt, path);
        fireIntervalAdded(this, insertAt, insertAt);
        return true;
    }

    public void remove(int index) {
        items.remove(index);
        fireIntervalRemoved(this, index, index);
    }

    public List<Path> snapshot() {
        return List.copyOf(items);
    }
}
