package me.hiremedev.tetrisplugin.scoreboards;

import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.Level;
import me.hiremedev.tetrisplugin.scoreboards.Entries.DynamicEntry;
import me.hiremedev.tetrisplugin.scoreboards.Entries.StaticEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;
import java.util.UUID;

public class ScoreboardHandler {
    private ScoreboardManager manager;

    private HashMap<TetrominoeSystem, OpScoreboard> systemScoreboards;
    private HashMap<UUID, OpScoreboard> playerScoreboards;
    private ScoreboardUpdater scoreboardUpdater;

    public ScoreboardHandler(JavaPlugin plugin) {
        manager = Bukkit.getScoreboardManager();
        systemScoreboards = new HashMap<>();
        playerScoreboards = new HashMap<>();
        scoreboardUpdater = new ScoreboardUpdater();
        scoreboardUpdater.runTaskTimer(plugin, 0, 10);
    }

    public void createScoreboardForPlayer(Player player) {
        if(playerScoreboards.containsKey(player))return;
        var scoreboard = new OpScoreboard(
                new StaticEntry(Component.text("Tetro")),
                new StaticEntry(Component.text("Tetro is a minecraft")),
                new StaticEntry(Component.text("minigame filled with")),
                new StaticEntry(Component.text("tetrominoes and fun.")),
                new StaticEntry(Component.text("")),
                new StaticEntry(Component.text("Get a High Score  ")),
                new StaticEntry(Component.text("on any screen, to ")),
                new StaticEntry(Component.text("show your skills.")),
                new StaticEntry(Component.text("")),
                new DynamicEntry(() -> Component.text("%s Score: 0".formatted(player.getName()))),
                new StaticEntry(Component.text(""))
                );
        playerScoreboards.putIfAbsent(player.getUniqueId(), scoreboard);
        scoreboardUpdater.addBoard(scoreboard);
    }

    public void createScoreboardForSystem(TetrominoeSystem system) {
        var scoreboard = new OpScoreboard(
                new StaticEntry(Component.text("Playing %s".formatted(system.getName()))),
                new DynamicEntry(() -> Component.text("HighScore: %s".formatted(system.getHighScore())).color(NamedTextColor.GOLD)),
                new DynamicEntry(() -> Component.text("by: %s".formatted(system.getHighScoreName()))),
                new StaticEntry(Component.text("")),
                new DynamicEntry(() -> Component.text("Level: %s".formatted(system.getCurrentLevel())).color(Level.valueOf("_" + system.getCurrentLevel().getValue()).getColor())),
                new StaticEntry(Component.text("")),
                new DynamicEntry(() -> Component.text("Score: %s".formatted(system.getScore()))),
                new DynamicEntry(() -> Component.text("Lines Clears: %s".formatted(system.getClears())))
        );
        systemScoreboards.put(system, scoreboard);
        scoreboardUpdater.addBoard(scoreboard);
    }

    public void updateScoreboardForSystem(TetrominoeSystem system) {
        systemScoreboards.get(system).update();
    }

    public void updateScoreboardForPlayer(Player player){
        playerScoreboards.get(player.getUniqueId()).update();
    }

    public void addPlayerToSystem(Player player, TetrominoeSystem system) {
        removePlayerFromServer(player);
        systemScoreboards.get(system).addViewer(player.getUniqueId());
    }

    public void addPlayerToServer(Player player) {
        createScoreboardForPlayer(player);
        playerScoreboards.get(player.getUniqueId()).addViewer(player.getUniqueId());
    }

    public void removePlayerFromServer(Player player) {
        playerScoreboards.get(player.getUniqueId()).removeViewer(player.getUniqueId());
        playerScoreboards.remove(player);
    }

    public void removePlayerFromSystem(Player player, TetrominoeSystem system) {
        systemScoreboards.get(system).removeViewer(player.getUniqueId());
        addPlayerToServer(player);
    }
}
