package me.hiremedev.tetrisplugin.display.sprites;

import me.hiremedev.tetrisplugin.display.lines.Line;
import me.hiremedev.tetrisplugin.display.screens.SpriteScreen;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Block;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Rotation;
import me.hiremedev.tetrisplugin.display.sprites.tetrominoes.Tetrominoe;
import me.hiremedev.tetrisplugin.display.util.GameState;
import me.hiremedev.tetrisplugin.display.util.PointMath;
import org.bukkit.Bukkit;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SpriteManager {
    private SpriteScreen screen;
    private HashMap<Sprite, Point2D> sprites;
    private List<Sprite> removals;

    public SpriteManager(SpriteScreen screen) {
        this.screen = screen;
        this.sprites = new HashMap<>();
        removals = new ArrayList<>();
    }

    public boolean registerSprite(Sprite sprite){
        var point = sprite.getScreenLocation();
        if (!screen.isSpriteValid(sprite, point)) {
            Bukkit.getLogger().info("Failed to register sprite since %s in invalid at %s on %s".formatted(sprite.getName(), point, screen.getName()));
            return false;
        }

        sprites.putIfAbsent(sprite, point);
        screen.spriteSet(sprite, point, true);
        return true;
    }
    public void removeSprite(Sprite sprite, Boolean abandon){
        var point = sprites.get(sprite);
        sprites.remove(sprite);
        if(abandon) return;
        screen.spriteSet(sprite, sprite.getScreenLocation(), false);
    }

    public void clearSprites(){
        sprites = new HashMap<>();
    }

    public Boolean dropRandomPeice(){
        var block = screen.getSystem().getNextBlock();
        var tetromino = Tetrominoe.getTetrominoe(block, Rotation.ROT_0);
        var sprite = new Sprite(tetromino.name(),tetromino.getValues(), new Point2D.Double(3, 18));
        if (!screen.casSpriteSpawn(sprite, sprite.getScreenLocation())) {
            screen.getSystem().lose(false, GameState.STANDBY_END);
            return true;
        }
        registerSprite(sprite);
         return false;
    }

    public List<Line> getLinesFromSprite(Sprite sprite, Point2D pixel){
        var block = Tetrominoe.valueOf(sprite.getName().toUpperCase()).getBlock();
        var yStart = (int) pixel.getY();
        var yEnd = yStart + ((block.equals(Block.I)) ? 4 : 3);
        var lines = new ArrayList<Line>();
        var clearCount = 0;
        for (int y = yStart; y < yEnd ; y++) {
            var lineValues = new HashMap<Point2D, Boolean>();
            for (int x = 0; x < screen.getWidth(); x++) {
                var point = new Point2D.Double(x,y);
                var value = screen.pixelCheck(point);
                lineValues.put(point, value);
            }
            var line = new Line(y, lineValues);
            if(line.isCleared()) clearCount++;
            line.setDropValue((line.isCleared()) ? 0 : clearCount);
            lines.add(line);
        }
        return lines;
    }

    public void clearLine(Line line){
        line.getValues().keySet().forEach(
                (pixel) -> screen.pixelSet(pixel,false)
        );
        Bukkit.getLogger().info("Line %s cleared!".formatted(line.getY()));
    }

    public void moveDownLine(Line line){
        var amount = line.getDropValue();
        line.getValues().keySet().forEach(
                (pixel) -> screen.pixelMove(pixel,new Point2D.Double(0, -amount))
        );
    }

    public void moveSprite(Sprite sprite, Point2D transform){
        var oldPoint = sprites.get(sprite);
        if(!screen.canSpriteMove(sprite, oldPoint, transform)) return;
        var newPoint = PointMath.pixelAdd(oldPoint, transform);
        if(!screen.isSpriteValid(sprite, newPoint)) return;
        screen.spriteSet(sprite, oldPoint, false);
        screen.spriteSet(sprite, newPoint, true);
        sprites.put(sprite, newPoint);
    }

    public SpriteScreen getScreen() {
        return screen;
    }

    public HashMap<Sprite, Point2D> getSprites() {
        return sprites;
    }
}
