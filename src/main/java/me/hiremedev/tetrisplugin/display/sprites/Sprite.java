package me.hiremedev.tetrisplugin.display.sprites;

import java.awt.geom.Point2D;
import java.util.HashMap;

public class Sprite {
    private String name;
    private int width;
    private int height;
    private HashMap<Point2D, Boolean> pixels = new HashMap<>();
    private Point2D screenLocation;

    public Sprite(String name, Boolean[][] values, Point2D screenLocation) {
        this.name = name;
        this.width = values[0].length;
        this.height = values.length;
        this.screenLocation = screenLocation;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pixels.put(new Point2D.Double(y,width - x - 1), values[x][y]);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public HashMap<Point2D, Boolean> getPixels() {
        return pixels;
    }

    public String getName() {
        return name;
    }

    public Point2D getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(Point2D screenLocation) {
        this.screenLocation = screenLocation;
    }
}
