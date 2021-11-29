package me.hiremedev.tetrisplugin.commands;

import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.database.DatabaseManager;
import me.hiremedev.tetrisplugin.display.screens.ScreenStyle;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.Axis;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.security.auth.kerberos.KerberosTicket;
import javax.swing.text.Style;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Screen implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;
    private SystemManager systemManager;
    private KeyManager keyManager;
    private DatabaseManager databaseManager;
    private ScoreboardHandler scoreboardHandler;

    public Screen(JavaPlugin plugin, MasterManager masterManager) {
        this.plugin = plugin;
        this.systemManager = masterManager.getSystemManager();
        this.keyManager = masterManager.getKeyManager();
        this.databaseManager = masterManager.getDatabaseManager();
        this.scoreboardHandler = masterManager.getScoreboardHandler();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) return true;
        var player = (Player) sender;
        if (!player.hasPermission("TetrisPlugin.screen")){
            player.sendMessage(Component.text("You do not have permission (TetrisPlugin.screen) to do this command.").color(NamedTextColor.RED));
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /screen <name> <Style>");
            return true;
        }
        var style = ScreenStyle.valueOf(args[1].toUpperCase());
        if(style == null){
            player.sendMessage(ChatColor.RED + "Not a valid style!");
            return true;
        }

        var location = player.getLocation();
        new TetrominoeSystem(
                args[0].toLowerCase(),
                location,
                Axis.getYaw(player),
                style,
                systemManager,
                plugin,
                keyManager,
                databaseManager,
                scoreboardHandler,
                true
                );
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 2){
            return Arrays.stream(ScreenStyle.values())
                    .map(Enum::name)
                    .map(String::toLowerCase)
                    .filter((style) -> style.contains(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
