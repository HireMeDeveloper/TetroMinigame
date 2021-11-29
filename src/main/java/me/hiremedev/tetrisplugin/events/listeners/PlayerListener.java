package me.hiremedev.tetrisplugin.events.listeners;

import me.hiremedev.tetrisplugin.Scrolling.BossBarManager;
import me.hiremedev.tetrisplugin.controls.keys.Key;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.sprites.Sprite;
import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.display.util.JoinIcon;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.players.PlayerManager;
import me.hiremedev.tetrisplugin.scoreboards.ScoreboardHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Random;

public class PlayerListener implements Listener {
    private PlayerManager playerManager;
    private KeyManager keyManager;
    private ScoreboardHandler scoreboardHandler;
    private BossBarManager bossBarManager;
    private SystemManager systemManager;

    public PlayerListener(MasterManager masterManager) {
        this.playerManager = masterManager.getPlayerManager();
        this.keyManager = masterManager.getKeyManager();
        this.scoreboardHandler = masterManager.getScoreboardHandler();
        this.bossBarManager = masterManager.getBossBarManager();
        this.systemManager = masterManager.getSystemManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        var player = event.getPlayer();
        var rand = new Random();
        var icon = JoinIcon.values()[rand.nextInt(JoinIcon.values().length)].getIcon();

        scoreboardHandler.addPlayerToServer(player);

        Bukkit.broadcast(Component.text()
                .append(icon)
                .append(Component.text(" %s joined the Server!".formatted(player.getName())))
                .build()
        );

        player.showTitle(Title.title(
                Component.text("Welcome to TETRO").color(NamedTextColor.GREEN).decorate(TextDecoration.BOLD),
                Component.text("go get a high score!")
        ));

        bossBarManager.bossBars().forEach(
                bossBar -> player.showBossBar(bossBar)
        );
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        var player = event.getPlayer();
        var players = playerManager.getPlayers();
        if(players.containsKey(player)) {
            var system = players.get(player);
            system.setState(GameState.OFF);
            Bukkit.getLogger().info("System %s shut off since player: %s has left the server.".formatted(system.getName(), player.getName()));
            playerManager.removePlayer(player);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        var player = event.getPlayer();
        var item = event.getItemDrop().getItemStack();
        if(keyManager.getStringKeys(item).size() == 0) return;
        event.setCancelled(true);
        if (player != null) return;
        var keys = keyManager.getStringKeys(item);
        var key = Key.getEnum(keys.get(0));
        var screens = systemManager.getSystems();
        if (screens.size() == 0) return;
        var screen = keyManager.getStringValue(item, keys.get(0));
        var system = (TetrominoeSystem) screens.get(screen);
        if(!system.getState().equals(GameState.PLAYING)) return;
        var copy = new HashMap<Sprite, Point2D>();
        var sprites = system.getMainDisplay().getSpriteManager().getSprites();
        copy.putAll(sprites);
        copy.forEach(
                ((sprite, point2D) -> {
                    system.getMainDisplay().getSpriteManager().moveSprite(sprite, new Point2D.Double(0, -1));
                })
        );
    }

    @EventHandler
    public void onChangeInventory(InventoryClickEvent event){
        var player = (Player) event.getClickedInventory().getViewers();
        var gamemode = player.getGameMode();

        if(gamemode.equals(GameMode.CREATIVE)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        var player = event.getPlayer();
        var gamemode = player.getGameMode();

        if(gamemode.equals(GameMode.CREATIVE)) return;
        player.sendMessage(ChatColor.RED + "Cant break this block." );
        event.setCancelled(true);
    }
}
