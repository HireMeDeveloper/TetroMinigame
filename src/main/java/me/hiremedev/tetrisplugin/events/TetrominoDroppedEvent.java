package me.hiremedev.tetrisplugin.events;

import me.hiremedev.tetrisplugin.commands.Screen;
import me.hiremedev.tetrisplugin.display.lines.Line;
import me.hiremedev.tetrisplugin.display.screens.SpriteScreen;
import me.hiremedev.tetrisplugin.display.sprites.Sprite;
import me.hiremedev.tetrisplugin.display.sprites.SpriteManager;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class TetrominoDroppedEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private SpriteManager spriteManager;
    private SpriteScreen screen;
    private Sprite sprite;
    private List<Line> lines;

    public TetrominoDroppedEvent(SpriteManager spriteManager, SpriteScreen screen, Sprite sprite, List<Line> lines) {
        this.spriteManager = spriteManager;
        this.screen = screen;
        this.sprite = sprite;
        this.lines = lines;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public SpriteScreen getScreen() {
        return screen;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public List<Line> getLines() {
        return lines;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }
}
