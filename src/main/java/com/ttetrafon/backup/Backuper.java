package com.ttetrafon.backup;

import com.ttetrafon.watcher.WatchedDirectoriesModel;

public class Backuper {
    // Runs at an interval, backing up all changed files as needed.

    private final WatchedDirectoriesModel model;

    public Backuper(WatchedDirectoriesModel model) {
        this.model = model;
    }
}
