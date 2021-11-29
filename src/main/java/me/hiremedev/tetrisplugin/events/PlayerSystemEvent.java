package me.hiremedev.tetrisplugin.events;

import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerSystemEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private TetrominoeSystem system;
    private SystemAction action;

    public PlayerSystemEvent(Player player, TetrominoeSystem system, SystemAction action) {
        this.player = player;
        this.system = system;
        this.action = action;
    }

    public SystemAction getAction() {
        return action;
    }

    public Player getPlayer() {
        return player;
    }

    public TetrominoeSystem getSystem() {
        return system;
    }


    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
