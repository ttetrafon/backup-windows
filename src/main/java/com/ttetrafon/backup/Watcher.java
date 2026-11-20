package com.ttetrafon.backup;

import com.ttetrafon.watcher.WatchedDirectoriesModel;

public class Watcher {
    // Keeps a list of paths which it continuously watches for changes, and backups changed files in realtime.#

    private final WatchedDirectoriesModel model;

    public Watcher(WatchedDirectoriesModel model) {
        this.model = model;
    }
}
