package me.hiremedev.tetrisplugin.display.screens;

import me.hiremedev.tetrisplugin.display.sprites.SpriteManager;
import me.hiremedev.tetrisplugin.display.types.TetrominoeSystem;
import me.hiremedev.tetrisplugin.display.util.Axis;
import me.hiremedev.tetrisplugin.display.sprites.Sprite;
import me.hiremedev.tetrisplugin.display.util.PointMath;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.awt.geom.Point2D;
import java.util.stream.Collectors;

public class SpriteScreen extends Screen {
    private String name;
    private SpriteManager spriteManager;
    private TetrominoeSystem system;

    public SpriteScreen(String name, Location origin, Axis axis, int height, int width, ScreenStyle style, TetrominoeSystem system) {
        super(origin, axis, height, width, style);
        this.name = name;
        this.spriteManager = new SpriteManager(this);
        this.system = system;
    }

    public boolean isSpriteValid(Sprite sprite, Point2D pixel) {
        var pixels = sprite.getPixels();
        var invalidPixels = pixels.keySet().stream()
                .filter((point) -> pixels.get(point))
                .filter((point) -> !isPixelValid(PointMath.pixelAdd(pixel, point)))
                .collect(Collectors.toList());

        if (invalidPixels.size() > 0) {
            Bukkit.getLogger().info("Invalid Pixels: %s".formatted(invalidPixels));
            return false;
        }
        return true;
    }

    public boolean casSpriteSpawn(Sprite sprite, Point2D pixel){
        var pixels = sprite.getPixels();
        var invalidPixels = pixels.keySet().stream()
                .filter(point -> pixels.get(point))
                .filter(point -> pixelCheck(PointMath.pixelAdd(pixel, point)))
                .collect(Collectors.toList());
        if(invalidPixels.size() > 0) {
            Bukkit.getLogger().info("Cant spawn sprite %s at %s".formatted(sprite.getName(), pixel));
            return false;
        }
        return true;
    }

    public void clearScreen(){
        var height = getHeight();
        var width = getWidth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixelSet(new Point2D.Double(x,y), false);
            }
        }
        spriteManager.clearSprites();
    }

    public boolean isSpriteValidIgnoreSprite(Sprite sprite, Point2D pixel, Sprite ignored){
        var pixels = sprite.getPixels();
        var invalidPixels = pixels.keySet().stream()
                .filter((point) -> pixels.get(point))
                .filter((point) -> !isPixelValid(PointMath.pixelAdd(pixel, point)))
                .collect(Collectors.toList());
        var blockedPixels = pixels.keySet().stream()
                .filter((point) -> pixels.get(point))
                .filter((key)-> pixelCheck(PointMath.pixelAdd(pixel, key)) && !spriteContainsPixel(ignored, PointMath.pixelAdd(pixel, key)))
                .collect(Collectors.toList());
        Bukkit.getLogger().info("Invalid Pixels: %s  Blocked Pixels: %s".formatted(invalidPixels.size(), blockedPixels.size()));
        if (invalidPixels.size() > 0  || blockedPixels.size() > 0) return false;
        return true;
    }

    private boolean spriteContainsPixel(Sprite sprite, Point2D pixel) {
        var pixels = sprite.getPixels();
        var enabledPixels = pixels.keySet().stream()
                .filter((key) -> pixels.get(key))
                .collect(Collectors.toList());
        if (enabledPixels.contains(PointMath.pixelSubtract(pixel, sprite.getScreenLocation()))) return true;
        return false;
    }

    public boolean canSpriteMove(Sprite sprite, Point2D pixel, Point2D transform) {
        var pixels = sprite.getPixels();
        var enabledPixels = pixels.keySet().stream()
                .filter((key) -> pixels.get(key))
                .collect(Collectors.toList());
        var newPixel = PointMath.pixelAdd(pixel, transform);
        var blockedPixels = enabledPixels.stream()
                .filter((key) -> !canPixelMove(PointMath.pixelAdd(pixel, key), transform))
                .filter((key) -> !spriteContainsPixel(sprite, PointMath.pixelAdd(PointMath.pixelAdd(key, pixel), transform)))
                .collect(Collectors.toList()
                );
        return blockedPixels.size() <= 0;
    }

    public void spriteSet(Sprite sprite, boolean drawValue){
        sprite.setScreenLocation(new Point2D.Double(Math.ceil(getWidth() / 2) - 2, Math.ceil(getHeight() / 2) - 2));
        spriteSet(sprite,sprite.getScreenLocation(), drawValue);
    }

    public void spriteSet(Sprite sprite, Point2D pixel, boolean drawValue) {
        if (!isSpriteValid(sprite, pixel)) return;
        if (!spriteManager.getSprites().containsKey(sprite) && drawValue) spriteManager.registerSprite(sprite);
        sprite.getPixels().forEach(
                (point, value) -> {
                    if (value) pixelSet(PointMath.pixelAdd(point, pixel), drawValue);
                }
        );
        sprite.setScreenLocation(pixel);
    }

    public boolean spriteReplaceAt(Sprite oldSprite, Point2D pixel, Sprite newSprite) {
        if (!isSpriteValidIgnoreSprite(newSprite, pixel, oldSprite)) return false;
        spriteManager.removeSprite(oldSprite, false);
        spriteSet(newSprite, pixel, true);
        return true;
    }

    public TetrominoeSystem getSystem() {
        return system;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public String getName() {
        return name;
    }
}
