package me.hiremedev.tetrisplugin.events.listeners;

import me.hiremedev.tetrisplugin.controls.UseDirection;
import me.hiremedev.tetrisplugin.controls.keys.Key;
import me.hiremedev.tetrisplugin.controls.keys.KeyManager;
import me.hiremedev.tetrisplugin.display.screens.SystemManager;
import me.hiremedev.tetrisplugin.display.sprites.RotationDirection;
import me.hiremedev.tetrisplugin.display.sprites.Sprite;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Rotation;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Tetrominoe;
import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.engine.MasterManager;
import me.hiremedev.tetrisplugin.events.PlayerSystemEvent;
import me.hiremedev.tetrisplugin.events.SystemAction;
import me.hiremedev.tetrisplugin.events.TetrominoDroppedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.yaml.snakeyaml.emitter.Emitter;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class ControlItemListeners implements Listener {
    private SystemManager systemManager;
    private KeyManager keyManager;

    public ControlItemListeners(MasterManager masterManager) {
        this.systemManager = masterManager.getSystemManager();
        this.keyManager = masterManager.getKeyManager();
    }

    @EventHandler
    public void onUseControlItem(PlayerInteractEvent event) {
        if (!event.getHand().equals(EquipmentSlot.HAND)) return;
        var action = event.getAction();
        if (action.equals(Action.LEFT_CLICK_AIR) | action.equals(Action.LEFT_CLICK_AIR) | action.equals(Action.RIGHT_CLICK_AIR) | action.equals(Action.RIGHT_CLICK_AIR)) {
            var direction = action.name().contains("LEFT") ? UseDirection.LEFT : UseDirection.RIGHT;
            var player = event.getPlayer();
            var item = player.getInventory().getItemInMainHand();
            if (item == null) return;
            if (item.getItemMeta() == null) return;
            var keys = keyManager.getStringKeys(item);
            if (keys.size() == 0) return;
            var key = Key.getEnum(keys.get(0));
            var screens = systemManager.getSystems();
            if (screens.size() == 0) return;
            var screen = keyManager.getStringValue(item, keys.get(0));
            activateControl(key, (TetrominoeSystem) screens.get(screen), player, direction);
        }
    }

    @EventHandler
    public void onPlayerItemSwap(PlayerSwapHandItemsEvent event) {
        var player = event.getPlayer();
        var system = systemManager.getSystems().get(player);
        if(!system.getState().equals(GameState.PLAYING)) return;
        event.setCancelled(true);
        activateControl(Key.FAST_DROP, system, player, UseDirection.LEFT);
    }

    private void activateControl(Key key, TetrominoeSystem system, Player player, UseDirection direction) {
        var sprites = system.getMainDisplay().getSpriteManager().getSprites();
        var copy = new HashMap<Sprite, Point2D>();
        copy.putAll(sprites);
        player.playSound(player.getLocation(), system.getStyle().getMoveSound(), 1, 1);
        switch (key) {
            case PLAY, RESET -> {
                system.setState(GameState.PLAYING);
                return;
            }
            case QUIT -> {
                system.lose(true, GameState.OFF);
                Bukkit.getServer().getPluginManager().callEvent(new PlayerSystemEvent(player, system, SystemAction.LEAVE));
                return;
            }
        }
        copy.forEach(
                (sprite, point) ->
                {
                    switch (key) {
                        case MOVE -> {
                            if (direction.equals(UseDirection.LEFT)) {
                                if (player.isSneaking()) {
                                    var tetrominoe = Tetrominoe.valueOf(sprite.getName());
                                    var rot = Rotation.getRotation(tetrominoe.getRotation(), RotationDirection.COUNTER_CLOCKWISE);
                                    var newSprite = Tetrominoe.getTetrominoe(tetrominoe.getBlock(), rot);
                                    system.getMainDisplay().spriteReplaceAt(sprite, point, new Sprite(newSprite.name(), newSprite.getValues(), point));
                                } else {
                                    system.getMainDisplay().getSpriteManager().moveSprite(sprite, new Point2D.Double(-1, 0));
                                }
                                return;
                            }
                            if (player.isSneaking()) {
                                var tetrominoe = Tetrominoe.valueOf(sprite.getName());
                                var rot = Rotation.getRotation(tetrominoe.getRotation(), RotationDirection.CLOCKWISE);
                                var newSprite = Tetrominoe.getTetrominoe(tetrominoe.getBlock(), rot);
                                system.getMainDisplay().spriteReplaceAt(sprite, point, new Sprite(newSprite.name(), newSprite.getValues(), point));
                            } else {
                                system.getMainDisplay().getSpriteManager().moveSprite(sprite, new Point2D.Double(1, 0));
                            }
                        }
                        case FAST_DROP -> system.getMainDisplay().getSpriteManager().moveSprite(sprite, new Point2D.Double(0, -1));
                        case ROTATE -> {
                            var tetrominoe = Tetrominoe.valueOf(sprite.getName());
                            var rot = Rotation.getRotation(tetrominoe.getRotation(), (direction.equals(UseDirection.LEFT)) ? RotationDirection.COUNTER_CLOCKWISE : RotationDirection.CLOCKWISE);
                            var newSprite = Tetrominoe.getTetrominoe(tetrominoe.getBlock(), rot);
                            system.getMainDisplay().spriteReplaceAt(sprite, point, new Sprite(newSprite.name(), newSprite.getValues(), point));
                        }
                    }
                });

    }
}
