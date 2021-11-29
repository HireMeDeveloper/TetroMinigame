package me.hiremedev.tetrisplugin.engine;

import me.hiremedev.tetrisplugin.Scrolling.BossBarManager;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.database.DatabaseManager;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.players.PlayerManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;

public class MasterManager {
    private KeyManager keyManager;
    private SystemManager systemManager;
    private DatabaseManager databaseManager;
    private ScoreboardHandler scoreboardHandler;
    private BossBarManager bossBarManager;
    private PlayerManager playerManager;

    public MasterManager(
            KeyManager keyManager,
            SystemManager systemManager,
            DatabaseManager databaseManager,
            ScoreboardHandler scoreboardHandler,
            BossBarManager bossBarManager,
            PlayerManager playerManager) {
        this.keyManager = keyManager;
        this.systemManager = systemManager;
        this.databaseManager = databaseManager;
        this.scoreboardHandler = scoreboardHandler;
        this.bossBarManager = bossBarManager;
        this.playerManager = playerManager;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public ScoreboardHandler getScoreboardHandler() {
        return scoreboardHandler;
    }

    public BossBarManager getBossBarManager() {
        return bossBarManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
