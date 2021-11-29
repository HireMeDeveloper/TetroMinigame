package me.hiremedev.tetrisplugin.display.lines;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Line {
    private int y;
    private HashMap<Point2D, Boolean> values;
    private int dropValue = 0;

    public Line(int y, HashMap<Point2D, Boolean> values) {
        this.y = y;
        this.values = values;
    }

    public boolean isCleared(){
        var empties = values.values().stream()
                .filter(value -> !value)
                .collect(Collectors.toList());
        return empties.size() == 0;
    }

    public int getY() {
        return y;
    }

    public HashMap<Point2D, Boolean> getValues() {
        return values;
    }

    public int getDropValue() {
        return dropValue;
    }

    public void setDropValue(int dropValue) {
        this.dropValue = dropValue;
    }

    @Override
    public String toString() {
        return "Line{" +
                "y=" + y +
                ", values=" + values +
                ", dropValue=" + dropValue +
                '}';
    }
}
