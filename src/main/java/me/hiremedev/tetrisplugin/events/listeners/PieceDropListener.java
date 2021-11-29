package me.hiremedev.tetrisplugin.events.listeners;

import me.hiremedev.tetrisplugin.display.lines.Line;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.events.TetrominoDroppedEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.awt.geom.Point2D;
import java.util.List;

public class PieceDropListener implements Listener {

    @EventHandler
    public void onPieceDrop(TetrominoDroppedEvent event){
        var spriteManager = event.getSpriteManager();
        var sprite = event.getSprite();
        var lines = event.getLines();
        var system = event.getScreen().getSystem();
        var player = system.getPlayers().keySet().iterator().next();
        player.playSound(player.getLocation(), system.getStyle().getDropSound(), 1,1);

        var clearCount = 0;
        var topline = 0;
        for (int i = 0; i < lines.size(); i++) {
            var line =lines.get(i);
            topline = line.getY();
            if(line.isCleared()) {
                spriteManager.clearLine(line);
                player.playSound(player.getLocation(), system.getStyle().getClearSound(), 1,1);
                clearCount++;
            }
            else if(line.getDropValue() != 0) spriteManager.moveDownLine(line);
        }
        var level = system.getCurrentLevel().getValue();
        system.addScore(10 * level);
        system.addScore((int) Math.multiplyFull(level, clearCount) * (2 * clearCount) * 100);
        system.addClears(clearCount);


        if (clearCount > 0) spriteManager.getScreen().movePixelsOnAndAbove(topline + 1, new Point2D.Double(0, -clearCount));
        if (clearCount == 4) player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1, 1);
        spriteManager.dropRandomPeice();
    }
}
