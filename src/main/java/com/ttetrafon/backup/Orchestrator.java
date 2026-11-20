package com.ttetrafon.backup;

import com.ttetrafon.watcher.WatchedDirectoriesModel;

public class Orchestrator {

    private final Backuper backuper;
    private final Watcher watcher;

    private final WatchedDirectoriesModel modelBackup;
    private final WatchedDirectoriesModel modelWatch;

    public Orchestrator(WatchedDirectoriesModel modelBackup, WatchedDirectoriesModel modelWatch) {
        this.modelBackup = modelBackup;
        this.modelWatch = modelWatch;

        this.backuper = new Backuper(modelBackup);
        this.watcher = new Watcher(modelWatch);
    }


}
