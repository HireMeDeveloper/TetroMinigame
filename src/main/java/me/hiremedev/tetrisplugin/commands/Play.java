package me.hiremedev.tetrisplugin.commands;

import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.events.PlayerSystemEvent;
import me.hiremedev.tetrisplugin.events.SystemAction;
import me.hiremedev.tetrisplugin.tasks.FallingSprites;
import me.hiremedev.tetrisplugin.controls.ControlItemFactory;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public class Play implements CommandExecutor, TabCompleter {
    private JavaPlugin plugin;
    private SystemManager systemManager;
    private KeyManager keyManager;

    public Play(JavaPlugin plugin, MasterManager masterManager) {
        this.plugin = plugin;
        this.systemManager = masterManager.getSystemManager();
        this.keyManager = masterManager.getKeyManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Usage: /play <screen>");
            return true;
        }
        try{
            var player = (Player) sender;
            var system = systemManager.getSystems().get(args[0].toLowerCase());
            if(system.getPlayers().size() > 0) {
                sender.sendActionBar(Component.text("Sorry, that system is already in use.").color(NamedTextColor.RED).decorate(TextDecoration.BOLD));
                return true;
            }
            sender.sendActionBar(Component.text("Get ready for TETRIS BABY!").color(NamedTextColor.YELLOW).decorate(TextDecoration.BOLD));
            system.addPlayer(player);
            Bukkit.getServer().getPluginManager().callEvent(new PlayerSystemEvent(player, system, SystemAction.JOIN));
            system.setState(GameState.STANDBY_START);
        } catch (IllegalArgumentException ignored){
            sender.sendMessage(Component.text("The system %s does not exist".formatted(args[0])).color(NamedTextColor.RED));
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if(args.length == 1 ) {
            return systemManager.getSystems().keySet().stream()
                    .filter((systemName)-> systemName.contains(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
