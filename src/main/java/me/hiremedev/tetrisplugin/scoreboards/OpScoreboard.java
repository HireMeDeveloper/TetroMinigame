package me.hiremedev.tetrisplugin.scoreboards;

import me.hiremedev.tetrisplugin.scoreboards.Entries.Entry;
import me.hiremedev.tetrisplugin.scoreboards.Entries.StaticEntry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class OpScoreboard {
    private final int MAX_LINES = 15;
    private final ScoreboardTools tools = new ScoreboardTools();
    private List<Entry> entries = new ArrayList<>();
    private List<Component> staleEntries;

    private Entry title;

    public OpScoreboard(Entry title, Entry... entries) {
        this.title = title;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Arrays.stream(entries).forEach(
                entry -> this.entries.add(entry)
        );
    }

    private Scoreboard bukkitBoard = Bukkit.getScoreboardManager().getNewScoreboard();
    private Objective objective = tools.registerObjective(bukkitBoard, "SB", "dummy", Component.text("Tetro").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD));

    private List<UUID> viewers = new ArrayList<>();

    public void addViewer(UUID uuid){
        viewers.add(uuid);
        Bukkit.getLogger().info("Added viewer to scoreboard.");
        var player = Bukkit.getPlayer(uuid);
        if(player != null) player.setScoreboard(bukkitBoard);
    }

    public void removeViewer(UUID uuid){
        var player = Bukkit.getPlayer(uuid);
        if(player != null) player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        viewers.remove(uuid);
    }

    public void update(){
        for (int i = 0; i < entries.size(); i++) {
            var entry = entries.get(i);
            var freshLine = entry.getComponent();

            var lines = MAX_LINES - (MAX_LINES - entries.size());

            var entryPrefix = ChatColor.values()[i];
            var team = tools.getTeam(bukkitBoard, String.valueOf(i));
            var entryName = entryPrefix.toString() + "" + ChatColor.WHITE;

            if(!team.hasEntry(entryName)) {
                team.addEntry(entryName);
            }

            team.prefix(freshLine);
            objective.getScore(entryName).setScore(lines - i);
        }
    }
}
