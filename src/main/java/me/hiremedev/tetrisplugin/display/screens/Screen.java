package me.hiremedev.tetrisplugin.display.screens;

import me.hiremedev.tetrisplugin.display.util.Axis;
import me.hiremedev.tetrisplugin.display.util.PointMath;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.io.BukkitObjectInputStream;

import javax.swing.text.Style;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Screen {
    private Location origin;
    private Axis axis;
    private int height;
    private int width;
    private ScreenStyle style;
    private List<Point2D> pixels = new ArrayList<>();

    public Screen(Location origin, Axis axis, int height, int width, ScreenStyle style) {
        this.origin = origin;
        this.axis = axis;
        this.height = height;
        this.width = width;
        this.style = style;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels.add(new Point2D.Double(x,y));
            }
        }

        fillScreen();
    }

    private void fillScreen(){
        pixels.forEach(
                (pixel) -> {
                    locationFromPoint(pixel, 1).getBlock().setType(style.getFront());
                    locationFromPoint(pixel, -1).getBlock().setType(style.getBack());
                }
        );
    }

    private Location locationFromPoint(Point2D point){
        return locationFromPoint(point, 0);
    }

    private Location locationFromPoint(Point2D point, int offset){
        return PointMath.locationFromPoint(origin, axis, point, offset);
    }

    protected boolean isPixelValid(Point2D pixel){
        var x = pixel.getX();
        var y = pixel.getY();
        if(x < width && x >= 0 && y < height && y >= 0) return true;
        return false;
    }
    protected boolean canPixelMove(Point2D pixel, Point2D transform){
        var newPixel = PointMath.pixelAdd(pixel, transform);
        if((transform.equals(new Point2D.Double(0,-1)) && newPixel.getY() < 0) | pixelCheck(newPixel)) return false;
        return true;
    }

    public void movePixelsOnAndAbove(int yStart, Point2D transform){
        for (int y = yStart; y < height; y++) {
            for (int x = 0; x < width; x++) {
                var pixel = new Point2D.Double(x,y);
                pixelMove(pixel, transform);
            }
        }
    }

    public boolean pixelCheck(Point2D pixel){
        var location = this.locationFromPoint(pixel);
        if(location.getBlock().getType().equals(style.getMiddle())) return true;
        return false;
    }

    public void pixelSet(Point2D pixel, boolean value){
        //if(isPixelValid(pixel)) return;
        locationFromPoint(pixel).getBlock().setType((value)? style.getMiddle() : Material.AIR);
    }

    public void pixelMove(Point2D pixel, Point2D transform){
        if (!pixelCheck(pixel)) return;
        pixelSet(pixel, false);
        pixelSet(PointMath.pixelAdd(pixel, transform), true);
    }

    protected void pixelSwap(Point2D pixel){
        if(isPixelValid(pixel)) return;
        pixelSet(pixel, pixelCheck(pixel));
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public List<Point2D> getPixels() {
        return pixels;
    }
}
