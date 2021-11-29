package me.hiremedev.tetrisplugin.tasks;

import me.hiremedev.tetrisplugin.display.sprites.SpriteManager;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Tetrominoe;
import me.hiremedev.tetrisplugin.events.TetrominoDroppedEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.geom.Point2D;

public class FallingSprites implements Runnable {
    private SpriteManager spriteManager;

    public FallingSprites(SpriteManager spriteManager) {
        this.spriteManager = spriteManager;
    }

    @Override
    public void run() {
        if(spriteManager.getSprites().size() == 0 ) return;
        var sprites = spriteManager.getSprites();
        var it = spriteManager.getSprites().keySet().iterator();
        while(it.hasNext()){
            var sprite = it.next();
            if(!sprites.containsKey(sprite)) return;
            var screen = spriteManager.getScreen();
            if(!screen.canSpriteMove(sprite, sprites.get(sprite), new Point2D.Double(0,-1))) {
                Bukkit.getLogger().info("Sprite cannot fall anymore. Unregistering %s!".formatted(sprite.getName()));
                spriteManager.removeSprite(sprite, true);

                Bukkit.getServer().getPluginManager().callEvent(
                        new TetrominoDroppedEvent(
                                spriteManager,
                                spriteManager.getScreen(),
                                sprite,
                                spriteManager.getLinesFromSprite(sprite, sprite.getScreenLocation())
                        )
                );
                return;
            }
            spriteManager.moveSprite(sprite, new Point2D.Double(0,-1));
        }
    }
}
