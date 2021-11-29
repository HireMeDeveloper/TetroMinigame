package me.hiremedev.tetrisplugin;

import com.mongodb.client.MongoClients;
import me.hiremedev.tetrisplugin.Scrolling.BossBarManager;
import me.hiremedev.tetrisplugin.Scrolling.BossBarTask;
import me.hiremedev.tetrisplugin.commands.Play;
import me.hiremedev.tetrisplugin.commands.Screen;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.events.listeners.ControlItemListeners;
import me.hiremedev.tetrisplugin.database.DatabaseManager;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.events.listeners.PieceDropListener;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.events.listeners.PlayerListener;
import me.hiremedev.tetrisplugin.events.listeners.SystemListener;
import me.hiremedev.tetrisplugin.players.PlayerManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class TetrisPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        //connect to database

        var client = MongoClients.create("redacted");
        var systemCollection = client.getDatabase("system_info").getCollection("system");
        var playerCollection = client.getDatabase("players").getCollection("items");


        var bossBarManager = new BossBarManager();
        var bossBarTask = new BossBarTask(bossBarManager).runTaskTimer(this,0,1);

        var systemManager = new SystemManager();
        var keyManager = new KeyManager(this);
        var playerManager = new PlayerManager();
        var scoreboardHandler = new ScoreboardHandler(this);

        var databaseManager = new DatabaseManager(systemCollection, systemManager, this, keyManager, scoreboardHandler);

        var masterManager = new MasterManager(
                keyManager,
                systemManager,
                databaseManager,
                scoreboardHandler,
                bossBarManager,
                playerManager
        );

        //register commands
        getCommand("screen").setExecutor(new Screen(this, masterManager));
        getCommand("screen").setTabCompleter(new Screen(this, masterManager));
        getCommand("play").setExecutor(new Play(this, masterManager));
        getCommand("play").setTabCompleter(new Play(this, masterManager ));

        //register listeners
        Bukkit.getServer().getPluginManager().registerEvents(new ControlItemListeners(masterManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PieceDropListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(masterManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SystemListener(masterManager), this);

        databaseManager.createSystemsFromDatabase();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
